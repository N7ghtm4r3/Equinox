![Static Badge](https://img.shields.io/badge/web-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

These APIs provide a wrapper for [Promise](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise)
to use that API across the two standard required by `JS` and `WasmJs` targets in the `web` target without any extra boilerplate

## await

### Description

Wrapper method to use await correctly a `Promise`

### Usage

```kotlin
fun getValue(): String {
    val anyPromise: Promise<String> = anyPromiseRoutineProducer()

    return anyPromise.await()
}
```
