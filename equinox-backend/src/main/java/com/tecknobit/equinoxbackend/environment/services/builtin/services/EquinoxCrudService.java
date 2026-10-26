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

/**
 * The {@code EquinoxCrudService} class is useful to manage entity deletion, restoration, and access checks
 *
 * <p>Missing entities and entities whose deletion timestamp differs from {@code -1} are rejected by
 * the guarded callbacks. Soft deletion retains stored data; hard deletion removes the entity
 *
 * @param <T> The entity type managed by the service
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public abstract class EquinoxCrudService<T extends EquinoxItem> {

    /**
     * {@code crudRepository} the repository used to retrieve, delete, and restore entities
     */
    private final EquinoxCrudRepository<T> crudRepository;

    /**
     * Constructor to init the {@link EquinoxCrudService} class
     *
     * @param crudRepository The repository managing the entities
     */
    public EquinoxCrudService(EquinoxCrudRepository<T> crudRepository) {
        this.crudRepository = crudRepository;
    }

    /**
     * Method used to run an action for an existing entity that is not soft deleted
     *
     * @param id    The identifier of the entity to retrieve
     * @param usage The action receiving the available entity
     *
     * @throws EquinoxForbiddenException when the entity is missing or soft deleted
     */
    @Wrapper
    protected void requireEntityNotSoftDeleted(String id, Consumer<T> usage) {
        requireEntityNotSoftDeleted(id, entity -> {
            usage.accept(entity);
            return null;
        });
    }

    /**
     * Method used to compute a result from an existing entity that is not soft deleted
     *
     * @param id    The identifier of the entity to retrieve
     * @param usage The function receiving the available entity
     * @param <R>   The result type
     *
     * @return the callback result as {@link R}
     * @throws EquinoxForbiddenException when the entity is missing or soft deleted
     */
    @Wrapper
    protected <R> R requireEntityNotSoftDeleted(String id, Function<T, R> usage) {
        T entity = getEntity(id);

        return requireEntityNotSoftDeleted(entity, () -> usage.apply(entity));
    }

    /**
     * Method used to compute a result only when the supplied entity is present and not soft deleted
     *
     * @param entity The entity to check, or {@code null} when missing
     * @param usage  The callback evaluated only when the entity passes the check
     * @param <R>    The result type
     *
     * @return the callback result as {@link R}
     * @throws EquinoxForbiddenException when the entity is {@code null} or soft deleted
     */
    protected <R> R requireEntityNotSoftDeleted(T entity, Supplier<R> usage) {
        if(isSoftDeleted(entity))
            throw new EquinoxForbiddenException();

        return usage.get();
    }

    /**
     * Method used to check whether an entity is missing or soft deleted
     *
     * @param id The identifier of the entity to retrieve
     *
     * @return whether the entity is missing or its deletion timestamp differs from {@code -1} as {@code boolean}
     */
    @Wrapper
    protected boolean isSoftDeleted(String id) {
        T entity = getEntity(id);

        return isSoftDeleted(entity);
    }

    /**
     * Method used to check whether the supplied entity is missing or soft deleted
     *
     * @param entity The entity to check, or {@code null} when missing
     *
     * @return whether the entity is {@code null} or its deletion timestamp differs from {@code -1} as {@code boolean}
     */
    @Returner
    protected boolean isSoftDeleted(T entity) {
        return entity == null || entity.getDeletedAt() != -1L;
    }

    /**
     * Method used to retrieve an entity by its identifier, including soft deleted entities
     *
     * @param id The identifier of the entity to retrieve
     *
     * @return the matching entity, or {@code null} when missing, as {@link T}
     */
    @EntityRetriever
    private T getEntity(String id) {
        return crudRepository.findById(id).orElse(null);
    }

    /**
     * Method used to delete an entity permanently or mark it as soft deleted in a transaction
     *
     * @param id         The identifier of the entity to delete
     * @param hardDelete Whether to remove the entity permanently instead of recording a deletion timestamp
     */
    @Transactional
    public void deleteEntity(String id, boolean hardDelete) {
        if(hardDelete)
            hardDelete(id);
        else
            softDelete(id);
    }

    /**
     * Method used to permanently remove an entity from the repository
     *
     * @param id The identifier of the entity to delete
     */
    protected void hardDelete(String id) {
        crudRepository.deleteById(id);
    }

    /**
     * Method used to mark an entity as soft deleted using the current time in milliseconds since the Unix epoch
     *
     * @param id The identifier of the entity to mark as deleted
     */
    protected void softDelete(String id) {
        long deletedAt = System.currentTimeMillis();

        crudRepository.softDelete(id, deletedAt);
    }

    /**
     * Method used to restore an entity in the repository and reset the supplied instance to the active state
     *
     * @param entity The non-null entity whose deletion timestamp is reset to {@code -1}
     */
    @Transactional
    protected void restoreSoftDeletedEntity(T entity) {
        String entityId = entity.getId();

        crudRepository.restoreSoftDeletedEntity(entityId);
        entity.setDeletedAt(-1L);
    }

}
