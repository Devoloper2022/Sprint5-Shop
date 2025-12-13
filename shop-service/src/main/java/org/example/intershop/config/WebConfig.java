package org.example.intershop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;


@Configuration
@ComponentScan(basePackages = "org.example.intershop")
@PropertySource("classpath:application.properties")
public class WebConfig implements WebFluxConfigurer {
    @Value("${storage.path}")
    private String storePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + storePath+"/");
    }
}