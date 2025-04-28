package com.tecknobit.equinoxbackend.events;

import com.tecknobit.equinoxbackend.events.EquinoxEventsCollector.OnEventConsumed;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import jdk.jfr.Experimental;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * The {@code EquinoxApplicationEvent} is the class used to share the information of an event emitted by the
 * {@link EquinoxEventsEmitter} allowing the dedicated {@link EquinoxEventsCollector} to retrieve and use that information.
 * It is based on top of the {@link ApplicationEvent} API
 *
 * @param <T> The type of the event emitted
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.1.1
 */
@Experimental
public abstract class EquinoxApplicationEvent<T extends Enum<?>> extends ApplicationEvent {

    /**
     * {@code eventType} the type of the event emitted
     */
    private final T eventType;

    /**
     * {@code extra} arguments shared with the event
     */
    private Object[] extra;

    /**
     * {@code onEventConsumed} callback to invoke when the event has been collected and performed
     */
    private OnEventConsumed onEventConsumed;

    /**
     * Constructor used to create the event to emit
     *
     * @param source The source object where the event has been emitted
     * @param eventType The type of the event emitted
     * @param extra Arguments shared with the event
     */
    @Wrapper
    public EquinoxApplicationEvent(Object source, T eventType, Object... extra) {
        this(source, Clock.systemDefaultZone(), eventType, null, extra);
    }

    /**
     * Constructor used to create the event to emit
     *
     * @param source The source object where the event has been emitted
     * @param clock The clock which will provide the timestamp
     * @param eventType The type of the event emitted
     * @param extra Arguments shared with the event
     */
    @Wrapper
    public EquinoxApplicationEvent(Object source, Clock clock, T eventType, Object... extra) {
        this(source, clock, eventType, null, extra);
    }

    /**
     * Constructor used to create the event to emit
     *
     * @param source The source object where the event has been emitted
     * @param eventType The type of the event emitted
     * @param onEventConsumed The callback to invoke when the event has been collected and performed
     * @param extra Arguments shared with the event
     */
    public EquinoxApplicationEvent(Object source, T eventType, OnEventConsumed onEventConsumed, Object... extra) {
        super(source);
        this.eventType = eventType;
        this.onEventConsumed = onEventConsumed;
        this.extra = extra;
    }

    /**
     * Constructor used to create the event to emit
     *
     * @param source The source object where the event has been emitted
     * @param clock The clock which will provide the timestamp
     * @param eventType The type of the event emitted
     * @param onEventConsumed The callback to invoke when the event has been collected and performed
     * @param extra Arguments shared with the event
     */
    public EquinoxApplicationEvent(Object source, Clock clock, T eventType, OnEventConsumed onEventConsumed,
                                   Object... extra) {
        super(source, clock);
        this.eventType = eventType;
        this.onEventConsumed = onEventConsumed;
        this.extra = extra;
    }

    /**
     * Method to get {@link #eventType} instance
     *
     * @return the {@link #eventType} instance as {@link T}
     */
    public T getEventType() {
        return eventType;
    }

    /**
     * Method to get {@link #extra} instance
     *
     * @return the {@link #extra} instance as array of {@link Object}
     */
    public Object[] getExtra() {
        return extra;
    }

    /**
     * Method to set the {@link #extra} instance
     *
     * @param extra arguments shared with the event
     */
    public void setExtra(Object... extra) {
        this.extra = extra;
    }

    /**
     * Method to get {@link #onEventConsumed} instance
     *
     * @return the {@link #onEventConsumed} instance as array of {@link OnEventConsumed}
     */
    public OnEventConsumed getOnEventConsumed() {
        return onEventConsumed;
    }

    /**
     * Method to set the {@link #onEventConsumed} instance
     *
     * @param onEventConsumed The callback to invoke when the event has been collected and performed
     */
    public void setOnEventConsumed(OnEventConsumed onEventConsumed) {
        this.onEventConsumed = onEventConsumed;
    }

    /**
     * Method used to perform the {@link #onEventConsumed} callback
     *
     * @throws IllegalStateException when the {@link #onEventConsumed} is {@code null}
     */
    public void performOnEventConsumed() {
        if (onEventConsumed == null)
            throw new IllegalStateException("No any OnEventConsumed set");
        onEventConsumed.perform(extra);
    }

}
