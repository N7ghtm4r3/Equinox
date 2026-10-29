---
title: "StandardMember"
slug: "miscellaneous/modules/backend-membership/annotations/StandardMember"
---

The `StandardMember` annotation marks methods intended for standard members

## Usage

In [ProjectAccess](/Equinox/miscellaneous/modules/backend-membership/interfaces/Scoped/), `getProjectMember` is a member operation. Its body checks membership
before converting the record to a DTO


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxmisc.backendmembership.annotations.StandardMember;

@StandardMember
public ProjectMemberDto getProjectMember(String projectId, EquinoxUser user) {
    ProjectMemberDto member = requireIsScopedMember(projectId, user).convertToRelatedDTO();

    return member;
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser
import com.tecknobit.equinoxmisc.backendmembership.annotations.StandardMember

@StandardMember
fun getProjectMember(projectId: String, user: EquinoxUser): ProjectMemberDto {
    val member = requireIsScopedMember(projectId, user).convertToRelatedDTO()

    return member
}
```

</div>

</div>
