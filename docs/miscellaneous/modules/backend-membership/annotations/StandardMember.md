The `StandardMember` annotation marks methods intended for standard members

## Usage

In [ProjectAccess](../interfaces/Scoped.md), `getProjectMember` is a member operation. Its body checks membership
before converting the record to a DTO

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
    import com.tecknobit.equinoxmisc.backendmembership.annotations.StandardMember;

    @StandardMember
    public ProjectMemberDto getProjectMember(String projectId, EquinoxUser user) {
        ProjectMemberDto member = requireIsScopedMember(projectId, user).convertToRelatedDTO();

        return member;
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser
    import com.tecknobit.equinoxmisc.backendmembership.annotations.StandardMember

    @StandardMember
    fun getProjectMember(projectId: String, user: EquinoxUser): ProjectMemberDto {
        val member = requireIsScopedMember(projectId, user).convertToRelatedDTO()

        return member
    }
    ```
