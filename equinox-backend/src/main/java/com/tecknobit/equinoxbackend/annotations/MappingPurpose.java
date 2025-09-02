package com.tecknobit.equinoxbackend.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * The {@code MappingPurpose} annotation is useful to indicate an unused field in a class declared as {@link jakarta.persistence.Entity}.
 * Its purpose is to indicate that the field is used for mapping, in order to create a table or a relationship in the database
 *
 * <pre>
 * {@code
 * @Entity
 * public class SimpleEntity {
 *
 *     @Id
 *     private final String name;
 *
 *     // unused field in the class
 *     @OneToOne
 *     @MappingPurpose // annotate it
 *     @JoinColumn(name = "item_id")
 *     private RelationshipItem item;
 *
 *     public SimpleEntity(String name) {
 *         this.name = name;
 *     }
 *
 *     public String getName() {
 *         return name;
 *     }
 *
 * }
 * }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.5
 */
@Target(FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface MappingPurpose {
}
