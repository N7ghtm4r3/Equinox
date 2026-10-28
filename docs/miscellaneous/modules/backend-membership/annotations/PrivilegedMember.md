The `PrivilegedMember` annotation marks methods intended for privileged members

## Usage

The `owner` label identifies the intended role for the project update operation

=== "Java"

    ```java
    import com.tecknobit.equinoxmisc.backendmembership.annotations.PrivilegedMember;

    @PrivilegedMember(role = "owner")
    public void updateProject(String projectId) {
        // check membership and the required role before updating the project
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxmisc.backendmembership.annotations.PrivilegedMember

    @PrivilegedMember(role = "owner")
    fun updateProject(projectId: String) {
        // check membership and the required role before updating the project
    }
    ```

The `role` label defaults to an empty string. The annotation does not define a role model or interpret the label
