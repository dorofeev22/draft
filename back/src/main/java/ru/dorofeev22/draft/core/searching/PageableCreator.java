package ru.dorofeev22.draft.core.searching;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dorofeev22.draft.core.error.BadParameterError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PageableCreator {
    
    static final int defaultRowLimit = 20;
    static final String SORT_ORDER_DELIMITER = ":";
    
    public static Pageable createPageable(final Integer limit, final Integer offset, final String[] sortParams) {
        if (limit != null) {
            if (offset != null) {
                checkOffset(limit, offset);
                return createPageable(offset > 0 ? offset / limit : 0, limit.intValue(), sortParams);
            }
            return createPageable(0, limit.intValue(), sortParams);
        }
        return createPageable(sortParams);
    }
    
    public static Pageable createPageable(final String[] sortParams) {
        return createPageable(0, defaultRowLimit, sortParams);
    }
    
    public static Pageable createPageable(final int page, final int size, final String[] sortParams) {
        return sortParams == null || sortParams.length == 0
                ? PageRequest.of(page, size) : PageRequest.of(page, size, createSort(sortParams));
    }
    
    private static void checkOffset(final int limit, final int offset) {
        if (offset % limit > 0) {
            throw new BadParameterError("offset", String.valueOf(offset));
        }
    }
    
    private static Sort createSort(final String[] parameterValue) {
        return Sort.by(
                Arrays.stream(parameterValue)
                        .map(v -> createOrder(v.split(SORT_ORDER_DELIMITER)))
                        .collect(Collectors.toList()));
    }
    
    /**
     *  Create ordering object
     * @param parameterValue sorting parameter value like sortFieldName:sorDirection
     */
    private static Sort.Order createOrder(final String[] parameterValue) {
        return new Sort.Order(
                parameterValue.length > 1 ? Sort.Direction.fromString(parameterValue[1]) : Sort.Direction.ASC,
                parameterValue[0]);
    }
    
    
}
