This API allows to execute batch queries such insertion, deletion and synchronization dynamically

## Implementation

Create the service

=== "Java"

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

=== "Kotlin"

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

## Usage

### Insert in batch

Use the `batchInsert` method to insert multiple cars in the same query

=== "Java"

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

=== "Kotlin"

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

### Synchronize data

Use the `syncBatch` method to execute a batch synchronization of the data, you can also create a dedicated
procedure using the [BatchSynchronizationProcedure](BatchSynchronizationProcedure.md) API class

#### Simple objects

With simple object such `String`, `int`, `double`, etc... you can use the method as follows:

=== "Java"

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

=== "Kotlin"

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

#### Complex objects

With complex object such custom classes, etc... you can use the method as follows:

###### Custom object example

=== "Java"

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

=== "Kotlin"

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

###### Map the custom object with the data to use during the synchronization

- Using `ComplexBatchItem` interface

=== "Java"

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

=== "Kotlin"

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

- Using `JoinTableSyncBatchItem` wrapper class for the queries which operate with join tables

=== "Java"

    ```java
    JoinTableSyncBatchItem<String, String> joinTableItem = new JoinTableSyncBatchItem<>(
        "owner_id",
        "owned_id"
    );
    ```

=== "Kotlin"

    ```kotlin
    val joinTableItem: JoinTableSyncBatchItem<String, String> = JoinTableSyncBatchItem<>(
        "owner_id",
        "owned_id"
    )
    ```

###### Execute the synchronization

To execute the synchronization with the complex objects the procedure is the same for
the [simple objects](#simple-objects), but you can just use the logic of your custom complex object to create the
`SyncBatchModel` and the related `BatchQuery`

### Delete in batch

Use the `batchDelete` method to delete in batch the data

#### Simple objects

With simple object such `String`, `int`, `double`, etc... you can use the method as follows:

=== "Java"

    ```java
    public void deleteCars(List<String> carsToDelete) {
        batchDelete(CARS_TABLE, carsToDelete, MODEL_COLUMN, etc..);
    }
    ```

=== "Kotlin"

    ```kotlin
    fun deleteCars(
        carsToDelete: List<String>
    ) {
        batchDelete(CARS_TABLE, carsToDelete, MODEL_COLUMN /*, etc.. */)
    }
    ```

#### Complex objects

With complex objects such custom classes, etc... you can use the method as follows:

###### Custom object example

=== "Java"

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

=== "Kotlin"

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

###### Map the custom object with the data to use during the deletion

- Using `ComplexBatchItem` interface

=== "Java"

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

=== "Kotlin"

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

###### Execute the batch delete

=== "Java"

    ```java
    public void deleteCars(List<Car> carsToDelete) {
        batchDelete(CARS_TABLE, carsToDelete, PLATE_COLUMN, MODEL_COLUMN);
    }
    ```

=== "Kotlin"

    ```kotlin
    fun deleteCars(
        carsToDelete: List<Car>
    ) {
        batchDelete(CARS_TABLE, carsToDelete, PLATE_COLUMN, MODEL_COLUMN)
    }
    ```