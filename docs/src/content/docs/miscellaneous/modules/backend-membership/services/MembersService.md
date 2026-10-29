---
title: "MembersService"
slug: "miscellaneous/modules/backend-membership/services/MembersService"
---

The `MembersService` class retrieves membership records and converts membership entity lists to their related DTOs

## Usage

`ProjectMembersService` uses [ProjectMembersRepository](/Equinox/miscellaneous/modules/backend-membership/repositories/MembersRepository/) for its membership
lookups. The service provides the common retrieval and conversion behavior, while the repository defines the scope query

```java
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxmisc.backendmembership.services.MembersService;
import org.springframework.stereotype.Service;

@Service
public class ProjectMembersService extends MembersService<ProjectMemberDto, EquinoxUser, ProjectMember, ProjectMembersRepository> {

    public ProjectMembersService(ProjectMembersRepository membersRepository) {
        super(membersRepository);
    }
}
```

The service provides `getMember(entityId, user)` and `getMember(entityId, memberId)`. Both return the matching
membership entity, or `null` when the repository returns an empty result

Subclasses can also use the protected `convertToMemberDtos(members)` method. It invokes each entity's
`convertToRelatedDTO()`, preserves the input order, and returns an empty list for an empty input list

[Scoped](/Equinox/miscellaneous/modules/backend-membership/interfaces/Scoped/) builds on this lookup by rejecting access when no matching membership is found
