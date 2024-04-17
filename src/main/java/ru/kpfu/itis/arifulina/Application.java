package ru.kpfu.itis.arifulina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ru.kpfu.itis.arifulina.base.ProjectResources;

import java.util.List;

@SpringBootApplication
public class Application {

    public Application(FreeMarkerConfigurer freeMarkerConfigurer) {
        freeMarkerConfigurer
                .getTaglibFactory()
                .setClasspathTlds(List.of(ProjectResources.CLASSPATH_TLD));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
