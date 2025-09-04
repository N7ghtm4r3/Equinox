package com.tecknobit.equinoxbackend.annotations;

import com.tecknobit.equinoxbackend.apis.events.EquinoxEventsCollector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 The {@code EventsHandler} annotation is useful to mark classes that handle events, such as {@link EquinoxEventsCollector}.
 Its purpose is to improve the readability of the code
 *
 * <pre>
 * {@code
 * @EventsHandler
 * public interface TestEventsCollector extends EquinoxEventsCollector<TestEvent, TestApplicationEvent> {
 *
 *     // your custom implementation
 *
 * }
 * }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see EquinoxEventsCollector
 *
 * @since 1.1.5
 */
@Target(TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EventsHandler {
}
