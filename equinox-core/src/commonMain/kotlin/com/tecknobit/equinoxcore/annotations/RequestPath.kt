package com.tecknobit.equinoxcore.annotations

// TODO: TO WORK ON 
/*
 * `"@RequestPath"` annotation is applied to those methods offered by libraries that allow to make a request. <br></br>
 * This annotation is useful to make the request path and its possible path parameters more readable
 * <pre>
 * `//with no parameters
 * = GET, path = "https://play.google.com/store/apps/developer")
 * public void sendRequest() {
 * APIRequest apiRequest = new APIRequest();
 * apiRequest.sendAPIRequest("https://play.google.com/store/apps/developer", GET);
 * }
 *
 * //with path parameters
 * = GET, path = "https://play.google.com/store/apps/developer?{id}", path_parameters = "id")
 * public void sendRequest() {
 * APIRequest apiRequest = new APIRequest();
 * apiRequest.sendAPIRequest("https://play.google.com/store/apps/developer?id=Tecknobit", GET);
 * }
` *
</pre> *
 *
 * @author N7ghtm4r3 - Tecknobit
 *
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class RequestPath(

    /**
     * `method` of the request
     */
    val method: RequestMethod,

    /**
     * `path` of the request
     */
    val path: String = "",

    /**
     * `path_parameters` path parameters of the request -> endpoint/{parameter}
     */
    val pathParameters: String = "",

    /**
     * `query_parameters` query parameters of the request -> endpoint?{parameter}&{parameter1}
     */
    vararg val query: String,

    /**
     * `body_parameters` body parameters of the request ->
     * <pre>
     * `{
     * parameter : parameter value,
     * parameter1 : parameter1 value
     * }
    ` *
    </pre> *
     */
    val bodyParameters: String = ""

)
*/