package ru.kpfu.itis.arifulina.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.arifulina.base.ProjectResources;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = ProjectResources.MAIL_PROPS)
public class MailConfig {

    private String content;
    private String subject;
    private String from;
    private String sender;
    private String to;
}
