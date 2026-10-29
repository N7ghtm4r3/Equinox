---
title: "IndexesCreator"
slug: "backend/APIs/IndexesCreator"
---

This API makes it easy to create various types of indexes for any table in your database

## Usage

### Create your own IndexesCreator instance

You need to create your own `IndexesCreator` instance and annotate is as `@Component`:


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
@Component // this annotation is required to enable the Spring Boot's mapping
public class CustomFullTextIndexCreator extends IndexesCreator {

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
@Component // this annotation is required to enable the Spring Boot's mapping
class CustomFullTextIndexCreator : IndexesCreator() {

}
```

</div>

</div>

### Declare index fields

To create the index you have to declare which column of the table made up that index


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


```java
private final List<String> INDEX_FIELDS = List.of("a", "b");
```

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
private val INDEX_FIELDS: List<String> = listOf("a", "b")
```

</div>

</div>

### Create the indexes

Override the `createIndexes` method and invoke either the provided methods from `IndexesCreator` or your own custom
methods to create indexes


<div class="equinox-tabs" id="equinox-tabs-2">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-2-tab-0" aria-controls="equinox-tabs-2-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-2-tab-1" aria-controls="equinox-tabs-2-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-2-panel-0" aria-labelledby="equinox-tabs-2-tab-0" tabindex="0">


```java
@Override
@PostConstruct // this annotation is required to enable automatically its invocation by Spring Boot
public void createIndexes() {
    // invoke your custom methods to create your own indexes
    createCustomFullTextIndex();
}

@Wrapper // not mandatory, but suggested for a better readability
private void createCustomFullTextIndex() {
    // create your own custom index
    createFullTextIndex("table", "index_name", INDEX_FIELDS);
}
```

</div>

<div role="tabpanel" id="equinox-tabs-2-panel-1" aria-labelledby="equinox-tabs-2-tab-1" tabindex="0">


```kotlin
@PostConstruct // this annotation is required to enable automatically its invocation by Spring Boot
override fun createIndexes() {
    // invoke your custom methods to create your own indexes
    createCustomFullTextIndex()
}

@Wrapper // not mandatory, but suggested for a better readability
private fun createCustomFullTextIndex() {
    // create your own custom index
    createFullTextIndex("table", "index_name", INDEX_FIELDS)
} 
```

</div>

</div>

## Overview

The completed implementation is the following:


<div class="equinox-tabs" id="equinox-tabs-3">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-3-tab-0" aria-controls="equinox-tabs-3-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-3-tab-1" aria-controls="equinox-tabs-3-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-3-panel-0" aria-labelledby="equinox-tabs-3-tab-0" tabindex="0">


```java
public class CustomFullTextIndexCreator extends IndexesCreator {

    private final List<String> INDEX_FIELDS = List.of("a", "b");

    @Override
    @PostConstruct // this annotation is required to enable automatically its invocation by Spring Boot
    public void createIndexes() {
        // invoke your custom methods to create your own indexes
        createCustomFullTextIndex();
    }

    @Wrapper // not mandatory, but suggested for a better readability
    private void createCustomFullTextIndex() {
        // create your own custom index
        createFullTextIndex("table", "index_name", INDEX_FIELDS);
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-3-panel-1" aria-labelledby="equinox-tabs-3-tab-1" tabindex="0">


```kotlin
@Component
class CustomFullTextIndexCreator : IndexesCreator() {

    private val INDEX_FIELDS: List<String> = listOf("a", "b")

    @PostConstruct // this annotation is required to enable automatically its invocation by Spring Boot
    override fun createIndexes() {
        // invoke your custom methods to create your own indexes
        createCustomFullTextIndex()
    }

    @Wrapper // not mandatory, but suggested for a better readability
    private fun createCustomFullTextIndex() {
        // create your own custom index
        createFullTextIndex("table", "index_name", INDEX_FIELDS)
    }            

}
```

</div>

</div>

## Provided indexes methods

- Use the `createFullTextIndex` method to create
  a [Full Text Search](https://www.geeksforgeeks.org/sql/mysql-full-text-search/)
  index