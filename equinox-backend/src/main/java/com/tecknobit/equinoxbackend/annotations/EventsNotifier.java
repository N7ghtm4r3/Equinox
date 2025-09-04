package com.tecknobit.equinoxbackend.annotations;

import com.tecknobit.equinoxbackend.apis.events.EquinoxEventsEmitter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * The {@code EventsNotifier} annotation is useful to mark classes that send events, such as {@link EquinoxEventsEmitter}.
 * Its purpose is to improve the readability of the code
 *
 * <pre>
 * {@code
 * @Service // required annotation
 * @EventsNotifier
 * public class TestEventsEmitter extends EquinoxEventsEmitter<TestEvent> {
 *
 *      // your custom implementation
 *
 * }
 * }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see EquinoxEventsEmitter
 *
 * @since 1.1.5
 */
@Target(TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EventsNotifier {
}
