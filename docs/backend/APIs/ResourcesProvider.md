Provides an easy way to handle the static resources used by the backend such images, assets, etc

## Usage

### Initialize The Provider

=== "Java"

    ```java
    ResourcesProvider resourcesProvider = new ResourcesProvider(
        "containerFolder_1",
        List.of("subfolder_1", "subfolder_2")
    );
    ```

=== "Kotlin"

    ```kotlin
    val resourcesProvider = ResourcesProvider(
        "containerFolder_1",
        listOf("subfolder_1", "subfolder_2")
    )
    ```

### Create Directories

Before is required to create the container directory which will contain other directories:

=== "Java"

    ```java
    resourcesProvider.createContainerDirectory();
    ```

=== "Kotlin"

    ```kotlin
    resourcesProvider.createContainerDirectory()
    ```

Then can be created the subdirectories:

=== "Java"

    ```java
    resourcesProvider.createSubDirectories();
    ```

=== "Kotlin"

    ```kotlin
    resourcesProvider.createSubDirectories()
    ```

Final structure:

``` bash
containerFolder_1
├── subfolder_1
└── subfolder_2
```