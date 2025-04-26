package com.tecknobit.equinoxbackend.events;

import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@FutureEquinoxApi(
        protoBehavior = """
                At the moment is just a wrapper of the ApplicationEventPublisher interface, but will be improved and
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
public abstract class EquinoxEventsEmitter<T extends Enum<?>> {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Wrapper
    public void emitEvent(EquinoxApplicationEvent<T> event) {
        publisher.publishEvent(event);
    }

}
