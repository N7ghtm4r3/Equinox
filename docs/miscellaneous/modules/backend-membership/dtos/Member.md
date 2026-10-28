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

The [ProjectMember entity](../entities/EntityMember.md) returns `ProjectMemberDto`, which adds the project identifier
to the associated user's identifier and profile details

=== "Java"

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

=== "Kotlin"

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

The DTO type is shared by the entity, repository, service, and `Scoped` declarations. The entity's
`convertToRelatedDTO()` implementation determines which values the DTO exposes, according to the
[DTOConvertible](../../../../backend/APIs/DtoConvertible.md) contract
