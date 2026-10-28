package com.tecknobit.equinoxmisc.backendmembership.repositories;

import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxcore.annotations.Structure;
import com.tecknobit.equinoxmisc.backendmembership.dtos.Member;
import com.tecknobit.equinoxmisc.backendmembership.entities.EntityMember;
import jdk.jfr.Experimental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.IDENTIFIER_KEY;
import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.MEMBER_IDENTIFIER_KEY;

/**
 * The {@code MembersRepository} interface defines the contract to persist and retrieve scoped membership records
 *
 * <p>Concrete repositories must supply the query or implementation of {@link #getMember(String, String)} using
 * their entity mapping. This base repository does not define a scope-specific query
 *
 * @param <MD> The DTO type produced by membership entities
 * @param <U>  The type of users associated with membership records
 * @param <M>  The membership entity type
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
@Experimental
@Structure
@Repository
@NoRepositoryBean
public interface MembersRepository<MD extends Member, U extends EquinoxUser, M extends EntityMember<MD, U>>
        extends JpaRepository<M, String> {

    /**
     * Method used to retrieve a user's membership record within the identified resource
     *
     * <p>The concrete query or implementation must restrict the lookup to both identifiers
     *
     * @param entityId The identifier of the resource defining the membership scope
     * @param memberId The identifier of the associated user
     *
     * @return the matching membership record, or an empty result when none exists, as {@link Optional} of {@code M}
     */
    Optional<M> getMember(
            @Param(IDENTIFIER_KEY) String entityId,
            @Param(MEMBER_IDENTIFIER_KEY) String memberId
    );

}
