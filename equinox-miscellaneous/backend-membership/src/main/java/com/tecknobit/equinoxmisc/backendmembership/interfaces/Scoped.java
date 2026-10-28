package com.tecknobit.equinoxmisc.backendmembership.interfaces;

import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxForbiddenException;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxmisc.backendmembership.annotations.ScopeProvider;
import com.tecknobit.equinoxmisc.backendmembership.dtos.Member;
import com.tecknobit.equinoxmisc.backendmembership.entities.EntityMember;
import com.tecknobit.equinoxmisc.backendmembership.services.MembersService;

/**
 * The {@code Scoped} interface defines the contract to require membership in an identified resource
 *
 * <p>Implementations provide the service used to resolve membership. The default checks return the membership
 * record or reject access with {@link EquinoxForbiddenException} when no record is found
 *
 * @param <MD> The DTO type produced by membership entities
 * @param <U>  The type of users whose membership is checked
 * @param <M>  The membership entity type
 * @param <S>  The service type used to retrieve membership records
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
@ScopeProvider
public interface Scoped<MD extends Member, U extends EquinoxUser, M extends EntityMember<MD, U>,
        S extends MembersService<MD, U, M, ?>> {

    /**
     * Method used to get the service that resolves membership within a resource
     *
     * @return the membership service as {@code S}
     */
    S getScopedMembersService();

    /**
     * Method used to require that the user belongs to the identified resource
     *
     * @param entityId The identifier of the resource defining the membership scope
     * @param user     The user whose identifier is used to check membership
     *
     * @return the matching membership record as {@code M}
     * @throws EquinoxForbiddenException if no membership record exists for the resource and user
     */
    default M requireIsScopedMember(String entityId, U user) {
        return requireIsScopedMember(entityId, user.getId());
    }

    /**
     * Method used to require that the user belongs to the identified resource
     *
     * @param entityId The identifier of the resource defining the membership scope
     * @param userId   The identifier of the user whose membership is checked
     *
     * @return the matching membership record as {@code M}
     * @throws EquinoxForbiddenException if no membership record exists for the resource and user
     */
    default M requireIsScopedMember(String entityId, String userId) {
        S service = getScopedMembersService();
        M member = service.getMember(entityId, userId);
        if(member == null)
            throw new EquinoxForbiddenException();

        return member;
    }

}