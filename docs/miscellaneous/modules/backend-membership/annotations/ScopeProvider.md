The `ScopeProvider` annotation marks types and record components that provide a membership scope

## Usage

`ProjectAccess` provides membership checks for projects through `ProjectMembersService`, using the same entity
and DTO as the [Scoped example](../interfaces/Scoped.md)

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
    import com.tecknobit.equinoxmisc.backendmembership.annotations.ScopeProvider;
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
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser
    import com.tecknobit.equinoxmisc.backendmembership.annotations.ScopeProvider
    import com.tecknobit.equinoxmisc.backendmembership.interfaces.Scoped
    import org.springframework.stereotype.Component

    @Component
    @ScopeProvider
    class ProjectAccess(
        private val membersService: ProjectMembersService
    ) : Scoped<ProjectMemberDto, EquinoxUser, ProjectMember, ProjectMembersService> {

        override fun getScopedMembersService(): ProjectMembersService {
            return membersService
        }
    }
    ```
