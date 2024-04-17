package ru.kpfu.itis.arifulina.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.kpfu.itis.arifulina.base.ParamsKey;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(ParamsKey.SIGN_UP_RM).setViewName(ParamsKey.SIGN_UP_VN);
        registry.addViewController(ParamsKey.INDEX_RM).setViewName(ParamsKey.INDEX_VN);
        registry.addViewController(ParamsKey.PROFILE_RM).setViewName(ParamsKey.PROFILE_VN);
    }
}