## EquinoxItemsHelper

Allows you to execute batch queries such insertion, deletion and synchronization dynamically

### Usage

### Create the service

```java
@Service
public class CarsService extends EquinoxItemsHelper {

    private static final String CARS_TABLE = "cars";

    private static final String OWNER_COLUMN = "owner";

    private static final String MODEL_COLUMN = "model";

    @Autowired
    private CarsRepository repository;

}
```

### Insert in batch

Use the `batchInsert` method to insert multiple cars in the same query

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

### Synchronize data

Use the `syncBatch` method to execute a batch synchronization of the data

#### Simple objects

With simple object such `String`, `int`, `double`, etc... you can use the method as following:

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

#### Complex objects

With complex object such custom classes, etc... you can use the method as following:

<h6>Custom object example</h6>

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

<h6>Map the custom object with the data to use during the synchronization</h6>

- Using `ComplexBatchItem` interface

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

- Using `JoinTableSyncBatchItem` wrapper class for the queries which operate in the join tables

```java
JoinTableSyncBatchItem<String, String> joinTableItem = new JoinTableSyncBatchItem<>(
        "owner_id",
        "owned_id"
);
```

<h6>Execute the synchronization</h6>

To execute the synchronization with the complex objects the procedure is the same for
the [simple objects](#simple-objects)
just you can use the logic of your custom complex object to create the `SyncBatchModel` model and the related
`BatchQuery`

### Delete in batch

Use the `batchDelete` method to delete in batch the data

#### Simple objects

With simple object such `String`, `int`, `double`, etc... you can use the method as following:

```java
public void deleteCars(List<String> carsToDelete) {
    batchDelete(CARS_TABLE, carsToDelete, MODEL_COLUMN, etc..);
}
```

#### Complex objects

With complex object such custom classes, etc... you can use the method as following:

<h6>Custom object example</h6>

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
}
```

<h6>Map the custom object with the data to use during the deletion</h6>

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

    // this will allow the automatically mapping of the custom value to use during the deletion
    @Override
    public List<?> mappedValues() {
        ArrayList<String> custom = new ArrayList<>();
        custom.add(plate);
        custom.add(model);
        return custom;
    }

}
```

<h6>Execute the batch delete</h6>

```java
public void deleteCars(List<Car> carsToDelete) {
    batchDelete(CARS_TABLE, carsToDelete, PLATE_COLUMN, MODEL_COLUMN);
}
```

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot) [![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**           | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4**   | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit