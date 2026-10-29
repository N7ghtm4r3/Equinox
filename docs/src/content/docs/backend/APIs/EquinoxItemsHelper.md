---
title: "EquinoxItemsHelper"
slug: "backend/APIs/EquinoxItemsHelper"
---

This API allows to execute batch queries such insertion, deletion and synchronization dynamically

## Implementation

Create the service


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
@Service
public class CarsService extends EquinoxItemsHelper { // make extends the class with EquinoxItemsHelper

    private static final String CARS_TABLE = "cars";

    private static final String OWNER_COLUMN = "owner";

    private static final String MODEL_COLUMN = "model";

    @Autowired
    private CarsRepository repository;

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
@Service
class CarsService : EquinoxItemsHelper() { // make extends the class with EquinoxItemsHelper

    companion object {
        private const val CARS_TABLE = "cars"
        private const val OWNER_COLUMN = "owner"
        private const val MODEL_COLUMN = "model"
    }

    @Autowired
    private lateinit var repository: CarsRepository
}
```

</div>

</div>

## Usage

### Insert in batch

Use the `batchInsert` method to insert multiple cars in the same query


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


```java
public void insertCars(String ownerId, ArrayList<String> cars) {
    InsertCommand command = INSERT_INTO; // INSERT_IGNORE_INTO, REPLACE_INTO
    batchInsert(command, CARS_TABLE, new BatchQuery<String>() {
        @Override
        public List<String> getData() {
        // useful if you need to filter values to insert
        return cars;
        }
        
        @Override
        public void prepareQuery(Query query, int index, List<String> cars) {
            for (String car : cars) {
                // the order of the parameters setting is the same of the table
                query.setParameter(index++, ownerId);
                query.setParameter(index++, car);
            }
        }

        @Override
        public String[] getColumns() {
            // the order of the columns matches the one of the table
            return new String[]{OWNER_COLUMN, MODEL_COLUMN};
        }
    });
}
```

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
fun insertCars(
    ownerId: String,
    cars: ArrayList<String>
) {
    val command = INSERT_INTO // INSERT_IGNORE_INTO, REPLACE_INTO
    batchInsert(command, CARS_TABLE, object : BatchQuery<String> {
        override fun getData(): List<String> {
            // useful if you need to filter values to insert
            return cars
        }

        override fun prepareQuery(
            query: Query, 
            index: Int, 
            cars: List<String>
        ) {
            for (car in cars) {
                // the order of the parameters setting is the same of the table
                query.setParameter(index++, ownerId)
                query.setParameter(index++, car)
            }
        }

        override fun getColumns(): Array<String> {
            // the order of the columns matches the one of the table
            return arrayOf(OWNER_COLUMN, MODEL_COLUMN)
        }
    })
}
```

</div>

</div>

### Synchronize data

Use the `syncBatch` method to execute a batch synchronization of the data, you can also create a dedicated
procedure using the [BatchSynchronizationProcedure](/Equinox/backend/APIs/BatchSynchronizationProcedure/) API class

#### Simple objects

With simple object such `String`, `int`, `double`, etc... you can use the method as follows:


<div class="equinox-tabs" id="equinox-tabs-2">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-2-tab-0" aria-controls="equinox-tabs-2-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-2-tab-1" aria-controls="equinox-tabs-2-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-2-panel-0" aria-labelledby="equinox-tabs-2-tab-0" tabindex="0">


```java
public void updateCars(String ownerId, ArrayList<String> cars) {
    SyncBatchModel model = new SyncBatchModel() {
        @Override
        public ArrayList<String> getCurrentData() {
            // logic to fetch the current cars of the owner
            return currentData;
        }

        @Override
        public String[] getDeletingColumns() {
            // the order of the columns matches the one of the table
            return new String[]{OWNER_COLUMN, MODEL_COLUMN};
        }

        // not mandatory
        @Override
        public void afterSync() {
            // execute after the synchronization completed 
        }
    };

    BatchQuery batchQuery = new BatchQuery<String>() {
        @Override
        public List<String> getData() {
            // useful if you need to filter values to insert
            return cars;
        }

        @Override
        public void prepareQuery(Query query, int index, List<String> cars) {
            for (String car : cars) {
                // the order of the parameters setting is the same of the table
                query.setParameter(index++, ownerId);
                query.setParameter(index++, car);
            }
        }

        @Override
        public String[] getColumns() {
            // the order of the columns matches the one of the table
            return new String[]{OWNER_COLUMN, MODEL_COLUMN};
        }
    };

    syncBatch(model, CARS_TABLE, batchQuery);
} 
```

</div>

<div role="tabpanel" id="equinox-tabs-2-panel-1" aria-labelledby="equinox-tabs-2-tab-1" tabindex="0">


```kotlin
fun updateCars(
    ownerId: String,
    cars: ArrayList<String>
) {
    val model = object : SyncBatchModel {

        override fun getCurrentData(): ArrayList<String> {
            // logic to fetch the current cars of the owner
            return currentData
        }
    
        override fun getDeletingColumns(): Array<String> {
            // the order of the columns matches the one of the table
            return arrayOf(OWNER_COLUMN, MODEL_COLUMN)
        }

        // not mandatory
        override fun afterSync() {
            // execute after the synchronization completed 
        }

    }
    
    val batchQuery = object : BatchQuery<String> {

        override fun getData(): List<String> {
            // useful if you need to filter values to insert
            return cars
        }
    
        override fun prepareQuery(
            query: Query,
            index: Int,
            cars: List<String>
        ) {
            for (car in cars) {
                // the order of the parameters setting is the same of the table
                query.setParameter(index++, ownerId)
                query.setParameter(index++, car)
            }
        }

        override fun getColumns(): Array<String> {
            // the order of the columns matches the one of the table
            return arrayOf(OWNER_COLUMN, MODEL_COLUMN)
        }

    }

    syncBatch(model, CARS_TABLE, batchQuery)
}
```

</div>

</div>

#### Complex objects

With complex object such custom classes, etc... you can use the method as follows:

###### Custom object example


<div class="equinox-tabs" id="equinox-tabs-3">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-3-tab-0" aria-controls="equinox-tabs-3-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-3-tab-1" aria-controls="equinox-tabs-3-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-3-panel-0" aria-labelledby="equinox-tabs-3-tab-0" tabindex="0">


```java
public class Car {

    private String model;

    private String plate;

    public Car(String model, String plate) {
        this.model = model;
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public String getPlate() {
        return plate;
    }

    // required the custom equals implementation to correctly synchronize complex objects
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;
        return Objects.equals(model, car.model) && Objects.equals(plate, car.plate);
    }

    // required the custom hashCode implementation to correctly synchronize complex objects
    @Override
    public int hashCode() {
        int result = Objects.hashCode(model);
        result = 31 * result + Objects.hashCode(plate);
        return result;
    }

} 
```

</div>

<div role="tabpanel" id="equinox-tabs-3-panel-1" aria-labelledby="equinox-tabs-3-tab-1" tabindex="0">


```kotlin
class Car(
    val model: String,
    val plate: String
) {

    // required the custom equals implementation to correctly synchronize complex objects
    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) return false

        other as Car
        return model == other.model && plate == other.plate
    }

    // required the custom hashCode implementation to correctly synchronize complex objects
    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + plate.hashCode()
        return result
    }

}
```

</div>

</div>

###### Map the custom object with the data to use during the synchronization

- Using `ComplexBatchItem` interface


<div class="equinox-tabs" id="equinox-tabs-4">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-4-tab-0" aria-controls="equinox-tabs-4-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-4-tab-1" aria-controls="equinox-tabs-4-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-4-panel-0" aria-labelledby="equinox-tabs-4-tab-0" tabindex="0">


```java
// implement this interface to do that mapping
public class Car implements ComplexBatchItem {

    private String model;

    private String plate;

    public Car(String model, String plate) {
        this.model = model;
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public String getPlate() {
        return plate;
    }

    // required the custom equals implementation to correctly synchronize complex objects
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;
        return Objects.equals(model, car.model) && Objects.equals(plate, car.plate);
    }

    // required the custom hashCode implementation to correctly synchronize complex objects
    @Override
    public int hashCode() {
        int result = Objects.hashCode(model);
        result = 31 * result + Objects.hashCode(plate);
        return result;
    }

    // this will allow the automatically mapping of the custom value to use during the synchronization
    @Override
    public List<?> mappedValues() {
        ArrayList<String> custom = new ArrayList<>();
        custom.add(plate);
        custom.add(model);
        return custom;
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-4-panel-1" aria-labelledby="equinox-tabs-4-tab-1" tabindex="0">


```kotlin
// implement this interface to do that mapping
class Car(
    val model: String,
    val plate: String
) : ComplexBatchItem {

    // required the custom equals implementation to correctly synchronize complex objects
    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) return false

        other as Car
        return model == other.model && plate == other.plate
    }

    // required the custom hashCode implementation to correctly synchronize complex objects
    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + plate.hashCode()
        return result
    }

    // this will allow the automatically mapping of the custom value to use during the synchronization
    override fun mappedValues(): List<*> {
        val custom = ArrayList<String>()
        custom.add(plate)
        custom.add(model)
        return custom
    }

}
```

</div>

</div>

- Using `JoinTableSyncBatchItem` wrapper class for the queries which operate with join tables


<div class="equinox-tabs" id="equinox-tabs-5">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-5-tab-0" aria-controls="equinox-tabs-5-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-5-tab-1" aria-controls="equinox-tabs-5-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-5-panel-0" aria-labelledby="equinox-tabs-5-tab-0" tabindex="0">


```java
JoinTableSyncBatchItem<String, String> joinTableItem = new JoinTableSyncBatchItem<>(
    "owner_id",
    "owned_id"
);
```

</div>

<div role="tabpanel" id="equinox-tabs-5-panel-1" aria-labelledby="equinox-tabs-5-tab-1" tabindex="0">


```kotlin
val joinTableItem: JoinTableSyncBatchItem<String, String> = JoinTableSyncBatchItem<>(
    "owner_id",
    "owned_id"
)
```

</div>

</div>

###### Execute the synchronization

To execute the synchronization with the complex objects the procedure is the same for
the [simple objects](#simple-objects), but you can just use the logic of your custom complex object to create the
`SyncBatchModel` and the related `BatchQuery`

### Delete in batch

Use the `batchDelete` method to delete in batch the data

#### Simple objects

With simple object such `String`, `int`, `double`, etc... you can use the method as follows:


<div class="equinox-tabs" id="equinox-tabs-6">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-6-tab-0" aria-controls="equinox-tabs-6-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-6-tab-1" aria-controls="equinox-tabs-6-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-6-panel-0" aria-labelledby="equinox-tabs-6-tab-0" tabindex="0">


```java
public void deleteCars(List<String> carsToDelete) {
    batchDelete(CARS_TABLE, carsToDelete, MODEL_COLUMN, etc..);
}
```

</div>

<div role="tabpanel" id="equinox-tabs-6-panel-1" aria-labelledby="equinox-tabs-6-tab-1" tabindex="0">


```kotlin
fun deleteCars(
    carsToDelete: List<String>
) {
    batchDelete(CARS_TABLE, carsToDelete, MODEL_COLUMN /*, etc.. */)
}
```

</div>

</div>

#### Complex objects

With complex objects such custom classes, etc... you can use the method as follows:

###### Custom object example


<div class="equinox-tabs" id="equinox-tabs-7">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-7-tab-0" aria-controls="equinox-tabs-7-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-7-tab-1" aria-controls="equinox-tabs-7-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-7-panel-0" aria-labelledby="equinox-tabs-7-tab-0" tabindex="0">


```java
public class Car {

    private String model;

    private String plate;

    public Car(String model, String plate) {
        this.model = model;
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public String getPlate() {
        return plate;
    }

    // required the custom equals implementation to correctly synchronize complex objects
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;
        return Objects.equals(model, car.model) && Objects.equals(plate, car.plate);
    }

    // required the custom hashCode implementation to correctly synchronize complex objects
    @Override
    public int hashCode() {
        int result = Objects.hashCode(model);
        result = 31 * result + Objects.hashCode(plate);
        return result;
    }

} 
```

</div>

<div role="tabpanel" id="equinox-tabs-7-panel-1" aria-labelledby="equinox-tabs-7-tab-1" tabindex="0">


```kotlin
class Car(
    val model: String,
    val plate: String
) {

    // required the custom equals implementation to correctly synchronize complex objects
    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) return false

        other as Car
        return model == other.model && plate == other.plate
    }

    // required the custom hashCode implementation to correctly synchronize complex objects
    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + plate.hashCode()
        return result
    }

}
```

</div>

</div>

###### Map the custom object with the data to use during the deletion

- Using `ComplexBatchItem` interface


<div class="equinox-tabs" id="equinox-tabs-8">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-8-tab-0" aria-controls="equinox-tabs-8-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-8-tab-1" aria-controls="equinox-tabs-8-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-8-panel-0" aria-labelledby="equinox-tabs-8-tab-0" tabindex="0">


```java
// implement this interface to do that mapping
public class Car implements ComplexBatchItem {

    private String model;

    private String plate;

    public Car(String model, String plate) {
        this.model = model;
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public String getPlate() {
        return plate;
    }

    // required the custom equals implementation to correctly synchronize complex objects
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;
        return Objects.equals(model, car.model) && Objects.equals(plate, car.plate);
    }

    // required the custom hashCode implementation to correctly synchronize complex objects
    @Override
    public int hashCode() {
        int result = Objects.hashCode(model);
        result = 31 * result + Objects.hashCode(plate);
        return result;
    }

    // this will allow the automatically mapping of the custom value to use during the synchronization
    @Override
    public List<?> mappedValues() {
        ArrayList<String> custom = new ArrayList<>();
        custom.add(plate);
        custom.add(model);
        return custom;
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-8-panel-1" aria-labelledby="equinox-tabs-8-tab-1" tabindex="0">


```kotlin
// implement this interface to do that mapping
class Car(
    val model: String,
    val plate: String
) : ComplexBatchItem {

    // required the custom equals implementation to correctly synchronize complex objects
    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) return false

        other as Car
        return model == other.model && plate == other.plate
    }

    // required the custom hashCode implementation to correctly synchronize complex objects
    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + plate.hashCode()
        return result
    }

    // this will allow the automatically mapping of the custom value to use during the synchronization
    override fun mappedValues(): List<*> {
        val custom = ArrayList<String>()
        custom.add(plate)
        custom.add(model)
        return custom
    }

}
```

</div>

</div>

###### Execute the batch delete


<div class="equinox-tabs" id="equinox-tabs-9">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-9-tab-0" aria-controls="equinox-tabs-9-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-9-tab-1" aria-controls="equinox-tabs-9-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-9-panel-0" aria-labelledby="equinox-tabs-9-tab-0" tabindex="0">


```java
public void deleteCars(List<Car> carsToDelete) {
    batchDelete(CARS_TABLE, carsToDelete, PLATE_COLUMN, MODEL_COLUMN);
}
```

</div>

<div role="tabpanel" id="equinox-tabs-9-panel-1" aria-labelledby="equinox-tabs-9-tab-1" tabindex="0">


```kotlin
fun deleteCars(
    carsToDelete: List<Car>
) {
    batchDelete(CARS_TABLE, carsToDelete, PLATE_COLUMN, MODEL_COLUMN)
}
```

</div>

</div>
