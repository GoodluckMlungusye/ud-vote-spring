package com.goodamcodes.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${url.image}")
    private String imageStoragePath;

    @Value("${url.video}")
    private String videoStoragePath;

    @Value("${url.document}")
    private String documentStoragePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imageStoragePath);

        registry.addResourceHandler("/videos/**")
                .addResourceLocations("file:" + videoStoragePath);

        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + documentStoragePath);
    }
}

