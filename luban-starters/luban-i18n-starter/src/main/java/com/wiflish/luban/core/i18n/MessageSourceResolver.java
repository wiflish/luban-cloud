package com.wiflish.luban.core.i18n;

import java.util.Locale;

/**
 * @author wiflish
 * @since 2023-08-15
 */
public interface MessageSourceResolver {
    default String getMessage(String key) {
        return getMessage(key, Locale.getDefault());
    }

    String getMessage(String key, Locale locale);
}
