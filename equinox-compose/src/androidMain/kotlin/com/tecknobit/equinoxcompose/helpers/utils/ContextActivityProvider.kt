package com.tecknobit.equinoxcompose.helpers.utils

import android.app.Activity
import java.lang.ref.WeakReference

/**
 * A singleton object that helps to maintain a weak reference to the current [Activity]
 * This is useful to avoid memory leaks by preventing strong references to an Activity
 *
 * The weak reference ensures that the Activity can be garbage collected when no longer in use,
 * preventing holding onto the Activity object longer than necessary
 *
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Deprecated(
    message = "Will be removed in the future releases",
    replaceWith = ReplaceWith(
        expression = "com.tecknobit.equinoxcore.utilities.context.ContextActivityProvider"
    ),
    level = DeprecationLevel.WARNING
)
object ContextActivityProvider {

    /**
     * A [WeakReference] to the current [Activity]. This reference is weak to avoid memory leaks.
     */
    private var activityRef: WeakReference<Activity>? = null

    /**
     * Sets the current [Activity] by holding a weak reference to it.
     * This method is called to update the reference when the current Activity changes.
     *
     * @param activity: the [Activity] to be set as the current activity.
     * @throws IllegalArgumentException if the provided [activity] is null.
     */
    fun setCurrentActivity(
        activity: Activity,
    ) {
        activityRef = WeakReference(activity)
    }

    /**
     * Returns the current [Activity] if it is still available, otherwise returns null.
     * This method helps retrieve the activity without holding a strong reference to it.
     *
     * @return The current [Activity] or null if the reference has been garbage collected.
     */
    fun getCurrentActivity(): Activity? = activityRef?.get()

}
