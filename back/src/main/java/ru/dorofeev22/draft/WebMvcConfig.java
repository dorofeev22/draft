package ru.dorofeev22.draft;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(Swagger.SWAGGER_PATHS)
                .addResourceLocations(Swagger.RESOURCES_SOURCE);
        registry
                .addResourceHandler(Swagger.WEBJARS_PATH)
                .addResourceLocations(Swagger.WEBJARS_SOURCE);
    }

}
