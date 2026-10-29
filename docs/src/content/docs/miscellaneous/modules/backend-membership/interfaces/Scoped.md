---
title: "Scoped"
slug: "miscellaneous/modules/backend-membership/interfaces/Scoped"
---

The `Scoped` interface provides reusable checks that require a user to belong to an identified resource

## Usage

`ProjectAccess` exposes its [membership service](/Equinox/miscellaneous/modules/backend-membership/services/MembersService/) through `getScopedMembersService()`.
The inherited checks use that service to resolve the user's membership in the requested project

```java
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxmisc.backendmembership.annotations.ScopeProvider;
import com.tecknobit.equinoxmisc.backendmembership.annotations.StandardMember;
import com.tecknobit.equinoxmisc.backendmembership.interfaces.Scoped;
import org.springframework.stereotype.Component;

@Component
@ScopeProvider
public class ProjectAccess implements Scoped<ProjectMemberDto, EquinoxUser, ProjectMember, ProjectMembersService> {

    private final ProjectMembersService membersService;

    public ProjectAccess(ProjectMembersService membersService) {
        this.membersService = membersService;
    }

    @Override
    public ProjectMembersService getScopedMembersService() {
        return membersService;
    }

    @StandardMember
    public ProjectMemberDto getProjectMember(String projectId, EquinoxUser user) {
        ProjectMemberDto member = requireIsScopedMember(projectId, user).convertToRelatedDTO();

        return member;
    }
}
```

The `getProjectMember` operation returns the member DTO only after confirming that the authenticated user belongs
to the requested project

```java
ProjectMemberDto member = projectAccess.getProjectMember(projectId, authenticatedUser);
String memberProjectId = member.getProjectId();
```

Both `requireIsScopedMember(entityId, user)` and `requireIsScopedMember(entityId, userId)` return the membership record
when found. If no record is found, they throw `EquinoxForbiddenException`, which corresponds to HTTP `403` when handled
by the [Equinox exception handler](/Equinox/backend/Exceptions/EquinoxGlobalExceptionHandler/)

`@ScopeProvider` and `@StandardMember` document the component's role. The explicit `requireIsScopedMember` call performs
the check using the repository lookup result. The user's identity comes from the application's
[authentication flow](/Equinox/backend/Security/); additional roles are evaluated by application logic
