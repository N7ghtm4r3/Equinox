This API makes it easy to create various types of indexes for any table in your database

## Usage

### Create Your Own IndexesCreator Instance

You need to create your own `IndexesCreator` instance and annotate is as `@Component`:

=== "Java"

    ```java
    @Component // this annotation is required to enable the Spring Boot's mapping
    public class CustomFullTextIndexCreator extends IndexesCreator {
    
    }
    ```

=== "Kotlin"

    ```kotlin
    @Component // this annotation is required to enable the Spring Boot's mapping
    class CustomFullTextIndexCreator : IndexesCreator {
    
    }
    ```

### Declare Index Fields

To create the index you have to declare which column of the table made up that index

=== "Java"

    ```java
    public class CustomFullTextIndexCreator extends IndexesCreator {

        private final List<String> INDEX_FIELDS = List.of("a", "b");
    
    }
    ```

=== "Kotlin"

    ```kotlin
    @Component
    class CustomFullTextIndexCreator : IndexesCreator {
    
        private val INDEX_FIELDS: List<String> = listOf("a", "b")

    }
    ```

### Create The Indexes

Override the `createIndexes` method and invoke either the provided methods from `IndexesCreator` or your own custom
methods
to create indexes

=== "Java"

    ```java
    public class CustomFullTextIndexCreator extends IndexesCreator {

        private final List<String> INDEX_FIELDS = List.of("a", "b");

        @Override
        @PostConstruct // this annotation is required to enable automatically its invocation by Spring Boot
        public void createIndexes() {
            // invoke your custom methods to create your own indexes
            createCustomFullTextIndex();
        }

        @Wrapper // not mandatory, but suggested for a better readability
        private void createCustomFullTextIndex() {
            // create your own custom index
            createFullTextIndex("table", "index_name", INDEX_FIELDS);
        }
    
    }
    ```

=== "Kotlin"

    ```kotlin
    @Component
    class CustomFullTextIndexCreator : IndexesCreator {
    
        private val INDEX_FIELDS: List<String> = listOf("a", "b")

        @PostConstruct // this annotation is required to enable automatically its invocation by Spring Boot
        override fun createIndexes() {
            // invoke your custom methods to create your own indexes
            createCustomFullTextIndex()
        }

        @Wrapper // not mandatory, but suggested for a better readability
        private fun createCustomFullTextIndex() {
            // create your own custom index
            createFullTextIndex("table", "index_name", INDEX_FIELDS)
        }            

    }
    ```

## Provided Indexes Methods

- Use the `createFullTextIndex` method to create a [Full Text Search](https://en.wikipedia.org/wiki/Full-text_search)
  index