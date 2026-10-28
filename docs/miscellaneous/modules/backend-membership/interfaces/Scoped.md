The `Scoped` interface provides reusable checks that require a user to belong to an identified resource

## Usage

`ProjectAccess` exposes its [membership service](../services/MembersService.md) through `getScopedMembersService()`.
The inherited checks use that service to resolve the user's membership in the requested project

```java
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxmisc.backendmembership.annotations.ScopeProvider;
import com.tecknobit.equinoxmisc.backendmembership.annotations.StandardMember;
import com.tecknobit.equinoxmisc.backendmembership.dtos.Member;
import com.tecknobit.equinoxmisc.backendmembership.interfaces.Scoped;
import org.springframework.stereotype.Component;

@Component
@ScopeProvider
public class ProjectAccess implements Scoped<Member, EquinoxUser, ProjectMember, ProjectMembersService> {

    private final ProjectMembersService membersService;

    public ProjectAccess(ProjectMembersService membersService) {
        this.membersService = membersService;
    }

    @Override
    public ProjectMembersService getScopedMembersService() {
        return membersService;
    }

    @StandardMember
    public Member getProjectMember(String projectId, EquinoxUser user) {
        Member member = requireIsScopedMember(projectId, user).convertToRelatedDTO();

        return member;
    }
}
```

The `getProjectMember` operation returns the member DTO only after confirming that the authenticated user belongs
to the requested project

```java
Member member = projectAccess.getProjectMember(projectId, authenticatedUser);
```

Both `requireIsScopedMember(entityId, user)` and `requireIsScopedMember(entityId, userId)` return the membership record
when found. If no record is found, they throw `EquinoxForbiddenException`, which corresponds to HTTP `403` when handled
by the [Equinox exception handler](../../../../backend/Exceptions/EquinoxGlobalExceptionHandler.md)

`@ScopeProvider` and `@StandardMember` document the component's role. The explicit `requireIsScopedMember` call performs
the check using the repository lookup result. The user's identity comes from the application's
[authentication flow](../../../../backend/Security/index.md); additional roles are evaluated by application logic
