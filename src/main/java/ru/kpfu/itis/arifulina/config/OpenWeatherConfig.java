package ru.kpfu.itis.arifulina.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.arifulina.base.ProjectResources;

@Data
@Component
@ConfigurationProperties(value = ProjectResources.OPEN_WEATHER_PROPS)
public class OpenWeatherConfig {

    private String url;
    private String appid;
}
