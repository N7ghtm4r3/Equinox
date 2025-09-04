This annotation is useful to indicate those methods used to assemble anything such payloads of the requests,
queries of the requests, etc

## Usage 

```kotlin
class CarsRequester : Requester() {

    fun addCar(
        // car data
    ) {
        val payload = assembleCarPayload()
        // use payload
    }

    @Assembler
    private fun assembleCarPayload(): JsonObject {
        // return the assembled payload
        return JsonObject()
    }

    fun addTyres(
        // car data
    ) {
        val payload = assembleTyresPayload()
        // use payload
    }

    @Assembler(
        structure = """
        {
            "model": "model",
            "size": "size"
        }
        """
    )
    private fun assembleTyresPayload(): JsonObject {
        // return the assembled payload
        return JsonObject()
    }

}
```