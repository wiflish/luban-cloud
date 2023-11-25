package com.wiflish.luban.starter.web.audit;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wiflish.luban.core.dto.constant.BaseConstant;
import com.wiflish.luban.starter.web.util.RequestContextUtil;
import com.wiflish.luban.tools.invoker.CommonInvoker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * 审计日志切面处理
 *
 * @author wiflish
 * @since 2023-11-22
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class AuditLogAspect {
    private final AuditLogService auditLogService;

    @Around("@annotation(auditLog)")
    public Object around(ProceedingJoinPoint joinPoint, AuditLog auditLog) throws Throwable {
        LocalDateTime startTime = LocalDateTime.now();
        try {
            Object result = joinPoint.proceed();

            saveLog(joinPoint, auditLog, startTime, BaseConstant.SUCCESS, null);
            
            return result;
        } catch (Exception ex) {
            saveLog(joinPoint, auditLog, startTime, BaseConstant.FAILURE, ex.getMessage());
            throw ex;
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint, AuditLog auditLog, LocalDateTime startTime, Integer result, String errorMessage) {
        try {
            AuditLogDTO auditLogDTO = new AuditLogDTO();

            // 执行时长
            long duration = LocalDateTimeUtil.between(startTime, LocalDateTime.now()).toMillis();
            auditLogDTO.setDuration((int) duration);
            auditLogDTO.setAuditType(auditLog.type().getValue());
            auditLogDTO.setModuleName(auditLog.module());
            auditLogDTO.setFuncName(auditLog.funcName());
            if (StrUtil.isNotEmpty(errorMessage)) {
                auditLogDTO.setResultMsg(errorMessage.length() > 490 ? errorMessage.substring(0, 490) : errorMessage);
            }

            // 默认从类上的Swagger注解Tag读取
            if (StrUtil.isEmpty(auditLogDTO.getModuleName())) {
                Tag tag = getClassAnnotation(joinPoint, Tag.class);
                if (tag != null) {
                    auditLogDTO.setModuleName(tag.name());
                }
            }
            // 默认从Swagger注解Operation读取
            if (StrUtil.isEmpty(auditLogDTO.getFuncName())) {
                Operation operation = getMethodAnnotation(joinPoint, Operation.class);
                if (operation != null) {
                    auditLogDTO.setFuncName(operation.summary());
                }
            }

            HttpServletRequest request = RequestContextUtil.getHttpServletRequest();
            if (request != null) {
                auditLogDTO.setIp(RequestContextUtil.getClientIP());
                auditLogDTO.setAddress(CommonInvoker.getRealAddress(auditLogDTO.getIp()));
                auditLogDTO.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
                auditLogDTO.setPath(request.getRequestURI());

                String method = request.getMethod();
                auditLogDTO.setMethod(method);

                int auditType = auditLog.type().getValue();
                AuditTypeEnum auditTypeEnum = AuditTypeEnum.get(method);
                if (auditType == AuditTypeEnum.OTHER.getValue()) {
                    auditLogDTO.setAuditType(auditTypeEnum.getValue());
                }
            }
            auditLogDTO.setParams(buildMethodArgs(joinPoint));
            auditLogDTO.setResult(result);

            auditLogService.save(auditLogDTO);
        } catch (Exception e) {
            log.warn("审计日志切面处理异常。auditLog: {}, 方法请求结果：{}", JSONObject.toJSONString(auditLog), result, e);
        }
    }

    private String buildMethodArgs(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] argNames = methodSignature.getParameterNames();
        Object[] argValues = joinPoint.getArgs();

        Map<String, Object> args = MapUtil.newHashMap(argValues.length);
        for (int i = 0; i < argNames.length; i++) {
            String argName = argNames[i];
            Object argValue = argValues[i];
            args.put(argName, ignoreArgs(argValue) ? "[ignore]" : argValue);
        }

        return JSON.toJSONString(args);
    }

    private static boolean ignoreArgs(Object object) {
        Class<?> clazz = object.getClass();

        // 处理数组
        if (clazz.isArray()) {
            return IntStream.range(0, Array.getLength(object))
                    .anyMatch(index -> ignoreArgs(Array.get(object, index)));
        }

        // 处理集合
        if (Collection.class.isAssignableFrom(clazz)) {
            return ((Collection<?>) object).stream()
                    .anyMatch((Predicate<Object>) AuditLogAspect::ignoreArgs);
        }

        // 处理Map
        if (Map.class.isAssignableFrom(clazz)) {
            return ignoreArgs(((Map<?, ?>) object).values());
        }

        return object instanceof MultipartFile
                || object instanceof HttpServletRequest
                || object instanceof HttpServletResponse
                || object instanceof BindingResult;
    }

    private static <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(annotationClass);
    }

    private static <T extends Annotation> T getClassAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getDeclaringClass().getAnnotation(annotationClass);
    }
}