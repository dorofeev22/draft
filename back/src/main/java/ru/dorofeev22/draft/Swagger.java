package ru.dorofeev22.draft;

public class Swagger {
    public static String[] SWAGGER_PATHS = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html"
    };

    public static final String WEBJARS_PATH = "/webjars/**";

    public static final String RESOURCES_SOURCE = "classpath:/META-INF/resources/";

    public static final String WEBJARS_SOURCE = "classpath:/META-INF/resources/webjars/";
}
