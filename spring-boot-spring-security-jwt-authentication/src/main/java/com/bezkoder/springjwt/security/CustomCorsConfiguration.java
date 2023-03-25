package com.bezkoder.springjwt.security;
/*
 * Copyright (c) 2022. Mavenir Systems.
 */

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;

public class CustomCorsConfiguration extends CorsConfiguration {

    /**
     * Check the origin of the request against the configured allowed origins.
     *
     * @param requestOrigin the origin to check
     * @return the origin to use for the response, or {@code null} which
     * means the request origin is not allowed
     */
    @Override
    @Nullable
    public String checkOrigin(@Nullable String requestOrigin) {
        if (!StringUtils.hasText(requestOrigin)) {
            return null;
        }
        if (ObjectUtils.isEmpty(getAllowedOrigins())) {
            return null;
        }

        if (getAllowedOrigins().contains(ALL)) {
            if (getAllowCredentials() != Boolean.TRUE) {
                return ALL;
            }
            else {
                return requestOrigin;
            }
        }
        for (String allowedOrigin : getAllowedOrigins()) {
            if (requestOrigin.matches(allowedOrigin)) {
                return requestOrigin;
            }
        }

        return null;
    }
}

