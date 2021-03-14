package ru.dorofeev22.draft.core.searching;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

public class SearchSpecification<E> implements Specification<E> {
    
    private final SearchCriteria searchCriteria;
    private static final String PERCENT_SIGN = "%";
    
    public SearchSpecification(@NotNull final SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
    
    @Override
    public Predicate toPredicate(@NotNull final Root<E> root,
                                 @NotNull final CriteriaQuery<?> criteriaQuery,
                                 @NotNull final CriteriaBuilder criteriaBuilder) {
        switch (searchCriteria.getOperation()) {
            case LIKE:
                return criteriaBuilder.like(
                        criteriaBuilder.lower(getPath(root)),
                        PERCENT_SIGN.concat(getValue().toString().toLowerCase()).concat(PERCENT_SIGN));
            case EQUAL:
                return criteriaBuilder.equal(getPath(root), getValue());
            case IN:
                return getPath(root).in(getValues());
            case BETWEEN:
                return criteriaBuilder.between(getPathTime(root), getTimeValue(0), getTimeValue(1));
        }
        return null;
    }

    private Path<String> getPath(@NotNull final Root<E> root) {
        return root.get(searchCriteria.getKey());
    }
    
    private Path<LocalDateTime> getPathTime(@NotNull final Root<E> root) {
        return root.get(searchCriteria.getKey());
    }
    
    private Object getValue() {
        return searchCriteria.getValues().get(0);
    }

    private List<Object> getValues() {
        return searchCriteria.getValues();
    }

    private LocalDateTime getTimeValue(int index) {
        return (LocalDateTime) searchCriteria.getValues().get(index);
    }

}