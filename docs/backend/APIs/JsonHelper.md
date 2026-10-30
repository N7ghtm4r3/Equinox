`JsonHelper` reads JSON configuration and request payloads through typed accessors. It is available in
`com.tecknobit.equinoxbackend.apis.json` and is included in `equinox-backend`.

Use an instance when reading several values from the same source. Static overloads accept a
`JSONObject` or `JSONArray` when only one lookup is needed. The
[controller core](EquinoxControllerCore.md#json-utilities) already retains a helper for loaded request payloads.

## Read an object

The constructor accepts a `JSONObject`, a `JSONArray`, or text containing a JSON object or array.
Key-based accessors can search nested object branches without a complete property path.

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.apis.json.JsonHelper;
    import org.json.JSONObject;

    JSONObject payload = new JSONObject()
            .put("user", new JSONObject().put("name", "Jane"))
            .put("page", 2);

    JsonHelper helper = new JsonHelper(payload);
    String name = helper.getString("name"); // "Jane"
    int page = helper.getInt("page"); // 2
    String language = helper.getString("language", "en"); // "en"
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.apis.json.JsonHelper
    import org.json.JSONObject

    val payload = JSONObject()
        .put("user", JSONObject().put("name", "Jane"))
        .put("page", 2)

    val helper = JsonHelper(payload)
    val name = helper.getString("name") // "Jane"
    val page = helper.getInt("page") // 2
    val language = helper.getString("language", "en") // "en"
    ```

To select a specific nested object before reading its fields, use `getJsonHelper("user")`.
It returns another helper, or `null` when no value is found. A found value must represent a JSON
object or array; wrapping a scalar can throw `IllegalArgumentException`.

For an isolated lookup, use `JsonHelper.getString(payload, "name")` instead of creating an instance.

## Read an array

Index-based accessors use zero-based positions. Unlike key-based searches, they read the selected
array element directly. `toList` converts a JSON array to a Java list.

=== "Java"

    ```java
    import org.json.JSONArray;
    import java.util.List;

    JSONArray roles = new JSONArray().put("reader").put("editor");
    JsonHelper helper = new JsonHelper(roles);

    String firstRole = helper.getString(0); // "reader"
    String missingRole = helper.getString(5, "guest"); // "guest"
    List<String> values = JsonHelper.toList(roles);
    ```

=== "Kotlin"

    ```kotlin
    import org.json.JSONArray

    val roles = JSONArray().put("reader").put("editor")
    val helper = JsonHelper(roles)

    val firstRole = helper.getString(0) // "reader"
    val missingRole = helper.getString(5, "guest") // "guest"
    val values = JsonHelper.toList<String>(roles)
    ```

The list's element types follow the JSON values. Nested arrays and objects become lists and maps;
the generic type does not convert or validate those elements.

## Available operations

| Methods | Purpose |
| --- | --- |
| `getString`, `getBoolean` | Read text or boolean values |
| `getInt`, `getLong`, `getFloat`, `getDouble` | Read primitive numeric values |
| `getNumber`, `getBigDecimal`, `getBigInteger` | Read numeric object values |
| `get` | Read a value without selecting a specific result type |
| `getJSONObject`, `getJSONArray`, `getJsonHelper` | Read a nested JSON structure |
| `toList` | Convert an array to a Java list |
| `fetchList` | Search object branches for a named array and retrieve its elements |
| `fetchVList` | Retrieve a key's value from each object in a matching array; an optional array key restricts the search |
| `getJSONObjectSource`, `getJSONArraySource` | Access the retained source |
| `setJSONObjectSource`, `setJSONArraySource` | Replace the source used by subsequent lookups |

`fetchVList` expects matching arrays to contain JSON objects. Its recursive searches do not
propagate a supplied fallback into nested objects.

## Fallbacks and conversion failures

Most accessors have an overload accepting a fallback for a missing value. Array accessors also
return that fallback when the element cannot be read as the requested type. Key-based conversion
failures have accessor-specific results:

- Primitive numeric accessors use `NUMERIC_DEF_VALUE_IF_MISSED` (`-1234567890`) when no fallback is supplied
- Failed key-based numeric conversions use `NUMERIC_CLASS_CAST_ERROR_VALUE` (`-987654321`), including its numeric-object representations
- Key-based string lookups return `null` on a failed cast, even when a different fallback was supplied
- Boolean text is parsed with `Boolean.parseBoolean`; only a case-insensitive `"true"` produces `true`

A fallback is therefore not a general validation rule for malformed input. Use the
[validation constraints](../Validation/index.md) to validate request DTOs.

Keep array reads and object reads on the appropriate source. An instance constructed from JSON
text initializes the parsed source only; supply both sources through the two-argument constructor
when the same helper must serve both kinds of lookup.
