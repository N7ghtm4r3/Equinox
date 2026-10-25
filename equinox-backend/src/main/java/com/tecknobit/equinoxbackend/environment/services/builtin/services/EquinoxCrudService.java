package com.tecknobit.equinoxbackend.environment.services.builtin.services;

import com.tecknobit.equinoxbackend.annotations.EntityRetriever;
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxForbiddenException;
import com.tecknobit.equinoxbackend.environment.services.builtin.entities.EquinoxItem;
import com.tecknobit.equinoxbackend.environment.services.builtin.repositories.EquinoxCrudRepository;
import com.tecknobit.equinoxcore.annotations.Returner;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import jakarta.transaction.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class EquinoxCrudService<T extends EquinoxItem> {

    private final EquinoxCrudRepository<T> crudRepository;

    public EquinoxCrudService(EquinoxCrudRepository<T> crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Wrapper
    protected void requireEntityNotSoftDeleted(String id, Consumer<T> usage) {
        requireEntityNotSoftDeleted(id, entity -> {
            usage.accept(entity);
            return null;
        });
    }

    @Wrapper
    protected <R> R requireEntityNotSoftDeleted(String id, Function<T, R> usage) {
        T entity = getEntity(id);

        return requireEntityNotSoftDeleted(entity, () -> usage.apply(entity));
    }

    protected <R> R requireEntityNotSoftDeleted(T entity, Supplier<R> usage) {
        if(isSoftDeleted(entity))
            throw new EquinoxForbiddenException();

        return usage.get();
    }

    @Wrapper
    protected boolean isSoftDeleted(String id) {
        T entity = getEntity(id);

        return isSoftDeleted(entity);
    }

    @Returner
    protected boolean isSoftDeleted(T entity) {
        return entity == null || entity.getDeletedAt() != -1L;
    }

    @EntityRetriever
    private T getEntity(String id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteEntity(String id, boolean hardDelete) {
        if(hardDelete)
            hardDelete(id);
        else
            softDelete(id);
    }

    protected void hardDelete(String id) {
        crudRepository.deleteById(id);
    }

    protected void softDelete(String id) {
        long deletedAt = System.currentTimeMillis();

        crudRepository.softDelete(id, deletedAt);
    }

    @Transactional
    protected void restoreSoftDeletedEntity(T entity) {
        String entityId = entity.getId();

        crudRepository.restoreSoftDeletedEntity(entityId);
        entity.setDeletedAt(-1L);
    }

}
