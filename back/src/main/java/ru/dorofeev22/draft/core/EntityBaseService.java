package ru.dorofeev22.draft.core;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import ru.dorofeev22.draft.core.constant.RequestConstants;
import ru.dorofeev22.draft.core.endpoint.PageModel;
import ru.dorofeev22.draft.core.error.DuplicateObjectError;
import ru.dorofeev22.draft.core.searching.PageableCreator;
import ru.dorofeev22.draft.core.searching.SearchCriteria;
import ru.dorofeev22.draft.core.searching.SearchOperation;
import ru.dorofeev22.draft.core.searching.SearchSpecification;
import ru.dorofeev22.draft.core.utils.DateTimeUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.dorofeev22.draft.core.constant.RequestConstants.pageableParameters;
import static ru.dorofeev22.draft.core.endpoint.PageModelHelper.createPageModel;
import static ru.dorofeev22.draft.core.error.service.ErrorHelper.createNotFountError;
import static ru.dorofeev22.draft.core.searching.SearchOperation.IN;
import static ru.dorofeev22.draft.core.searching.SearchOperation.LIKE;
import static ru.dorofeev22.draft.core.utils.WebUtils.getIntParameterValue;
import static ru.dorofeev22.draft.core.utils.WebUtils.getParameterValues;

/**
 * Base service with common methods for Entities
 * @param <E> Entity class
 * @param <I> Income model class
 * @param <O> Outcome model class
 */
public abstract class EntityBaseService<E extends BaseEntity, I, O> {
    
    // <Key (entity filed name), Value (Operation for searching on this filed)>
    protected Map<String, SearchOperation> searchOperationMap = new HashMap<>();
    // <Key (entity filed name), Value (function for transformation parameter value into the object fore searching)>
    protected Map<String, Function<String[], List<Object>>> searchParameterFunctionMap = new HashMap<>();
    
    @Autowired
    protected ModelMapper modelMapper;
    
    @PostConstruct
    public void init() {
        searchParameterFunctionMap.put("id", this::createListIds);
    }
    
    protected abstract BaseRepository<E> getRepository();
    protected abstract Class<E> getEntityClass();
    protected abstract Class<O> getOutcomeModelClass();
    
    protected void beforeCreation(@NotNull final I incomeModel) {
        // implement in the heirs if it needed
    }
    
    protected O toOutcome(@NotNull final E entity) {
        return modelMapper.map(entity, getOutcomeModelClass());
    }
    
    protected E toEntity(@NotNull final I incomeModel) {
        return modelMapper.map(incomeModel, getEntityClass());
    }
    
    protected void mapEntity(@NotNull final I incomeModel, @NotNull final E entity) {
        modelMapper.map(incomeModel, entity);
    }
    
    protected E getOrThrow(@NotNull final UUID id) {
        return getRepository().findById(id).orElseThrow(() -> createNotFountError(getEntityClass(), id));
    }
    
    public E create(@NotNull final I incomeModel) {
        beforeCreation(incomeModel);
        return getRepository().save(toEntity(incomeModel));
    }

    public O createAndGetOutcome(@NotNull final I creationModel) {
        return toOutcome(create(creationModel));
    }
    
    @Transactional
    public E update(@NotNull final UUID id, @NotNull final I incomeModel) {
        final E entity = getOrThrow(id);
        mapEntity(incomeModel, entity);
        return entity;
    }
    
    @Transactional
    public O updateAnfGetOutcome(@NotNull final UUID id, @NotNull final I incomeModel) {
        return toOutcome(update(id, incomeModel));
    }

    public O getOutcomeModel(@NotNull final UUID id) {
        return toOutcome(getOrThrow(id));
    }
    
    public Page<E> search(@NotNull final Map<String, String[]> parameters) {
        return getRepository().findAll(createSpecification(parameters), createPageable(parameters));
    }
    
    /**
     * Find entities by criteria
     * @param parameters criteria (not empty to restrict return collection)
     * @return list of entities
     */
    protected List<E> find(@NotNull @NotEmpty final Map<String, String[]> parameters) {
        return getRepository().findAll(createSpecification(parameters));
    }
    
    /**
     * Find entities and throw DuplicateObjectError if find
     * @param parameters criteria
     */
    protected void findAndThrow(@NotNull final Map<String, String[]> parameters) {
        if (!find(parameters).isEmpty())
            throw new DuplicateObjectError(getEntityClass().getName(), getParameterValues(parameters));
    }
    
    public PageModel<O> searchOutcomes(@NotNull final HttpServletRequest httpServletRequest) {
        return createPageModel(search(httpServletRequest.getParameterMap()), this::toOutcome);
    }
    
    @Transactional
    public void delete(@NotNull final UUID id) {
        getRepository().delete(getOrThrow(id));
    }
    
    private Specification<E> createSpecification(@NotNull final Map<String, String[]> parameters) {
        Specification<E> specification = null;
        for (Map.Entry<String, String[]> e : parameters.entrySet()) {
            if (!pageableParameters.contains(e.getKey()))
                specification = specification == null ? createSpecification(e): specification.and(createSpecification(e));
        }
        return specification;
    }
    
    private SearchSpecification<E> createSpecification(@NotNull final Map.Entry<String, String[]> parameter) {
        return new SearchSpecification<E>(
                new SearchCriteria(
                        parameter.getKey(),
                        searchOperationMap.getOrDefault(parameter.getKey(), parameter.getValue().length > 1 ? IN : LIKE),
                        searchParameterFunctionMap.getOrDefault(parameter.getKey(), Arrays::asList).apply(parameter.getValue())));
    }
    
    private Pageable createPageable(@NotNull final Map<String, String[]> parameters) {
        return PageableCreator.createPageable(
                getIntParameterValue(parameters, RequestConstants.LIMIT),
                getIntParameterValue(parameters, RequestConstants.OFFSET),
                parameters.get(RequestConstants.SORT_BY));
    }
    
    private List<Object> createListIds(String[] ids) {
        return toObjectList(ids, UUID::fromString);
    }
    
    public List<Object> createLocalDateTimes(String[] dateTimes) {
        return toObjectList(dateTimes, DateTimeUtils::fromIso);
    }
    
    private List<Object> toObjectList(String[] ids, Function<String, Object> mapper) {
        return Arrays.stream(ids).map(mapper).collect(Collectors.toList());
    }
    
}