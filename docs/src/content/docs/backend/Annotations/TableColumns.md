---
title: "TableColumns"
slug: "backend/Annotations/TableColumns"
---

This annotation is useful to indicate the columns of a table and which order those columns have
in that table. It is particularly useful for those method which have to handling queries, for example the
[BatchQuery](/Equinox/backend/APIs/EquinoxItemsHelper/#insert-in-batch) methods

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
BatchQuery<Simple> batchQuery = new BatchQuery<>() {

    @Override
    @TableColumns(columns = {"id", "simple", "week"})
    public void prepareQuery(Query query, int index, Collection<Simple> items) {
        for (Simple item : items) {
            query.setParameter(index++, item.getId());
            query.setParameter(index++, item.getSimple());
            query.setParameter(index++, item.getWeek());
        }
    }

    @Override
    @TableColumns(columns = {"id", "simple", "week"})
    public String[] getColumns() {
        return new String[]{"id", "simple", "week"};
    }
};
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
val batchQuery: BatchQuery<Simple> = object : BatchQuery<Simple> {

    @TableColumns(columns = ["id", "simple", "week"])
    override fun prepareQuery(query: Query, index: Int, items: Collection<Simple>) {
        var idx = index
        for (item in items) {
            query.setParameter(idx++, item.id)
            query.setParameter(idx++, item.simple)
            query.setParameter(idx++, item.week)
        }
    }

    @TableColumns(columns = ["id", "simple", "week"])
    override fun getColumns(): Array<String> {
        return arrayOf("id", "simple", "week")
    }
}
```

</div>

</div>
