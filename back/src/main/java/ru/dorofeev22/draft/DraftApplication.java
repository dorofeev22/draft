package ru.dorofeev22.draft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.dorofeev22.draft.core.config.ModelMapperConfig;
import ru.dorofeev22.draft.core.config.ObjectMapperConfig;

@SpringBootApplication
@Import({ModelMapperConfig.class, ObjectMapperConfig.class})
public class DraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(DraftApplication.class, args);
	}

}
