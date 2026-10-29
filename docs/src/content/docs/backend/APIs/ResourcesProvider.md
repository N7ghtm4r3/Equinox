---
title: "ResourcesProvider"
slug: "backend/APIs/ResourcesProvider"
---

Provides an easy way to handle the static resources used by the backend such images, assets, etc

## Usage

### Initialize the provider


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
ResourcesProvider resourcesProvider = new ResourcesProvider(
    "containerFolder_1",
    List.of("subfolder_1", "subfolder_2")
);
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
val resourcesProvider = ResourcesProvider(
    "containerFolder_1",
    listOf("subfolder_1", "subfolder_2")
)
```

</div>

</div>

### Create directories

Before is required to create the container directory which will contain other directories:


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


```java
resourcesProvider.createContainerDirectory();
```

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
resourcesProvider.createContainerDirectory()
```

</div>

</div>

Then can be created the subdirectories:


<div class="equinox-tabs" id="equinox-tabs-2">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-2-tab-0" aria-controls="equinox-tabs-2-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-2-tab-1" aria-controls="equinox-tabs-2-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-2-panel-0" aria-labelledby="equinox-tabs-2-tab-0" tabindex="0">


```java
resourcesProvider.createSubDirectories();
```

</div>

<div role="tabpanel" id="equinox-tabs-2-panel-1" aria-labelledby="equinox-tabs-2-tab-1" tabindex="0">


```kotlin
resourcesProvider.createSubDirectories()
```

</div>

</div>

Final structure:

``` bash
containerFolder_1
├── subfolder_1
└── subfolder_2
```