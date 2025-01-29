package com.tecknobit.equinoxcore.annotations

/**
 * The `Assembler` annotation is useful to indicate those methods used to assemble anything such payloads of the requests,
 * queries of the requests, etc. For example
 *
 * ```kotlin
 * class CarsRequester : Requester() {
 *
 *    fun addCar(
 *      // car data
 *    ) {
 *       val payload = assembleCarPayload()
 *       // use payload
 *    }
 *
 *    @Assembler
 *    private assembleCarPayload() : JsonObject {
 *      // return the assembled payload
 *    }
 *
 *    fun addTyres(
 *      // car data
 *    ) {
 *       val payload = assembleCarPayload()
 *       // use payload
 *    }
 *
 *    @Assembler(
 *      structure = "{
 *          "model" = "model",
 *          "size" = "size"
 *      }"
 *    )
 *    private assembleTyresPayload() : JsonObject {
 *      // return the assembled payload
 *    }
 *
 *
 * }
 * ```
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.7
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Assembler(

    /**
     * The structure of the item returned by the method annotated as `@Assembler`
     */
    val structure: String = "",

    )
