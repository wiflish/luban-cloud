/*-
 * ************
 * luban-cloud
 * ------------
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ************
 */
package ${package.Controller};

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.wiflish.luban.core.dto.ListResponse;
import com.wiflish.luban.core.dto.OneResponse;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.core.dto.exception.BizException;

<#if package.ModuleName?? && package.ModuleName != "">
import ${package.ModuleName}.convert.${ClassName}Convert;
import ${package.ModuleName}.entity.${ClassName}Entity;
import ${package.ModuleName}.service.${ClassName}Service;
import ${package.ModuleName}.query.${ClassName}Query;
import ${package.ModuleName}.vo.${ClassName}VO;
</#if>

<#if package.ModuleName?? && package.ModuleName != "">
import org.springdoc.core.annotations.ParameterObject;
</#if>

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>
 * ${table.comment!} Controller
 * </p>
 *
 * @author ${author}
 * @since ${date}
*/
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("${moduleName}/${functionName}")
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")

@Tag(name="${tableComment}")
public class ${ClassName}Controller {
    private final ${ClassName}Service ${className}Service;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:page')")
    public Result<PageResult<${ClassName}VO>> page(@ParameterObject @Valid ${ClassName}Query query){
        PageResult<${ClassName}VO> page = ${className}Service.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:info')")
    public Result<${ClassName}VO> get(@PathVariable("id") Long id){
        ${ClassName}Entity entity = ${className}Service.getById(id);

        return Result.ok(${ClassName}Convert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:save')")
    public Result<String> save(@RequestBody ${ClassName}VO vo){
        ${className}Service.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:update')")
    public Result<String> update(@RequestBody @Valid ${ClassName}VO vo){
        ${className}Service.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        ${className}Service.delete(idList);

        return Result.ok();
    }
}