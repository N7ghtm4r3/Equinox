package com.tecknobit.equinoxcore.annotations

import com.tecknobit.equinoxcore.network.RequestMethod

/**
 * The `@RequestPath` annotation is used to make the request path, path parameters, query parameters,
 * and body parameters more readable and easier to manage in API request methods. It supports multiple
 * types of parameters including path, query, and body parameters, helping to organize the API request
 * construction in a more structured and understandable way
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class RequestPath(

    /**
     * The HTTP method for the request
     */
    val method: RequestMethod,

    /**
     * The base URL or path of the request endpoint
     */
    val path: String = "",

    /**
     * A comma-separated list of path parameter names for dynamic segments in the URL
     */
    val pathParameters: String = "",

    /**
     * A list of query parameters for the request, formatted as `key_parameter_one, key_parameter_two`
     */
    val queryParameters: String = "",

    /**
     * The body parameters of the request formatted as `key_parameter_one, key_parameter_two`
     */
    val bodyParameters: String = "",

    )
