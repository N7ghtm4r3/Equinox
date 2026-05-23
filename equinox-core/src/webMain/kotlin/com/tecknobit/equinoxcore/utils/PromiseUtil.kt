@file:OptIn(ExperimentalWasmJsInterop::class)

package com.tecknobit.equinoxcore.utils

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.Promise

/**
 * Wrapper method to use await correctly a [Promise]
 *
 * @return the result of the promise as [T]
 *
 * @param T the type the promise have to return when resolved
 *
 * @since 1.2.0
 */
expect suspend fun <T : JsAny?> Promise<T>.await(): T