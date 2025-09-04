This annotation is useful to indicate the columns of a table and which order those columns have
in that table. It is particularly useful for those method which have to handling queries, for example the
[BatchQuery](../APIs/EquinoxItemsHelper.md#insert-in-batch) methods

## Usage

=== "Java"

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

=== "Kotlin"

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