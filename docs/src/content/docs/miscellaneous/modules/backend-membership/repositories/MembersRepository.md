---
title: "MembersRepository"
slug: "miscellaneous/modules/backend-membership/repositories/MembersRepository"
---

The `MembersRepository` interface provides Spring Data JPA persistence and a scoped membership lookup

## Usage

The concrete repository connects `getMember(entityId, memberId)` to the resource mapping in its
[membership entity](/Equinox/miscellaneous/modules/backend-membership/entities/EntityMember/). `ProjectMembersRepository` searches by the project identifier
and the associated user's identifier

The lookup must match both the scoped resource identifier and the user's identifier. This example uses JPQL entity
and property names, so the query does not depend on the physical table name

```java
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxmisc.backendmembership.repositories.MembersRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectMembersRepository extends MembersRepository<ProjectMemberDto, EquinoxUser, ProjectMember> {

    @Override
    @Query("""
            SELECT m FROM ProjectMember m
            WHERE m.projectId = :id AND m.member.id = :member_id
            """)
    Optional<ProjectMember> getMember(@Param("id") String entityId, @Param("member_id") String memberId);
}
```

`getMember` returns an empty `Optional` when no record matches. `memberId` is the user's identifier, not the primary
key of the membership record. The inherited `findById` method instead retrieves a membership by its own identifier

The example query checks the resource and user identifiers. Deletion-state and role conditions, when relevant,
also belong to the concrete query. The [soft deletion guide](/Equinox/backend/SoftDeletion/) explains the
deletion state inherited from `EquinoxItem`

[MembersService](/Equinox/miscellaneous/modules/backend-membership/services/MembersService/) uses this repository to retrieve membership records
