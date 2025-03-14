package com.tecknobit.equinoxbackend.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.RECORD_COMPONENT;
import static java.lang.annotation.ElementType.TYPE;

/**
 * The {@code BatchQueryItem} annotation is useful to indicate an object which its scope is the support to handle the
 * data and manipulate them during the batch operations
 *
 * <pre>
 * {@code
 * @BatchQueryItem(
 *  // not mandatory
 *   description= """
 *          Informative description about the scope of the this item, such the operation where is used what data are
 *          manipulated, etc...
 *          """ // suggested text block
 * )
 * public class SimpleItem {
 *
 *     private final String id;
 *
 *     private final String ownedEntityId;
 *
 *     ... rest of the item ...
 *
 * }
 * }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.8
 */
@Target(value = {TYPE, RECORD_COMPONENT})
@Retention(RetentionPolicy.SOURCE)
public @interface BatchQueryItem {

    /**
     * Informative description about the behavior of the implemented batch item
     */
    String description() default "";

}

