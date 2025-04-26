package com.tecknobit.equinoxbackend.events;

import com.tecknobit.equinoxbackend.events.EquinoxEventsCollector.OnEventConsumed;
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@FutureEquinoxApi(
        protoBehavior = """
                At the moment is just a wrapper of the ApplicationEvent interface, but will be improved and
                generalized as well in the official implementation
                """,
        releaseVersion = "1.1.1",
        additionalNotes = """
                - To document the entire system with related MD
                - Check improvements in the official release
                - Suggest to create a package (like configuration) "events" where integrate own events system like the custom
                enum, emitter and collector
                """
)
public abstract class EquinoxApplicationEvent<T extends Enum<?>> extends ApplicationEvent {

    private final T eventType;

    private Object[] extra;

    private OnEventConsumed onEventConsumed;

    @Wrapper
    public EquinoxApplicationEvent(Object source, T eventType) {
        this(source, eventType, (Object) null);
    }

    @Wrapper
    public EquinoxApplicationEvent(Object source, Clock clock, T eventType) {
        this(source, clock, eventType, (Object[]) null);
    }

    @Wrapper
    public EquinoxApplicationEvent(Object source, T eventType, Object... extra) {
        this(source, Clock.systemDefaultZone(), eventType, null, extra);
    }

    @Wrapper
    public EquinoxApplicationEvent(Object source, Clock clock, T eventType, Object... extra) {
        this(source, clock, eventType, null, extra);
    }

    @Wrapper
    public EquinoxApplicationEvent(Object source, T eventType, OnEventConsumed onEventConsumed) {
        this(source, eventType, (Object) null, onEventConsumed);
    }

    @Wrapper
    public EquinoxApplicationEvent(Object source, Clock clock, T eventType, OnEventConsumed onEventConsumed) {
        this(source, clock, eventType, (Object[]) null, onEventConsumed);
    }

    public EquinoxApplicationEvent(Object source, T eventType, OnEventConsumed onEventConsumed, Object... extra) {
        super(source);
        this.eventType = eventType;
        this.extra = extra;
        this.onEventConsumed = onEventConsumed;
    }

    public EquinoxApplicationEvent(Object source, Clock clock, T eventType, OnEventConsumed onEventConsumed,
                                   Object... extra) {
        super(source, clock);
        this.eventType = eventType;
        this.extra = extra;
        this.onEventConsumed = onEventConsumed;
    }

    public T getEventType() {
        return eventType;
    }

    public Object[] getExtra() {
        return extra;
    }

    public void setExtra(Object... extra) {
        this.extra = extra;
    }

    public OnEventConsumed getOnEventConsumed() {
        return onEventConsumed;
    }

    public void setOnEventConsumed(OnEventConsumed onEventConsumed) {
        this.onEventConsumed = onEventConsumed;
    }

    public void performOnEventConsumed() {
        if (onEventConsumed == null)
            throw new IllegalStateException("No any OnEventConsumed set");
        onEventConsumed.perform();
    }

}
