The `StandardMember` annotation marks methods intended for standard members

## Usage

In [ProjectAccess](../interfaces/Scoped.md), `getProjectMember` is a member operation. Its body checks membership
before converting the record to a DTO

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
    import com.tecknobit.equinoxmisc.backendmembership.annotations.StandardMember;
    import com.tecknobit.equinoxmisc.backendmembership.dtos.Member;

    @StandardMember
    public Member getProjectMember(String projectId, EquinoxUser user) {
        Member member = requireIsScopedMember(projectId, user).convertToRelatedDTO();

        return member;
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser
    import com.tecknobit.equinoxmisc.backendmembership.annotations.StandardMember
    import com.tecknobit.equinoxmisc.backendmembership.dtos.Member

    @StandardMember
    fun getProjectMember(projectId: String, user: EquinoxUser): Member {
        val member = requireIsScopedMember(projectId, user).convertToRelatedDTO()

        return member
    }
    ```
