package ru.dorofeev22.draft;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalTime;

import static java.util.Collections.emptyList;

public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .enable(true)
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalTime.class, Long.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "API draft system",
                "API draft system",
                "1.0.0",
                "",
                new Contact("Support Service", "", "support@draft.com"),
                "",
                "",
                emptyList());
    }

}
