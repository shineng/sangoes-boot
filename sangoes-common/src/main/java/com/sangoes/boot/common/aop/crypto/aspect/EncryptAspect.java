package com.sangoes.boot.common.aop.crypto.aspect;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangoes.boot.common.aop.crypto.annotation.Encrypt;
import com.sangoes.boot.common.aop.crypto.properties.CryptoProperties;
import com.sangoes.boot.common.utils.crypto.CryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.nio.charset.Charset;

/**
 * Copyright (c) sangoes 2018
 * https://github.com/sangoes
 *
 * @author jerrychir
 * @date 2019 2019/1/16 8:12 PM
 */
@Slf4j
@Component
@Scope
@Aspect
@RestControllerAdvice
public class EncryptAspect implements ResponseBodyAdvice {

    @Autowired
    private CryptoProperties properties;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Whether this component supports the given controller method return type
     * and the selected {@code HttpMessageConverter} type.
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
     * {@code false} otherwise
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 检查加密注解是否存在 method 上
        return returnType.getMethod().isAnnotationPresent(Encrypt.class);
    }

    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String content = null;
        try {
            content = objectMapper.writeValueAsString(body);
            if (log.isDebugEnabled()) {
                log.info("[加密前的内容] - [{}]", content);
            }

        } catch (JsonProcessingException e) {
            log.error("格式转换错误", e);
        }
        final Charset charset = Charset.forName(properties.getEncoding());
        final Encrypt encrypt = returnType.getMethodAnnotation(Encrypt.class);
        final CryptoProperties.Encrypt propertiesEncrypt = properties.getEncrypt();
        final String key = StringUtils.defaultString(encrypt.key(), propertiesEncrypt.getKey());
        final String result = CryptoUtils.encryptToString(encrypt.type(), key, content, charset);
        if (log.isDebugEnabled()) {
            log.info("[加密后的内容] - [{}]", result);
        }
        return result;
    }
}
