The `IsAuthorizedUser` annotation restricts access to methods whose `userId` argument
matches the authenticated principal's `id`. It can be applied to a method or a type

It belongs to the `com.tecknobit.equinoxbackend.environment.security.authorization.annotations` package

## Usage

Enable Spring Security method authorization in your application. The `userId` argument
must be resolvable by name, and the authenticated principal must expose an `id` property

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.security.authorization.annotations.IsAuthorizedUser;

    @IsAuthorizedUser
    public void updateUser(String userId) {
        // update the requested user
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.security.authorization.annotations.IsAuthorizedUser

    @IsAuthorizedUser
    fun updateUser(userId: String) {
        // update the requested user
    }
    ```

When applied to a type, the same authorization expression applies to its secured methods
