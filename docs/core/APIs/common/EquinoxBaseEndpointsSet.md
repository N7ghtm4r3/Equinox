Provides the shared API base path and user endpoint constants for Equinox clients and backends.

## Provided constants

| Constant | Value |
|----------|-------|
| `BASE_EQUINOX_ENDPOINT` | `/api/v2/` |
| `SIGN_UP_ENDPOINT` | `users/signUp` |
| `SIGN_IN_ENDPOINT` | `users/signIn` |
| `DYNAMIC_ACCOUNT_DATA_ENDPOINT` | `/dynamicAccountData` |
| `CHANGE_PROFILE_PIC_ENDPOINT` | `/changeProfilePic` |
| `CHANGE_EMAIL_ENDPOINT` | `/changeEmail` |
| `CHANGE_PASSWORD_ENDPOINT` | `/changePassword` |
| `CHANGE_LANGUAGE_ENDPOINT` | `/changeLanguage` |

## Usage

Append the sign-up or sign-in endpoint directly to the base path:

=== "Java"

    ```java
    import static com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.BASE_EQUINOX_ENDPOINT;
    import static com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.SIGN_UP_ENDPOINT;

    String signUpPath = BASE_EQUINOX_ENDPOINT + SIGN_UP_ENDPOINT;
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.BASE_EQUINOX_ENDPOINT
    import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.SIGN_UP_ENDPOINT

    val signUpPath = BASE_EQUINOX_ENDPOINT + SIGN_UP_ENDPOINT
    ```

Both examples produce `/api/v2/users/signUp`. The remaining user endpoints include a leading slash and append to a
user-specific route.
