package com.tecknobit.equinoxbackend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * The {@code EntityRetriever} annotation is useful to mark methods that
 * retrieve a persisted entity from the database through a repository
 *
 * <pre>
 * {@code
 * @EntityRetriever
 * public Item retrieveItem(String id) {
 *     return itemsRepository.findById(id).orElseThrow();
 * }
 * }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 2.0.0
 */
@Target(value = {METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface EntityRetriever {
}
