package com.tecknobit.equinoxmisc.backendmembership.services;

import com.tecknobit.equinoxbackend.apis.batch.EquinoxItemsHelper;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxcore.annotations.Returner;
import com.tecknobit.equinoxmisc.backendmembership.dtos.Member;
import com.tecknobit.equinoxmisc.backendmembership.entities.EntityMember;
import com.tecknobit.equinoxmisc.backendmembership.repositories.MembersRepository;
import jdk.jfr.Experimental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MembersService} class is useful to retrieve scoped membership records and convert them to DTOs
 *
 * <p>Concrete services supply a repository whose lookup query matches their scoped entity mapping
 *
 * @param <MD> The DTO type produced by membership entities
 * @param <U>  The type of users associated with membership records
 * @param <M>  The membership entity type
 * @param <R>  The repository type used to retrieve membership records
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxItemsHelper
 *
 */
@Service
@Experimental
public abstract class MembersService<MD extends Member, U extends EquinoxUser, M extends EntityMember<MD, U>, R
        extends MembersRepository<MD, U, M>> extends EquinoxItemsHelper {

    /**
     * {@code membersRepository} the repository used to retrieve scoped membership records
     */
    protected final R membersRepository;

    /**
     * Constructor to init the {@link MembersService} class
     *
     * @param membersRepository The repository used to retrieve scoped membership records
     */
    @Autowired
    protected MembersService(R membersRepository) {
        this.membersRepository = membersRepository;
    }

    /**
     * Method used to retrieve a user's membership record within the identified resource
     *
     * @param entityId The identifier of the resource defining the membership scope
     * @param user     The user whose identifier is used to retrieve the membership record
     *
     * @return the matching membership record, or {@code null} when none exists, as {@code M}
     */
    public M getMember(String entityId, U user) {
        return getMember(entityId, user.getId());
    }

    /**
     * Method used to retrieve a user's membership record within the identified resource
     *
     * @param entityId The identifier of the resource defining the membership scope
     * @param memberId The identifier of the user associated with the membership record
     *
     * @return the matching membership record, or {@code null} when none exists, as {@code M}
     */
    public M getMember(String entityId, String memberId) {
        return membersRepository.getMember(entityId, memberId).orElse(null);
    }

    /**
     * Method used to convert membership records to their related DTOs
     *
     * <p>The returned list preserves the input order and is empty when the input list is empty
     *
     * @param members The membership records to convert
     *
     * @return the converted membership records as {@link List} of {@code MD}
     */
    @Returner
    protected List<MD> convertToMemberDtos(List<M> members) {
        List<MD> dtos = new ArrayList<>();

        for (M member : members) {
            MD dto = member.convertToRelatedDTO();
            dtos.add(dto);
        }

        return dtos;
    }

}
