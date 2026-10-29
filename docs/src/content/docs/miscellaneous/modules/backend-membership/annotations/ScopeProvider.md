---
title: "ScopeProvider"
slug: "miscellaneous/modules/backend-membership/annotations/ScopeProvider"
---

The `ScopeProvider` annotation marks types and record components that provide a membership scope

## Usage

`ProjectAccess` provides membership checks for projects through `ProjectMembersService`, using the same entity
and DTO as the [Scoped example](/Equinox/miscellaneous/modules/backend-membership/interfaces/Scoped/)


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


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

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


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

</div>

</div>
