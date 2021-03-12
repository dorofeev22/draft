package ru.dorofeev22.draft.service.model;

import org.springframework.data.domain.Page;
import ru.dorofeev22.draft.domain.BaseEntity;

import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class PageModelHelper {

    public static <E extends BaseEntity, M> PageModel<M> createPageModel(Page<E> page, Class<M> modelClass, BiFunction<E, Class<M>, M> mapper) {
        return new PageModel<M>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getContent().stream().map(e -> mapper.apply(e, modelClass)).collect(Collectors.toList()));
    }
}
