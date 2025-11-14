These APIs are useful to handle the responsive layouts from specific components creation to assign specific
value based on the
current [window-size-class](https://www.jetbrains.com/help/kotlin-multiplatform-dev/whats-new-compose-170.html#material3-material3-window-size-class)

## Kit composition

### Responsive classes

| Class                      | Description                                                                                                             |
|----------------------------|-------------------------------------------------------------------------------------------------------------------------|
| `EXPANDED_CONTENT`         | Represents the content to be displayed on a device that belongs to an expanded width class and an expanded height class |
| `EXPANDED_MEDIUM_CONTENT`  | Represents the content to be displayed on a device that belongs to an expanded width class and a medium height class    |
| `EXPANDED_COMPACT_CONTENT` | Represents the content to be displayed on a device that belongs to an expanded width class and a compact height class   |
| `MEDIUM_CONTENT`           | Represents the content to be displayed on a device that belongs to a medium width class and a medium height class       |
| `MEDIUM_EXPANDED_CONTENT`  | Represents the content to be displayed on a device that belongs to a medium width class and an expanded height class    |
| `MEDIUM_COMPACT_CONTENT`   | Represents the content to be displayed on a device that belongs to a medium width class and a compact height class      |
| `COMPACT_CONTENT`          | Represents the content to be displayed on a device that belongs to a compact width class and a compact height class     |
| `COMPACT_EXPANDED_CONTENT` | Represents the content to be displayed on a device that belongs to a compact width class and an expanded height class   |
| `COMPACT_MEDIUM_CONTENT`   | Represents the content to be displayed on a device that belongs to a compact width class and a medium height class      |

### Annotations

- **LayoutCoordinator** useful to indicate the components which are just coordinator of the specific components designed
  for the specific size classes

- **ExpandedClassComponent** useful to indicate the components which are shown on those devices which belong to the
  `Expanded` class

- **MediumClassComponent** useful to indicate the components which are shown on those devices which belong to the
  `Medium` class

- **CompactClassComponent** useful to indicate the components which are shown on those devices which belong to the
  `Compact` class

- **ResponsiveClassComponent** useful to indicate the components which are shown on those devices which
  belong to the specified classes. This annotation can include both the **WindowWidthSizeClass** and 
  **WindowHeightSizeClass** categorizations

### Responsive methods

- [ResponsiveContent](#responsive-content) used to display the correct content based on the
  current [ResponsiveClass](#responsive-classes)
- [responsiveAction](#responsive-action) used to execute an action based on the
  current [ResponsiveClass](#responsive-classes)
- [responsiveAssignment](#responsive-assignment) used to assigns a specific value based on the
  current [ResponsiveClass](#responsive-classes)
- [responsiveMaxWidth](#responsive-max-width) modifier extension used to assign the max width of a content in responsive
  way

## Usage

### Responsive content

Using the `ResponsiveContent` method you can handle the different sizes of the screen where your application is running
on:

```kotlin
@Composable
@LayoutCoordinator
fun App() {
    ResponsiveContent(
        onExpandedSizeClass = {
            ExpandedContent()
        },
        onMediumSizeClass = {
            MediumContent()
        },
        onCompactSizeClass = {
            CompactContent()
        }
    )
}

@Composable
@ExpandedClassComponent // not mandatory, but suggested for a better readability
fun ExpandedContent() {
    // specific content for the expanded screens
}

@Composable
@MediumClassComponent // not mandatory, but suggested for a better readability
fun MediumContent() {
    // specific content for the medium screens
}

@Composable
@CompactClassComponent // not mandatory, but suggested for a better readability
fun CompactContent() {
    // specific content for the compact screens
}
```

### Responsive assignment

Using the `responsiveAssignment` method you can assign a specific value based on the current screen size where your
application
is running on:

```kotlin
@Composable
fun App() {
    val text = responsiveAssignment(
        onExpandedSizeClass = {
            "Hello Expanded World!"
        },
        onMediumSizeClass = {
            "Hello Medium World!"
        },
        onCompactSizeClass = {
            "Hello Compact World!"
        }
    )
    Text(
        text = text
    )
}
```

### Responsive action

Using the `responsiveAction` method you can execute specific (non @Composable) actions based on the current screen size
where your application
is running on

```kotlin
@Composable
fun App() {
    responsiveAction(
        onExpandedSizeClass = {
            println("Hello Expanded World!")
        },
        onMediumSizeClass = {
            println("Hello Medium World!")
        },
        onCompactSizeClass = {
            println("Hello Compact World!")
        }
    )
}
```

### Responsive max width

Using the `responsiveMaxWidth` extension you can assign the max width of a content in responsive way based on the
current
width size where your application is running on

```kotlin

@Composable
fun App() {
    Column(
        modifier = Modifier
            .responsiveMaxWidth() // will be automatically resized based on the width responsive class
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ...
        }
    }
}
```