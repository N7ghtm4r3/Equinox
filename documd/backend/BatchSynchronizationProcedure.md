## BatchSynchronizationProcedure

This api is designed to compact and to clean implement a batch synchronization procedure giving the base behavior that a
batch synchronization need to have

### Usage

#### Create the support item

It is suggested to use `JoinTableSyncBatchItem` as base for the support item, but is not required

```java

@BatchQueryItem
public class TeamMemberBatchItem extends JoinTableSyncBatchItem<String, String> {

    public TeamMemberBatchItem(String teamId, String memberId) {
        super(teamId, memberId);
    }

}
```

#### Create the procedure

```java

@BatchSyncProcedureImpl
public class TeamMemberBatchSyncProcedure extends BatchSynchronizationProcedure<String, String, TeamMemberBatchItem> {

    public TeamMemberBatchSyncProcedure(String owner, Collection<String> ownedItems, EntityManager entityManager) {
        super(owner, ownedItems, "team_members", entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SyncBatchModel createModel() {
        // create the model
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BatchQuery<TeamMemberBatchItem> createBatchQuery() {
        // create the query
    }

}
```

#### Use in a service

The inheritance with the `EquinoxItemsHelper` is required to properly provide the `entityManager`

```java
 import java.util.List;

@Service
public class TeamsService extends EquinoxItemsHelper {

    public void syncMembers(String teamId, List<String> members) {
        TeamMemberBatchSyncProcedure procedure = new TeamMemberBatchSyncProcedure(teamId, members, entityManager);
        // create the callback to retrieve the current data (required)
        procedure.setCurrentDataCallback(new BatchSynchronizationProcedure.CurrentDataCallback<String>() {
            @Override
            public Collection<String> retrieveCurrentData() {
                List<String> currentData = List.of();
                // custom logic to fill the list
                return currentData;
            }
        });

        // execute the procedure
        procedure.executeBatchSynchronization();
    }

}
```

#### Mirror usage

If you have also a mirrored service of the `TeamsService` like for example `MembersService` and you need to sync the
teams
of a member you can use a dedicated converter to properly create the support item to use in the same sync procedure you
created for the team's side ([TeamMemberBatchSyncProcedure](#create-the-procedure)), this because the support item has a
fixed constructor, but the owner entity changes related to the service where the procedure is invoked. You can do as
follows:

```java
 import java.util.List;

@Service
public class MembersService extends EquinoxItemsHelper {

    public void syncTeams(String memberId, List<String> teams) {
        TeamMemberBatchSyncProcedure procedure = new TeamMemberBatchSyncProcedure(memberId, teams, entityManager);
        // create the callback to retrieve the current data (required)
        procedure.setCurrentDataCallback(new BatchSynchronizationProcedure.CurrentDataCallback<String>() {
            @Override
            public Collection<String> retrieveCurrentData() {
                List<String> currentData = List.of();
                // custom logic to fill the list
                return currentData;
            }
        });
        // create the converter to properly create the support item
        procedure.useConverter(teamsIds -> {
            List<TeamMemberBatchItem> teamMemberBatchItems = new ArrayList<>();
            for (String teamId : teamsIds)
                // the member identifier is fixed, meanwhile the team identifier changes
                teamMemberBatchItems.add(new TeamMemberBatchItem(teamId, memberId));
            return teamMemberBatchItems;
        });
        // execute the procedure
        procedure.executeBatchSynchronization();
    }

}
```

Then use the converter, if you need to use the converter you have to create a converter for both sides, in the procedure

```java

@BatchSyncProcedureImpl
public class TeamMemberBatchSyncProcedure extends BatchSynchronizationProcedure<String, String, TeamMemberBatchItem> {

    public TeamMemberBatchSyncProcedure(String owner, Collection<String> ownedItems, EntityManager entityManager) {
        super(owner, ownedItems, "team_members", entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SyncBatchModel createModel() {
        return new SyncBatchModel() {
            @Override
            public Collection<TeamMemberBatchItem> getCurrentData() {
                // use the converter to convert the current data retrieved with the CurrentDataCallback
                return converter.convert(currentDataCallback.retrieveCurrentData());
            }

            @Override
            public String[] getDeletingColumns() {
                return new String[]{"team_id", "member_id"};
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BatchQuery<TeamMemberBatchItem> createBatchQuery() {
        return new BatchQuery<>() {
            @Override
            public Collection<TeamMemberBatchItem> getData() {
                // use the converter 
                return converter.convert(ownedItems);
            }

            @Override
            @TableColumns(columns = {"team_id", "member_id"})
            public void prepareQuery(Query query, int index, Collection<TeamMemberBatchItem> items) {
                for (TeamMemberBatchItem element : items) {
                    query.setParameter(index++, element.getOwner());
                    query.setParameter(index++, element.getOwned());
                }
            }

            @Override
            public String[] getColumns() {
                return new String[]{"team_id", "member_id"};
            }
        };
    }

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