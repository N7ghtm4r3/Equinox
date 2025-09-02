package com.tecknobit.equinoxbackend.apis.events;

import com.tecknobit.equinoxbackend.annotations.EventsHandler;
import jdk.jfr.Experimental;
import org.springframework.context.event.EventListener;

/**
 * The {@code EquinoxEventsCollector} is the interface that allows to collect all the events emitted by the
 * {@link EquinoxEventsEmitter} and to handle each specific event
 *
 * @param <T> The type of the event emitted
 * @param <E> The type of the custom application event to handle
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see EventsHandler
 *
 * @since 1.1.1
 */
@EventsHandler
@Experimental
@FunctionalInterface
public interface EquinoxEventsCollector<T extends Enum<?>, E extends EquinoxApplicationEvent<T>> {

    /**
     * Listener method used to handle the collected events
     *
     * @param event The emitted event to handle
     */
    @EventListener
    void onEventCollected(E event);

    /**
     * The {@code OnEventConsumed} interface used to implement a custom callback to invoke after the event has been
     * consumed
     *
     * @author N7ghtm4r3 - Tecknobit
     *
     * @since 1.1.1
     */
    @Experimental
    interface OnEventConsumed {

        /**
         * Method used to perform the callback
         *
         * @param extra The extra arguments shared with the performed event
         */
        void perform(Object... extra);

    }

}
