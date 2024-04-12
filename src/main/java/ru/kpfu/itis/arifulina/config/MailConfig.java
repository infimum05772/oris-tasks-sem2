package ru.kpfu.itis.arifulina.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfig {

    private String content;
    private String subject;
    private String from;
    private String sender;
    private String to;
}
