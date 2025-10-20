These APIs allow to treat the content of a `Json.jsonPrimitive` as arbitrary type without handling the cast
manually 

## Usage

### Supported types

| Method                    | Description                                                        |
|---------------------------|--------------------------------------------------------------------|
| `treatsAsBoolean`         | Method used to treats a `JsonElement` value as null-safe `Boolean` |
| `treatsAsNullableBoolean` | Method used to treats a `JsonElement` value as nullable `Boolean`  |
| `treatsAsByte`            | Method used to treats a `JsonElement` value as null-safe `Byte`    |
| `treatsAsNullableByte`    | Method used to treats a `JsonElement` value as nullable `Byte`     |
| `treatsAsUByte`           | Method used to treats a `JsonElement` value as null-safe `UByte`   |
| `treatsAsNullableUByte`   | Method used to treats a `JsonElement` value as nullable `UByte`    |
| `treatsAsShort`           | Method used to treats a `JsonElement` value as null-safe `Short`   |
| `treatsAsNullableShort`   | Method used to treats a `JsonElement` value as nullable `Short`    |
| `treatsAsUShort`          | Method used to treats a `JsonElement` value as null-safe `UShort`  |
| `treatsAsNullableUShort`  | Method used to treats a `JsonElement` value as nullable `UShort`   |
| `treatsAsInt`             | Method used to treats a `JsonElement` value as null-safe `Int`     |
| `treatsAsNullableInt`     | Method used to treats a `JsonElement` value as nullable `Int`      |
| `treatsAsUInt`            | Method used to treats a `JsonElement` value as null-safe `UInt`    |
| `treatsAsNullableUInt`    | Method used to treats a `JsonElement` value as nullable `UInt`     |
| `treatsAsFloat`           | Method used to treats a `JsonElement` value as null-safe `Float`   |
| `treatsAsNullableFloat`   | Method used to treats a `JsonElement` value as nullable `Float`    |
| `treatsAsDouble`          | Method used to treats a `JsonElement` value as null-safe `Double`  |
| `treatsAsNullableDouble`  | Method used to treats a `JsonElement` value as nullable `Double`   |
| `treatsAsLong`            | Method used to treats a `JsonElement` value as null-safe `Long`    |
| `treatsAsNullableLong`    | Method used to treats a `JsonElement` value as nullable `Long`     |
| `treatsAsULong`           | Method used to treats a `JsonElement` value as null-safe `ULong`   |
| `treatsAsNullableULong`   | Method used to treats a `JsonElement` value as nullable `ULong`    |
| `treatsAsString`          | Method used to treats a `JsonElement` value as null-safe `String`  |
| `treatsAsNullableString`  | Method used to treats a `JsonElement` value as nullable `String`   |

### Example

Following a comparison between not using the adapter and using it

#### Sample

```kotlin
val dummyJson = """
    {
        "boolean_key": true
    }
    """
```

#### Without adapter

```kotlin
val dummy = Json.decodeFromString<JsonObject>(
    string = dummyJson
)

val dummyBoolean: Boolean = dummy.jsonObject["boolean_key"]
    ?.jsonPrimitive?.boolean ?: false

println(dummyBoolean)
```

#### With adapter

```kotlin
val dummy = Json.decodeFromString<JsonObject>(
    string = dummyJson
)

val dummyBoolean: Boolean = dummy.jsonObject["boolean_key"].treatsAsBoolean(
    defValue = false
)

println(dummyBoolean)
```