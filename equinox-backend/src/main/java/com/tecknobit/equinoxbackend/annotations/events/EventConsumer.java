package com.tecknobit.equinoxbackend.annotations.events;

import com.tecknobit.equinoxbackend.apis.events.EquinoxApplicationEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code EventConsumer} annotation is useful to mark types and methods
 * that consume application events
 *
 * <p>The optional {@link #event()} element identifies the application event
 * class associated with the consumer. This annotation is retained only
 * in source code
 *
 * <pre>
 * {@code
 * @EventConsumer(event = TestApplicationEvent.class)
 * public void consume(TestApplicationEvent event) {
 *     // handle the event
 * }
 * }
 * </pre>
 *
 * <p>The example assumes that {@code TestApplicationEvent} extends
 * {@link EquinoxApplicationEvent}
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface EventConsumer {

    /**
     * The application event class associated with the consumer, defaulting to
     * {@link EquinoxApplicationEvent}
     */
    Class<? extends EquinoxApplicationEvent> event() default EquinoxApplicationEvent.class;

}
