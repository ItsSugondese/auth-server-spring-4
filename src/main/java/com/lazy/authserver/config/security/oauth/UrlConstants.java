package com.lazy.authserver.config.security.oauth;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class UrlConstants {
    private UrlConstants() {}

    public static final RequestMatcher PUBLIC_ENDPOINTS = new OrRequestMatcher(
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/auth/login"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/temporary-attachments"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/app-users"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/staff"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/images/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/swagger"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/swagger-ui/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/v3/api-docs/**")
    );
}
