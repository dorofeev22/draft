package ru.dorofeev22.draft.service.model;

import org.springframework.data.domain.Page;
import ru.dorofeev22.draft.domain.BaseEntity;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;


public class PageModelHelper {
    
    /**
     * Create page model.
     *
     * @param page       page object from JPA repository
     * @param mapper     function for entity -> model transformation
     * @param <E>        entity class
     * @param <M>        model class
     * @return page model object
     */
    public static <E extends BaseEntity, M> PageModel<M> createPageModel(Page<E> page, Function<E, M> mapper) {
        return new PageModel<M>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getContent().stream().map(mapper).collect(Collectors.toList()));
    }
}
