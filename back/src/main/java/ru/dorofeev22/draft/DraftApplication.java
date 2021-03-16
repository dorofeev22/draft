package ru.dorofeev22.draft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.dorofeev22.draft.core.config.ModelMapperConfig;
import ru.dorofeev22.draft.core.config.ObjectMapperConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
@Import({ModelMapperConfig.class, ObjectMapperConfig.class, WebMvcConfig.class, SwaggerConfig.class})
public class DraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(DraftApplication.class, args);
	}

}
