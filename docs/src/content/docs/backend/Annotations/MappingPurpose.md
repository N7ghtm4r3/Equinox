---
title: "MappingPurpose"
slug: "backend/Annotations/MappingPurpose"
---

This annotation is useful to indicate an unused field in a class declared as [Entity](https://www.geeksforgeeks.org/advance-java/jpa-creating-an-entity/).
Its purpose is to indicate that the field is used for mapping, in order to create a table or a relationship in the database

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
@Entity
public class SimpleEntity {

    @Id
    private final String name;

    // unused field in the class
    @OneToOne
    @MappingPurpose // annotate it
    @JoinColumn(name = "item_id")
    private RelationshipItem item;

    public SimpleEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.JoinColumn

@Entity
class SimpleEntity(
    @Id
    val name: String
) {

    // unused field in the class
    @OneToOne
    @MappingPurpose // annotate it
    @JoinColumn(name = "item_id")
    var item: RelationshipItem? = null

}
```

</div>

</div>
