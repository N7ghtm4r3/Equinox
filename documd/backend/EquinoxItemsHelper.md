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
// TODO: 30/01/2025 FIX THE DOCU 
public void insertCars(String ownerId, ArrayList<String> cars) {
    InsertCommand command = INSERT_INTO; // INSERT_IGNORE_INTO, REPLACE_INTO
    batchInsert(
            command,
            CARS_TABLE,
            new BatchQuery<String>() {
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
            },
            OWNER_COLUMN, MODEL_COLUMN // the order of the columns matches the one of the table
    );
}
```

### Synchronize data

Use the `syncBatch` method to execute a batch synchronization of the cars data

```java
// TODO: 30/01/2025 FIX THE DOCU
public void updateCars(String ownerId, ArrayList<String> cars) {
    syncBatch(
            new SyncBatchContainer() {
                @Override
                public ArrayList<String> getCurrentData() {
                    // logic to fetch the current cars of the owner
                    return currentData;
                }
    
                @Override
                public String[] getColumns() {
                    // the order of the columns matches the one of the table
                    return new String[]{OWNER_COLUMN, MODEL_COLUMN};
                }

                // not mandatory
                @Override
                public void afterSync() {
                    // execute after the synchronization completed 
                }
                
            },
            CARS_TABLE,
            ownerId,
            new BatchQuery<String>() {
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
            }
    );
} 
```

### Delete in batch

Use the `batchDeleteOnSingleSet` method to delete in batch a single set of data

```java
public void deleteCars(List<String> carsToDelete) {
    batchDeleteOnSingleSet(CARS_TABLE, carsToDelete, MODEL_COLUMN);
}
```

Use the `batchDelete` method to delete in batch a multiple set of data

```java
public void deleteCars(String ownerId, List<String> carsToDelete) {
    List<List<?>> values = new ArrayList<>();
    values.add(List.of(ownerId));
    values.add(carsToDelete);
    batchDelete(CARS_TABLE, values, OWNER_COLUMN, MODEL_COLUMN);
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
