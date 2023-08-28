package com.wiflish.luban.samples.ddd.config;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * fastjson的配置. Json结构的请求参数使用fastjson解析.
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Configuration
public class FastjsonConverterConfig extends WebMvcConfigurationSupport {
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);

        converter.setFastJsonConfig(fastJsonConfig);
        converter.setSupportedMediaTypes(ListUtil.of(MediaType.APPLICATION_JSON));

        converters.add(new StringHttpMessageConverter());
        converters.add(converter);
    }
}