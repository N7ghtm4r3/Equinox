## EquinoxItemsHelper

### Usage/Examples

```java
public class CarsService {

    /**
     * The {@code BatchQuery} interface is useful to manage the batch queries to insert or delete values in batch
     *
     * @param <V> The type of the item used in the query
     *
     * @author N7ghtm4r3 - Tecknobit
     *
     * @apiNote example usage for a join table of user and his/her cars:
     * <table>
     *     <thead>
     *         <tr>
     *             <th>user_id</th>
     *             <th>car_id</th>
     *         </tr>
     *     </thead>
     *     <tbody>
     *         <tr>
     *             <td>userId</td>
     *             <td>carId</td>
     *         </tr>
     *     </tbody>
     * </table>
     * <pre>
     * {@code
     *     ArrayList<String> carsIds = fetchCarsIdentifiers();
     *     BatchQuery batchQuery = new BatchQuery<String>() {
     *
     *          @Override
     *          public void getUpdatedData() {
     *              return updatedCars; // your updated data list
     *         }
     *
     *          @Override
     *          public void prepareQuery(Query query, int index, List<String> updatedItems) {
     *              for (String carId : updatedItems) {
     *                  // the order of the parameters setting is the same of the table
     *                  query.setParameter(index++, userId);
     *                  query.setParameter(index++, carId);
     *             }
     *         }
     *     }
     * }
     * </pre>
     */

}
```

The other apis will be gradually released

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

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
