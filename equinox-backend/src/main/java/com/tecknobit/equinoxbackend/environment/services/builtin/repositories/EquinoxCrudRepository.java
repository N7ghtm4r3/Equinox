package com.tecknobit.equinoxbackend.environment.services.builtin.repositories;

import com.tecknobit.equinoxbackend.environment.services.builtin.entities.EquinoxItem;
import com.tecknobit.equinoxcore.annotations.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.DELETED_AT_KEY;
import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.IDENTIFIER_KEY;

@Structure
@Repository
@NoRepositoryBean
public interface EquinoxCrudRepository<T extends EquinoxItem> extends JpaRepository<T, String> {

    void softDelete(
            @Param(IDENTIFIER_KEY) String id,
            @Param(DELETED_AT_KEY) long deletedAt
    );

    void restoreSoftDeletedEntity(
            @Param(IDENTIFIER_KEY) String id
    );

}
