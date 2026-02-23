package com.lazy.authserver.config.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: Santosh Paudel
 */

@PropertySource("classpath:mail.properties")
@ConfigurationProperties(prefix = "mail")
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailProperties {

    private int port;
    private String host;
    private String from;
    private String username;
    private String password;
    private String uri;
    private Long companyId;
}
