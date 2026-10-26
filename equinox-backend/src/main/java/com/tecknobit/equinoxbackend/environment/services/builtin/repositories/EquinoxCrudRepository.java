package com.tecknobit.equinoxbackend.environment.services.builtin.repositories;

import com.tecknobit.equinoxbackend.environment.services.builtin.entities.EquinoxItem;
import com.tecknobit.equinoxcore.annotations.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.DELETED_AT_KEY;
import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.IDENTIFIER_KEY;

/**
 * The {@code EquinoxCrudRepository} interface defines the contract to persist entities and manage soft deletion
 *
 * @param <T> The entity type managed by the repository
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Structure
@Repository
@NoRepositoryBean
public interface EquinoxCrudRepository<T extends EquinoxItem> extends JpaRepository<T, String> {

    /**
     * Method used to mark an entity as soft deleted without removing its stored data
     *
     * @param id        The identifier of the entity
     * @param deletedAt The deletion timestamp in milliseconds since the Unix epoch
     */
    void softDelete(
            @Param(IDENTIFIER_KEY) String id,
            @Param(DELETED_AT_KEY) long deletedAt
    );

    /**
     * Method used to restore an entity by resetting its deletion timestamp to {@code -1}
     *
     * @param id The identifier of the entity to restore
     */
    void restoreSoftDeletedEntity(
            @Param(IDENTIFIER_KEY) String id
    );

}
