package ru.kpfu.itis.arifulina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.List;

@SpringBootApplication
public class Application {

    public Application(FreeMarkerConfigurer freeMarkerConfigurer) {
        freeMarkerConfigurer
                .getTaglibFactory()
                .setClasspathTlds(List.of("/META-INF/security.tld"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
