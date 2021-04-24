package ru.dorofeev22.draft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.dorofeev22.draft.core.config.ErrorHandlingConfig;
import ru.dorofeev22.draft.core.config.ModelMapperConfig;
import ru.dorofeev22.draft.core.config.ObjectMapperConfig;
import ru.dorofeev22.draft.core.config.RestTemplateConfig;

@SpringBootApplication
@Import({ModelMapperConfig.class, ObjectMapperConfig.class, ErrorHandlingConfig.class, RestTemplateConfig.class})
public class DraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(DraftApplication.class, args);
	}

}
