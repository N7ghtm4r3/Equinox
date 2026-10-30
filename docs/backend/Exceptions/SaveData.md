`SaveData` interrupts an initialization operation while exposing content that an administrator must
save before continuing. It is a checked exception in `com.tecknobit.equinoxbackend.exceptions`.

The [server protector](../APIs/ServerProtector.md) throws it after storing the digest of a newly
generated secret. The content includes the clear-text secret and the configured administrator message.
This is an initialization signal, separate from the
[HTTP exceptions](index.md) handled by `EquinoxGlobalExceptionHandler`.

## Read the content

`getMessage()` includes a save reminder followed by the supplied content. `getSaveableContent()`
returns the original value without adding that reminder.

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.exceptions.SaveData;

    SaveData signal = new SaveData("value-to-save");
    String content = signal.getSaveableContent(); // "value-to-save"
    String message = signal.getMessage(); // Includes the save reminder
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.exceptions.SaveData

    val signal = SaveData("value-to-save")
    val content: String = signal.getSaveableContent() // "value-to-save"
    val message = signal.message // Includes the save reminder
    ```

The method's generic type must match the supplied value. It does not deserialize or convert the
content. For a signal raised by `ServerProtector`, the string includes both the generated secret and
the save message; it is not a getter for the secret alone.

## Handle environment initialization

Calling `ServerProtector.launch(args)` directly can throw `SaveData`. The supplied
[environment service](../APIs/EquinoxEnvironmentService.md) wraps initialization exceptions in
`RuntimeException`, so inspect the cause when handling that entry point:

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;

    try {
        EquinoxEnvironmentService.getInstance().initEquinoxEnvironment(args);
    } catch (RuntimeException exception) {
        if (exception.getCause() instanceof SaveData signal) {
            System.out.println(signal.getMessage());
        }
        throw exception;
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService

    try {
        EquinoxEnvironmentService.getInstance().initEquinoxEnvironment(args)
    } catch (exception: RuntimeException) {
        val signal = exception.cause
        if (signal is SaveData) {
            println(signal.message)
        }
        throw exception
    }
    ```

The examples retain the startup interruption after displaying the message. Save the generated value
and restart the backend with the same preferences node. A normal launch then uses the stored digest.

## Constructors

| Constructor | Purpose |
| --- | --- |
| `SaveData(message)` | Retains the content and includes it after the save reminder |
| `SaveData(message, cause)` | Also retains an originating failure |
| `SaveData(message, cause, enableSuppression, writableStackTrace)` | Also configures exception suppression and stack-trace recording |
