package com.tecknobit.equinoxbackend.events;

import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import org.springframework.context.event.EventListener;

@FunctionalInterface
@FutureEquinoxApi(
        protoBehavior = """
                Must be improved and generalized as well in the official implementation
                """,
        releaseVersion = "1.1.1",
        additionalNotes = """
                - To document the entire system with related MD
                - Check improvements in the official release
                - Suggest to create a package (like configuration) "events" where integrate own events system like the custom
                enum, emitter and collector
                """
)
public interface EquinoxEventsCollector<T extends Enum<?>, E extends EquinoxApplicationEvent<T>> {

    @EventListener
    void onEventCollected(E event);

    interface OnEventConsumed {

        void perform(Object... extra);

    }

}
