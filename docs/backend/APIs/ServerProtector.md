`ServerProtector` manages the shared secret used to protect an Equinox backend. It is available in
`com.tecknobit.equinoxbackend.configuration`.

The protector stores a Base64-encoded SHA-256 digest under the `server_secret` key in a node of the
current user's Java preferences. Clients send the original secret; `serverSecretMatches` hashes
the supplied value and compares it with the stored digest.

## Enable protection

Add a `server_protector` section to the backend's
[`equinox.config`](../Environment.md#additional-backend-setup):

```json
{
  "server_protector": {
    "storage_path": "my-backend",
    "save_message": "Save this secret for %s"
  }
}
```

`storage_path` identifies the preferences node. `save_message` is appended to newly generated secrets;
its format arguments come from the values passed after `args` during environment initialization.

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;

    EquinoxEnvironmentService.getInstance().initEquinoxEnvironment(args, "My backend");
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService

    EquinoxEnvironmentService.getInstance().initEquinoxEnvironment(args, "My backend")
    ```

Call initialization before starting Spring Boot, as shown in the
[environment launch guide](../Environment.md#out-of-the-box-launch). Omitting `server_protector`
disables protection in the supplied environment.

### First launch

When no digest is stored, initialization generates a new secret, stores its digest, and throws
[SaveData](../Exceptions/SaveData.md) with the clear-text secret and configured message. The environment
service wraps this exception in `RuntimeException`, interrupting startup so the administrator can save
the value. Restart with the same preferences node after saving it.

The preferences belong to the operating-system user running the backend. A different user or node
can therefore have a different stored secret.

## Verify a secret

After initialization, retrieve the protector from the environment service:

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.configuration.ServerProtector;

    EquinoxEnvironmentService environment = EquinoxEnvironmentService.getInstance();
    ServerProtector protector = environment.getServerProtector();

    boolean matches = protector != null && protector.serverSecretMatches(submittedSecret);
    ```

=== "Kotlin"

    ```kotlin
    val environment = EquinoxEnvironmentService.getInstance()
    val matches = environment.getServerProtector()?.serverSecretMatches(submittedSecret) == true
    ```

The getter returns `null` before initialization or when protection is disabled. A protector returns
`false` for a null candidate, a missing stored digest, or an unavailable SHA-256 algorithm.

For request DTOs, use the [ServerSecret constraint](../Validation/ServerSecret.md) to apply the
configured protection rule together with the supplied non-blank validation.

## Manage the stored secret

Pass a command to the backend process so it reaches `initEquinoxEnvironment(args)`:

| Command | Effect |
| --- | --- |
| `rss` | Generates a replacement secret and interrupts startup with `SaveData` |
| `dss` | Deletes the stored digest without interrupting execution |
| `dssi` | Deletes the stored digest and throws `RuntimeException` to interrupt execution |

```bash
java -jar backend.jar rss
```

Saving the replacement and restarting is required after `rss`. After `dss`, candidates cannot match
until a new secret is generated. On the next launch, an absent digest triggers first-launch generation.
When the digest is already absent at startup, generation happens before command processing.

The same operations are available through `generateServerSecret()`, `recreateServerSecret()`,
`deleteServerSecret()`, and `deleteServerSecret(true)`.

## Direct use

If your backend does not use the supplied environment, construct a protector with its preferences
node and administrator message, then call `launch(args)`. Handle `SaveData` before continuing startup
and handle or propagate `NoSuchAlgorithmException` in Java.

=== "Java"

    ```java
    ServerProtector protector = new ServerProtector("my-backend", "Save this secret for My backend");
    protector.launch(args);
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.configuration.ServerProtector

    val protector = ServerProtector("my-backend", "Save this secret for My backend")
    protector.launch(args)
    ```

When the environment owns the protector, use its retained instance instead of launching another one.
