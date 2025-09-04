This annotation is useful to indicate an unused field in a class declared as [Entity](https://www.geeksforgeeks.org/advance-java/jpa-creating-an-entity/).
Its purpose is to indicate that the field is used for mapping, in order to create a table or a relationship in the database

## Usage

=== "Java"

    ```java
    @Entity
    public class SimpleEntity {
    
        @Id
        private final String name;
    
        // unused field in the class
        @OneToOne
        @MappingPurpose // annotate it
        @JoinColumn(name = "item_id")
        private RelationshipItem item;
    
        public SimpleEntity(String name) {
            this.name = name;
        }
    
        public String getName() {
            return name;
        }
    
    }
    ```

=== "Kotlin"

    ```kotlin
    import jakarta.persistence.Entity
    import jakarta.persistence.Id
    import jakarta.persistence.OneToOne
    import jakarta.persistence.JoinColumn
    
    @Entity
    class SimpleEntity(
        @Id
        val name: String
    ) {
    
        // unused field in the class
        @OneToOne
        @MappingPurpose // annotate it
        @JoinColumn(name = "item_id")
        var item: RelationshipItem? = null
    
    }
    ```
