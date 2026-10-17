package com.tecknobit.equinoxbackend.annotations.events;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * The {@code EventsProcessor} annotation is useful to mark services that both handle and emit events
 *
 * <p>Its purpose is to improve code readability by identifying both the {@link EventsHandler} and
 * {@link EventsNotifier} roles with a single annotation
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Target(TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EventsProcessor {
}
