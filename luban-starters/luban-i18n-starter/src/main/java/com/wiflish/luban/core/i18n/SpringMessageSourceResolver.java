package com.wiflish.luban.core.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author wiflish
 * @since 2023-08-15
 */
@Component
public class SpringMessageSourceResolver implements MessageSourceResolver {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    @Override
    public String getMessage(String key, Locale locale) {
        return getMessage(key);
    }
}
