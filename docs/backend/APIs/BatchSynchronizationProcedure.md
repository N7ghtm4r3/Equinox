This API is designed to compact and to clean implement a **batch synchronization procedure** giving the base behavior
that a
batch synchronization need to have

## Implementation

### Create the support item

It is suggested to use `JoinTableSyncBatchItem` as base for the support item, but is not required

```java
@BatchQueryItem
public class TeamMemberBatchItem extends JoinTableSyncBatchItem<String, String> {

    public TeamMemberBatchItem(String teamId, String memberId) {
        super(teamId, memberId);
    }

}
```

### Create the procedure

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

## Usage

### Use in a service

The inheritance with the [EquinoxItemsHelper](EquinoxItemsHelper.md) is required to properly provide the `entityManager`

=== "Java"

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

=== "Kotlin"

    ```kotlin
    import org.springframework.stereotype.Service

    @Service
    class TeamsService : EquinoxItemsHelper() {
    
        fun syncMembers(
            teamId: String,
            members: List<String>
        ) {
            val procedure = TeamMemberBatchSyncProcedure(
                teamId,
                members,
                entityManager
            )
    
            // create the callback to retrieve the current data (required)
            procedure.setCurrentDataCallback(object : BatchSynchronizationProcedure.CurrentDataCallback<String> {
                override fun retrieveCurrentData(): Collection<String> {
                    val currentData: List<String> = listOf()
                    // custom logic to fill the list
                    return currentData
                }
            })
    
            // execute the procedure
            procedure.executeBatchSynchronization()
        }

    }
    ```

### Mirrored usage

If you have also a mirrored service of the `TeamsService` like for example `MembersService` and you need to sync the
teams of a member you can use a dedicated converter to properly create the support item to use in the same sync
procedure
you created for the team's side ([TeamMemberBatchSyncProcedure](#create-the-procedure)), this because the support item
has a
fixed constructor, but the owner entity changes related to the service where the procedure is invoked. You can do as
follows:

=== "Java"

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

=== "Kotlin"

    ```kotlin
    import org.springframework.stereotype.Service

    @Service
    class MembersService : EquinoxItemsHelper() {
    
        fun syncTeams(
            memberId: String,
            teams: List<String>
        ) {
            val procedure = TeamMemberBatchSyncProcedure(
                memberId,
                teams,
                entityManager
            )
    
            // create the callback to retrieve the current data (required)
            procedure.setCurrentDataCallback(object : BatchSynchronizationProcedure.CurrentDataCallback<String> {
                override fun retrieveCurrentData(): Collection<String> {
                    val currentData: List<String> = listOf()
                    // custom logic to fill the list
                    return currentData
                }
            })
    
            // create the converter to properly create the support item
            procedure.useConverter { teamsIds ->
                val teamMemberBatchItems = ArrayList<TeamMemberBatchItem>()
                for (teamId in teamsIds) {
                    // the member identifier is fixed, meanwhile the team identifier changes
                    teamMemberBatchItems.add(TeamMemberBatchItem(teamId, memberId))
                }
                teamMemberBatchItems
            }
    
            // execute the procedure
            procedure.executeBatchSynchronization()
        }

    }
    ```

Then use the converter in the procedure (if you need to use the converter you have to create a converter for both sides)

=== "Java"

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

=== "Kotlin"

    ```kotlin
    @BatchSyncProcedureImpl
    class TeamMemberBatchSyncProcedure(
        owner: String,
        ownedItems: Collection<String>,
        entityManager: EntityManager
    ) : BatchSynchronizationProcedure<String, String, TeamMemberBatchItem>(
        owner,
        ownedItems,
        "team_members",
        entityManager
    ) {
    
        /**
         * {@inheritDoc}
         */
        override fun createModel(): SyncBatchModel {
            return object : SyncBatchModel() {
                override fun getCurrentData(): Collection<TeamMemberBatchItem> {
                    // use the converter to convert the current data retrieved with the CurrentDataCallback
                    return converter.convert(currentDataCallback.retrieveCurrentData())
                }
    
                override fun getDeletingColumns(): Array<String> {
                    return arrayOf("team_id", "member_id")
                }
            }
        }
    
        /**
         * {@inheritDoc}
         */
        override fun createBatchQuery(): BatchQuery<TeamMemberBatchItem> {
            return object : BatchQuery<TeamMemberBatchItem>() {
                override fun getData(): Collection<TeamMemberBatchItem> {
                    // use the converter
                    return converter.convert(ownedItems)
                }
    
                @TableColumns(columns = ["team_id", "member_id"])
                override fun prepareQuery(
                    query: Query,
                    index: Int,
                    items: Collection<TeamMemberBatchItem>
                ) {
                    var idx = index
                    for (element in items) {
                        query.setParameter(idx++, element.owner)
                        query.setParameter(idx++, element.owned)
                    }
                }
    
                override fun getColumns(): Array<String> {
                    return arrayOf("team_id", "member_id")
                }
            }
        }

    }
    ```