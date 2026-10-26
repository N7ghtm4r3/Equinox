package com.tecknobit.equinoxmisc.backendmembership.interfaces;

import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import com.tecknobit.equinoxmisc.backendmembership.entities.EntityMember;

@FutureEquinoxApi(
        releaseVersion = "2.0.0",
        additionalNotes = """
                - Make general purpose using ItemMember instead LaniakeaItemMember
                - Think about the same for S
                - To docu
                """
)
// TODO: 13/06/2026 TO ANNOTATE WITH DEDICATED ANNOTATION
public interface Scoped<U extends EquinoxUser, M extends EntityMember<U>, S> {

    S getScopedMembersService();

    default M requireIsPrivilegedScopedMember(String entityId, U user) {
        return requireIsPrivilegedScopedMember(entityId, user.getId());
    }

    default M requireIsPrivilegedScopedMember(String entityId, String memberId) {
        return null;
    }

    default M requireIsScopedMember(String entityId, U user) {
        return requireIsScopedMember(entityId, user.getId());
    }

    default M requireIsScopedMember(String entityId, String userId) {
        return null;
    }

}