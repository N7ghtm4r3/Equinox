package com.tecknobit.equinoxbackend.apis.events;

import jdk.jfr.Experimental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * The {@code EquinoxEventsEmitter} class is useful to share events between
 * services in a strict and controlled way by leveraging the {@code enums}.
 * It is based on top of the {@link ApplicationEventPublisher} API
 *
 * @param <T> The type of the event to emit
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.1.1
 */
@Component
@Experimental
public abstract class EquinoxEventsEmitter<T extends Enum<?>> {

    /**
     * {@code ApplicationEventPublisher} the publisher used to emit the events
     */
    private final ApplicationEventPublisher publisher;

    /**
     * Constructor to init the emitter
     *
     * @param publisher The publisher used to emit the events
     */
    @Autowired
    protected EquinoxEventsEmitter(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Method used to emit the event
     *
     * @param event The event to emit
     */
    public void emitEvent(EquinoxApplicationEvent<T> event) {
        publisher.publishEvent(event);
    }

}
