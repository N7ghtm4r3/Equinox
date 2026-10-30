`ResourcesUtils` reads resources packaged with a backend, such as configuration files and templates.
It is available in `com.tecknobit.equinoxbackend.apis.resources`.

The context class supplies the class loader used to find the resource. In the examples below,
`Launcher` is your backend's application class. Resource names are relative to the classpath root,
so a file at `src/main/resources/templates/welcome.txt` is requested as `templates/welcome.txt`.

Use [ResourcesProvider](ResourcesProvider.md) to create directories for files served by the backend.
Use `ResourcesUtils` to read files already included in the application's classpath.

## Read text

An instance retains the context class for repeated lookups. `getResourceContent` decodes the resource
as UTF-8 and closes the stream after reading it.

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.apis.resources.ResourcesUtils;

    ResourcesUtils<Launcher> resources = new ResourcesUtils<>(Launcher.class);
    String template = resources.getResourceContent("templates/welcome.txt");
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.apis.resources.ResourcesUtils

    val resources = ResourcesUtils(Launcher::class.java)
    val template = resources.getResourceContent("templates/welcome.txt")
    ```

For a single read, use the static overload:

=== "Java"

    ```java
    String template = ResourcesUtils.getResourceContent("templates/welcome.txt", Launcher.class);
    ```

=== "Kotlin"

    ```kotlin
    val template = ResourcesUtils.getResourceContent("templates/welcome.txt", Launcher::class.java)
    ```

The [backend configuration](../Environment.md#additional-backend-setup) uses this API to load
`equinox.config` from the classpath.

## Read a stream

Use `getResourceStream` when another API needs the original bytes. The caller owns the returned stream
and must close it, including when reading fails.

=== "Java"

    ```java
    import java.io.InputStream;

    try (InputStream resource = resources.getResourceStream("images/logo.png")) {
        byte[] image = resource.readAllBytes();
    }
    ```

=== "Kotlin"

    ```kotlin
    val image = resources.getResourceStream("images/logo.png").use { resource ->
        resource.readAllBytes()
    }
    ```

Content and stream reads work with resources packaged inside a JAR. A missing resource causes
`IOException`; text reads can also fail while reading the stream.

## Copy a resource to a file

The copy methods preserve the original bytes, so they also support binary resources. Each method
has an instance form and a static form accepting the context class.

| Method | Result |
| --- | --- |
| `getResourceFileCopy(pathname)` | Copies the resource to the same path in the process working directory |
| `getResourceFileRuntimeCopy(pathname)` | Creates that copy and schedules it for deletion when the JVM exits |
| `getResourceFile(pathname)` | Resolves an unpacked classpath resource directly as a file |

For a resource named `welcome.txt` at the classpath root:

=== "Java"

    ```java
    import java.io.File;

    File copy = resources.getResourceFileRuntimeCopy("welcome.txt");
    ```

=== "Kotlin"

    ```kotlin
    val copy = resources.getResourceFileRuntimeCopy("welcome.txt")
    ```

Both copy methods overwrite an existing destination. Parent directories must already exist.
The runtime copy uses the supplied path; it is not a uniquely named file in the system temporary directory.

`getResourceFile` does not extract JAR entries. Use a stream or a copy when the backend runs from a JAR.
