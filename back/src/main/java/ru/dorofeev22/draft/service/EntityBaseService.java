package ru.dorofeev22.draft.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.dorofeev22.draft.domain.BaseEntity;
import ru.dorofeev22.draft.repository.BaseRepository;
import ru.dorofeev22.draft.service.model.PageModel;
import ru.dorofeev22.draft.service.model.PageModelHelper;

public abstract class EntityBaseService<E extends BaseEntity, C, R> {

    @Autowired
    protected ModelMapper modelMapper;

    protected abstract BaseRepository<E> getRepository();
    protected abstract Class<E> getEntityClass();
    protected abstract Class<R> getResponseClass();

    public E create(C creationModel) {
        return getRepository().save(modelMapper.map(creationModel, getEntityClass()));
    }

    public R createAndGetResponse(final C creationModel) {
        return modelMapper.map(create(creationModel), getResponseClass());
    }

    public PageModel<R> find(Pageable pageable) {
        return PageModelHelper.createPageModel(getRepository().findAll(pageable), getResponseClass(), modelMapper::map);
    }

}
