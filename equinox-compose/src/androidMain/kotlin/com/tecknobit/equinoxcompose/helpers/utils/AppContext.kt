package com.tecknobit.equinoxcompose.helpers.utils

import android.app.Application
import android.content.Context

/**
 * Utility object that manages the application's `Context` across the app lifecycle.
 *
 * The `AppContext` object allows for safe access to the application's global `Context` throughout the app.
 * It ensures that the `Context` is initialized properly before being used by other components, preventing
 * potential issues like null pointer exceptions or uninitialized access.
 *
 * @throws Exception if the `Context` is accessed before initialization via `setUp`
 *
 */
@Deprecated(
    message = "Will be removed in the future releases",
    replaceWith = ReplaceWith(
        expression = "com.tecknobit.equinoxcore.utilities.context.AppContext"
    ),
    level = DeprecationLevel.WARNING
)
object AppContext {

    /**
     * `application` instance for the app, initialized via `setUp()` method
     */
    private lateinit var application: Application

    /**
     * Initializes the `AppContext` with the provided `Context`.
     *
     * This method should be called once in the application's lifecycle, usually
     * in the `onCreate()` method of the `Application` class.
     *
     * @param context: the application `Context`, typically cast from the `Context` of the calling component.
     */
    fun setUp(
        context: Context,
    ) {
        application = context as Application
    }

    /**
     * Provides the application-wide `Context`.
     *
     * If the context has not been initialized via `setUp()`, this method will throw an `Exception`.
     *
     * @return the application `Context`.
     * @throws Exception if the context is not yet initialized.
     */
    fun get(): Context {
        if (AppContext::application.isInitialized.not()) throw Exception("Application context isn't initialized")
        return application.applicationContext
    }

}