---
title: "Member"
slug: "miscellaneous/modules/backend-membership/dtos/Member"
---

The `Member` class transfers a member identifier and profile details

## Fields available

| Field | Getter | Purpose |
|-------|--------|---------|
| `id` | `getId()` | Identifier exposed for the member by the entity-to-DTO conversion |
| `profilePic` | `getProfilePic()` | Member's profile picture |
| `name` | `getName()` | Member's name |
| `surname` | `getSurname()` | Member's surname |
| `email` | `getEmail()` | Member's email address |

## Usage

The [ProjectMember entity](/Equinox/miscellaneous/modules/backend-membership/entities/EntityMember/) returns `ProjectMemberDto`, which adds the project identifier
to the associated user's identifier and profile details


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxcore.annotations.DTO;
import com.tecknobit.equinoxmisc.backendmembership.dtos.Member;

@DTO
public class ProjectMemberDto extends Member {

    private final String projectId;

    public ProjectMemberDto(String id, String profilePic, String name, String surname, String email,
                            String projectId) {
        super(id, profilePic, name, surname, email);
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxcore.annotations.DTO
import com.tecknobit.equinoxmisc.backendmembership.dtos.Member

@DTO
class ProjectMemberDto(
    id: String,
    profilePic: String?,
    name: String,
    surname: String,
    email: String,
    val projectId: String
) : Member(
    id = id,
    profilePic = profilePic,
    name = name,
    surname = surname,
    email = email
)
```

</div>

</div>

The DTO type is shared by the entity, repository, service, and `Scoped` declarations. The entity's
`convertToRelatedDTO()` implementation determines which values the DTO exposes, according to the
[DTOConvertible](/Equinox/backend/APIs/DtoConvertible/) contract
