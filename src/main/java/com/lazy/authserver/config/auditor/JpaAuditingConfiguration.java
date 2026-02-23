package com.lazy.authserver.config.auditor;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@RequiredArgsConstructor
public class JpaAuditingConfiguration {


    @Bean
    public AuditorAware<Long> auditorProvider(HttpServletRequest request) {
        return () -> {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
//                Long userId = userDataConfig.userId();
//
//                if(userId == null){
//                    return Optional.empty();
//                } else {
//                    return Optional.of(userId);
//                }
                return Optional.empty();
            } else {
                return Optional.empty();
            }
        };
    }




}
