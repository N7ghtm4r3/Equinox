package com.tecknobit.equinoxcore.helpers

import com.tecknobit.equinoxcore.annotations.Wrapper
import kotlinx.serialization.json.*
import org.json.JSONException

/**
 * The `JsonHelper` class is a useful utility class for working with the `"JSON"` data format.
 *
 * This class helps avoid the [JSONException], as it returns a default or custom value chosen by the user if
 * the searched value is not present in the `"JSON"`. For all operations to retrieve values, you don't need to provide
 * the exact `"JSON"` path to the desired value, as it will be automatically resolved.
 *
 * This class uses the [org.json] library by default to work with `"JSON"`. The primary classes used are:
 * - [JsonObject]
 * - [JsonArray]
 *
 * All methods where a `"JSON"` structure is required will return one of these classes. Additionally, if this class is
 * instantiated using the constructor with a [String] parameter, it enables direct operations on the `"JSON"` and returns
 * the requested results.
 *
 * @author N7ghtm4r3 - Tecknobit
 */
class JsonHelper {

    companion object {

        /**
         * `NUMERIC_DEF_VALUE_IF_MISSED` is constant that memorizes default numeric value if the main is missed
         */
        const val NUMERIC_DEF_VALUE_IF_MISSED: Int = -1234567890

        /**
         * `NUMERIC_CLASS_CAST_ERROR_VALUE` is constant that memorizes default numeric value if [ClassCastException]
         * has been thrown
         */
        const val NUMERIC_CLASS_CAST_ERROR_VALUE: Int = -987654321

    }

    /**
     * `jsonObjectSource` is instance that memorizes [JsonObject] to work on
     */
    private lateinit var jsonObjectSource: JsonObject

    /**
     * `jsonArraySource` is instance that memorizes [JsonArray] to work on
     */
    private lateinit var jsonArraySource: JsonArray?

    /**
     * Constructor to init [JsonHelper] tool class
     *
     * @param jsonSource: the source of `"JSON"` to work on, it can be formatted as object or array `"JSON"` structures
     * @throws IllegalArgumentException when `"jsonSource"` inserted is not a valid `"JSON"` source
     * @apiNote will be called the `"toString()"` method, if it will be thrown an [IllegalArgumentException]
     * use directly the `"JsonHelper(String jsonSource)"` constructor or check validity of the `"JSON"` source
     * inserted
     */
    constructor(
        jsonSource: Any,
    ) : this(
        jsonSource = jsonSource.toString()
    )

    /**
     * Constructor to init [JsonHelper] tool class
     *
     * @param jsonObjectSource: jsonObject used to fetch data
     */
    constructor(
        jsonObjectSource: JsonObject,
    ) {
        this.jsonObjectSource = jsonObjectSource
        jsonArraySource = JsonArray()
    }

    /**
     * Constructor to init [JsonHelper] tool class
     *
     * @param jsonArraySource: jsonArray used to fetch data
     */
    constructor(
        jsonArraySource: JsonArray,
    ) {
        this.jsonArraySource = jsonArraySource
        jsonObjectSource = JsonObject()
    }

    /**
     * Constructor to init [JsonHelper] tool class
     *
     * @param jsonSource: the source of `"JSON"` to work on, it can be formatted as object or array `"JSON"` structures
     * @throws IllegalArgumentException when `"jsonSource"` inserted is not a valid `"JSON"` source
     */
    constructor(
        jsonSource: String,
    ) {
        val json = Json.parseToJsonElement(jsonSource)
        try {
            jsonObjectSource = json.jsonObject
        } catch (exception: IllegalArgumentException) {
            try {
                jsonArraySource = json.jsonArray
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("The JSON string source inserted is not a valid JSON source")
            }
        }
    }

    /**
     * Constructor to init [JsonHelper] tool class
     *
     * @param jsonObjectSource: jsonObject used to fetch data
     * @param jsonArraySource:  jsonArray used to fetch data
     */
    constructor(
        jsonObjectSource: JsonObject,
        jsonArraySource: JsonArray,
    ) {
        this.jsonObjectSource = jsonObjectSource
        this.jsonArraySource = jsonArraySource
    }

    /**
     * Method to get from [JsonObject] a string value
     *
     * @param key: key of string value to get from json
     * @return value as [String], if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    fun getString(
        key: String,
    ): String? {
        return getString(
            key = key,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonObject] a string value
     *
     * @param key:      key of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as [String], if it is not exist will return `defValue`
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    fun getString(
        key: String,
        defValue: String?,
    ): String? {
        return try {
            autoSearch(
                json = jsonObjectSource,
                searchKey = key,
                defValue = defValue
            )
        } catch (e: ClassCastException) {
            null
        }
    }

    /**
     * Method to get from [JsonArray] a string value
     *
     * @param index: index of string value to get from json
     * @return value as [String], if it is not exist will return null value
     */
    @Wrapper
    fun getString(
        index: Int,
    ): String? {
        return getString(
            index = index,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonArray] a string value
     *
     * @param index:    index of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as [String], if it is not exist will return `defValue`
     */
    fun getString(
        index: Int,
        defValue: String?,
    ): String? {
        return try {
            jsonArraySource?.get(index)?.jsonPrimitive?.content
        } catch (e: Exception) {
            defValue
        }
    }

    /**
     * Method to get from [JsonObject] a double value
     *
     * @param key: key of double value to get from json
     * @return value as double, if it is not exist will return [.NUMERIC_DEF_VALUE_IF_MISSED]
     * @throws ClassCastException: when this exception has been thrown will be returned [.NUMERIC_CLASS_CAST_ERROR_VALUE]
     * as default value
     */
    @Wrapper
    fun getDouble(
        key: String,
    ): Double {
        return getDouble(
            key = key,
            defValue = NUMERIC_DEF_VALUE_IF_MISSED.toDouble()
        )
    }

    /**
     * Method to get from [JsonObject] a double value
     *
     * @param key:      key of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as double, if it is not exist will return `defValue`
     * @throws ClassCastException: when this exception has been thrown will be returned [.NUMERIC_CLASS_CAST_ERROR_VALUE]
     * as default value
     */
    fun getDouble(
        key: String,
        defValue: Double,
    ): Double {
        try {
            val value: Any = autoSearch(
                json = jsonObjectSource,
                searchKey = key,
                defValue = defValue
            )
            return if (value is Number) value.toDouble()
            else value.toString().toDouble()
        } catch (e: ClassCastException) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE.toDouble()
        } catch (e: NumberFormatException) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE.toDouble()
        }
    }

    /**
     * Method to get from [JsonArray] a double value
     *
     * @param index: index of double value to get from json
     * @return value as double, if it is not exist will return [.NUMERIC_DEF_VALUE_IF_MISSED]
     */
    @Wrapper
    fun getDouble(
        index: Int,
    ): Double {
        return getDouble(
            index = index,
            defValue = NUMERIC_DEF_VALUE_IF_MISSED.toDouble()
        )
    }

    /**
     * Method to get from [JsonArray] a double value
     *
     * @param index:    index of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as double, if it is not exist will return `defValue`
     */
    fun getDouble(
        index: Int,
        defValue: Double,
    ): Double {
        return try {
            jsonArraySource?.get(index)?.jsonPrimitive?.content!!.toDouble()
        } catch (e: Exception) {
            defValue
        }
    }

    /**
     * Method to get from [JsonObject] an int value
     *
     * @param key: key of int value to get from json
     * @return value as int, if it is not exist will return [.NUMERIC_DEF_VALUE_IF_MISSED]
     * @throws ClassCastException: when this exception has been thrown will be returned [.NUMERIC_CLASS_CAST_ERROR_VALUE]
     * as default value
     */
    @Wrapper
    fun getInt(
        key: String,
    ): Int {
        return getInt(
            key = key,
            defValue = NUMERIC_DEF_VALUE_IF_MISSED
        )
    }

    /**
     * Method to get from [JsonObject] an int value
     *
     * @param key:      key of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as int, if it is not exist will return `defValue`
     * @throws ClassCastException: when this exception has been thrown will be returned [.NUMERIC_CLASS_CAST_ERROR_VALUE]
     * as default value
     */
    fun getInt(
        key: String,
        defValue: Int,
    ): Int {
        try {
            val value: Any = autoSearch(jsonObjectSource, key, defValue)
            return if (value is Number) value.toInt()
            else value.toString().toInt()
        } catch (e: ClassCastException) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE
        } catch (e: NumberFormatException) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE
        }
    }

    /**
     * Method to get from [JsonArray] an int value
     *
     * @param index: index of int value to get from json
     * @return value as int, if it is not exist will return [.NUMERIC_DEF_VALUE_IF_MISSED]
     */
    @Wrapper
    fun getInt(
        index: Int,
    ): Int {
        return getInt(
            index = index,
            defValue = NUMERIC_DEF_VALUE_IF_MISSED
        )
    }

    /**
     * Method to get from [JsonArray] an int value
     *
     * @param index:    index of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as int, if it is not exist will return `defValue`
     */
    fun getInt(
        index: Int,
        defValue: Int,
    ): Int {
        return try {
            jsonArraySource?.get(index)?.jsonPrimitive?.content!!.toInt()
        } catch (e: Exception) {
            defValue
        }
    }

    /**
     * Method to get from [JsonObject] a float value
     *
     * @param key: key of float value to get from json
     * @return value as float, if it is not exist will return [.NUMERIC_DEF_VALUE_IF_MISSED]
     * @throws ClassCastException: when this exception has been thrown will be returned [.NUMERIC_CLASS_CAST_ERROR_VALUE]
     * as default value
     */
    @Wrapper
    fun getFloat(
        key: String,
    ): Float {
        return getFloat(
            key = key,
            defValue = NUMERIC_DEF_VALUE_IF_MISSED.toFloat()
        )
    }

    /**
     * Method to get from [JsonObject] a float value
     *
     * @param key:      key of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as float, if it is not exist will return `defValue`
     * @throws ClassCastException: when this exception has been thrown will be returned [.NUMERIC_CLASS_CAST_ERROR_VALUE]
     * as default value
     */
    fun getFloat(
        key: String,
        defValue: Float,
    ): Float {
        try {
            val value: Any = autoSearch(jsonObjectSource, key, defValue)
            return if (value is Number) value.toFloat()
            else value.toString().toFloat()
        } catch (e: ClassCastException) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE.toFloat()
        } catch (e: NumberFormatException) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE.toFloat()
        }
    }

    /**
     * Method to get from [JsonArray] a float value
     *
     * @param index: index of float value to get from json
     * @return value as float, if it is not exist will return [.NUMERIC_DEF_VALUE_IF_MISSED]
     */
    @Wrapper
    fun getFloat(
        index: Int,
    ): Float {
        return getFloat(
            index = index,
            defValue = NUMERIC_DEF_VALUE_IF_MISSED.toFloat()
        )
    }

    /**
     * Method to get from [JsonArray] a float value
     *
     * @param index:    index of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as float, if it is not exist will return `defValue`
     */
    fun getFloat(
        index: Int,
        defValue: Float,
    ): Float {
        return try {
            jsonArraySource?.get(index)?.jsonPrimitive?.content!!.toFloat()
        } catch (e: Exception) {
            defValue
        }
    }

    /**
     * Method to get from [JsonObject] a long value
     *
     * @param key: key of long value to get from json
     * @return value as long, if it is not exist will return [.NUMERIC_DEF_VALUE_IF_MISSED]
     * @throws ClassCastException: when this exception has been thrown will be returned [.NUMERIC_CLASS_CAST_ERROR_VALUE]
     * as default value
     */
    @Wrapper
    fun getLong(
        key: String,
    ): Long {
        return getLong(
            key = key,
            defValue = NUMERIC_DEF_VALUE_IF_MISSED.toLong()
        )
    }

    /**
     * Method to get from [JsonObject] a long value
     *
     * @param key:      key of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as long, if it is not exist will return `defValue`
     * @throws ClassCastException: when this exception has been thrown will be returned [.NUMERIC_CLASS_CAST_ERROR_VALUE]
     * as default value
     */
    fun getLong(
        key: String,
        defValue: Long,
    ): Long {
        try {
            val value: Any = autoSearch(
                json = jsonObjectSource,
                searchKey = key,
                defValue = defValue
            )
            return if (value is Number) value.toLong()
            else value.toString().toLong()
        } catch (e: ClassCastException) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE.toLong()
        } catch (e: NumberFormatException) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE.toLong()
        }
    }

    /**
     * Method to get from [JsonArray] a long value
     *
     * @param index: index of long value to get from json
     * @return value as long, if it is not exist will return [.NUMERIC_DEF_VALUE_IF_MISSED]
     */
    @Wrapper
    fun getLong(
        index: Int,
    ): Long {
        return getLong(
            index = index,
            defValue = NUMERIC_DEF_VALUE_IF_MISSED.toLong()
        )
    }

    /**
     * Method to get from [JsonArray] a long value
     *
     * @param index:    index of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as long, if it is not exist will return `defValue`
     */
    fun getLong(
        index: Int,
        defValue: Long,
    ): Long {
        return try {
            jsonArraySource?.get(index)?.jsonPrimitive?.content!!.toLong()
        } catch (e: Exception) {
            defValue
        }
    }

    /**
     * Method to get from [JsonObject] an object value
     *
     * @param key: key of Object value to get from json
     * @return value as [Object], if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    fun get(
        key: String,
    ): Any? {
        return get(
            key = key,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonObject] an object value
     *
     * @param key:      key of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as [Object], if it is not exist will return `defValue`
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    fun get(
        key: String,
        defValue: Any?,
    ): Any? {
        return try {
            autoSearch(
                json = jsonObjectSource,
                searchKey = key,
                defValue = defValue
            )
        } catch (e: ClassCastException) {
            null
        }
    }

    /**
     * Method to get from [JsonArray] an object value
     *
     * @param index: index of Object value to get from json
     * @return value as [Object], if it is not exist will return null value
     */
    @Wrapper
    fun get(
        index: Int,
    ): Any? {
        return get(
            index = index,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonArray] an object value
     *
     * @param index:    index of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as [Object], if it is not exist will return `defValue`
     */
    fun get(
        index: Int,
        defValue: Any?,
    ): Any? {
        return try {
            jsonArraySource?.get(index)
        } catch (e: Exception) {
            defValue
        }
    }

    /**
     * Method to get from [JsonObject] a boolean value
     *
     * @param key: key of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * @throws ClassCastException: when this exception has been trowed will be returned false as default value
     */
    fun getBoolean(
        key: String,
    ): Boolean {
        return getBoolean(
            key = key,
            defValue = false
        )
    }

    /**
     * Method to get from [JsonObject] a boolean value
     *
     * @param key:      key of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return `defValue`
     * @throws ClassCastException: when this exception has been trowed will be returned false as default value
     */
    fun getBoolean(
        key: String,
        defValue: Boolean,
    ): Boolean {
        try {
            val value: Any = autoSearch(
                json = jsonObjectSource,
                searchKey = key,
                defValue = defValue
            )
            return if (value is Boolean) value
            else value.toString().toBoolean()
        } catch (e: ClassCastException) {
            return defValue
        }
    }

    /**
     * Method to get from [JsonArray] a boolean value
     *
     * @param index: index of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     */
    @Wrapper
    fun getBoolean(
        index: Int,
    ): Boolean {
        return getBoolean(
            index = index,
            defValue = false
        )
    }

    /**
     * Method to get from [JsonArray] a boolean value
     *
     * @param index:    index of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return `defValue`
     */
    fun getBoolean(
        index: Int,
        defValue: Boolean,
    ): Boolean {
        return try {
            jsonArraySource?.get(index)?.jsonPrimitive?.content!!.toBoolean()
        } catch (e: Exception) {
            defValue
        }
    }

    /**
     * Method to get from [JsonObject]  a list of values
     *
     * @param key: key of JsonArray to get from json
     * @return value as [JsonArray], if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    fun getJSONArray(
        key: String,
    ): JsonArray? {
        return getJSONArray(
            key = key,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonObject]  a list of values
     *
     * @param key:      key of JsonArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as [JsonArray], if it is not exist will return `defValue`
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    fun <T> getJSONArray(
        key: String,
        defValue: JsonArray?,
    ): JsonArray? {
        val value = autoSearch<T>(
            json = jsonObjectSource,
            searchKey = key,
            defValue = defValue
        )
        if (value is JsonArray) return value
        return defValue
    }

    /**
     * Method to get from [JsonArray]  a list of values
     *
     * @param index: index of JsonArray to get from json
     * @return value as [JsonArray], if it is not exist will return null value
     */
    @Wrapper
    fun getJSONArray(
        index: Int,
    ): JsonArray? {
        return getJSONArray(index, null)
    }

    /**
     * Method to get from [JsonArray]  a list of values
     *
     * @param index:    index of JsonArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as [JsonArray], if it is not exist will return `defValue`
     */
    fun getJSONArray(
        index: Int,
        defValue: JsonArray?,
    ): JsonArray? {
        return try {
            jsonArraySource.getJSONArray(index)
        } catch (e: Exception) {
            defValue
        }
    }

    /**
     * Method to get a list from the [.jsonArraySource]<br></br>
     * No-any params required
     *
     * @return list value as [List] of [T]
     * @implNote The [.jsonArraySource] must have the explicit type to create the correct list type, for example:
     *
     *  *
     * With the list formatted as:
     * <pre>
     * `[
     * true,
     * true,
     * false,
     * true
     * ]
    ` *
    </pre> *
     * the method will return a [List] of [Boolean]
     *
     *  *
     * With the list formatted as:
     * <pre>
     * `[
     * "true",
     * "true",
     * "false",
     * "true"
     * ]
    ` *
    </pre> *
     * the method will return a [List] of [String]
     *
     *
     */
    @Wrapper
    fun <T> toList(): List<T> {
        return toList(jsonArraySource)
    }

    /**
     * Method to get from [JsonObject] a jsonObject
     *
     * @param key: key of [JsonObject] to get from json
     * @return value as [JsonObject], if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    fun getJSONObject(
        key: String,
    ): JsonObject? {
        return getJSONObject(
            key = key,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonObject] a jsonObject
     *
     * @param key:      key of [JsonObject] to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as [JsonObject], if it is not exist will return `defValue`
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    fun getJSONObject(
        key: String,
        defValue: JsonObject?,
    ): JsonObject? {
        val value = autoSearch<Any>(
            json = jsonObjectSource,
            searchKey = key,
            defValue = defValue
        )
        if (value is JsonObject) return value
        return defValue
    }

    /**
     * Method to get from [JsonArray] a jsonObject
     *
     * @param index: index of [JsonObject] to get from json
     * @return value as [JsonObject], if it is not exist will return null value
     */
    @Wrapper
    fun getJSONObject(
        index: Int,
    ): JsonObject? {
        return getJSONObject(
            index = index,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonArray] a jsonObject
     *
     * @param index:    index of [JsonObject] to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as [JsonObject], if it is not exist will return `defValue`
     */
    fun getJSONObject(
        index: Int,
        defValue: JsonObject?,
    ): JsonObject? {
        return try {
            jsonArraySource.getJSONObject(index)
        } catch (e: Exception) {
            defValue
        }
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param key: key of [JsonHelper] to get from json
     * @return value as [JsonHelper], if it is not exist will return null
     */
    @Wrapper
    fun getJsonHelper(
        key: String,
    ): JsonHelper? {
        return getJsonHelper<JsonHelper?>(
            key = key,
            defValue = null
        )
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param key:      key of [JsonHelper] to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as [JsonHelper] if exists, if it is not exist will return `defValue` as [T]
     */
    fun <T> getJsonHelper(
        key: String,
        defValue: T,
    ): T {
        val value = get(
            key = key
        )
        return if (value != null) JsonHelper(value) as T
        else defValue
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param index: index of [JsonHelper] to get from json
     * @return value as [JsonHelper], if it is not exist will return null
     */
    @Wrapper
    fun getJsonHelper(
        index: Int,
    ): JsonHelper? {
        return getJsonHelper<JsonHelper?>(
            index = index,
            defValue = null
        )
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param index:    index of [JsonHelper] to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as [JsonHelper] if exists, if it is not exist will return `defValue` as [T]
     */
    fun <T> getJsonHelper(
        index: Int,
        defValue: T,
    ): T {
        val value = get(index)
        return if (value != null) JsonHelper(value) as T
        else defValue
    }

    /**
     * Method to get from [JsonArray] a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonArray] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     */
    @Wrapper
    fun <T> fetchList(
        searchKey: String,
    ): ArrayList<T> {
        return fetchList<T, ArrayList<*>?>(
            searchKey = searchKey,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonArray] a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonArray] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     */
    @Wrapper
    fun <T, V : ArrayList<*>?> fetchList(
        searchKey: String,
        defValue: V,
    ): ArrayList<T>? {
        return fetchList<T, V>(
            source = jsonObjectSource,
            searchKey = searchKey,
            defValue = defValue
        )
    }

    /**
     * Method to get from [JsonObject] a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonObject] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     */
    @Wrapper
    fun <T> fetchVList(
        searchKey: String,
    ): ArrayList<T>? {
        return fetchVList<T, ArrayList<*>?>(
            json = jsonObjectSource,
            searchKey = searchKey,
            listKey = null,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonObject] a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonObject] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     */
    @Wrapper
    fun <T, V : ArrayList<*>?> fetchVList(
        searchKey: String,
        defValue: V,
    ): ArrayList<T>? {
        return fetchVList<T, V>(
            json = jsonObjectSource,
            searchKey = searchKey,
            listKey = null,
            defValue = defValue
        )
    }

    /**
     * Method to get from [JsonObject] a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @param listKey:   list key for value to fetch
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonObject] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     * @implNote This method is useful when the value to fetch appears in multiple list
     */
    @Wrapper
    fun <T> fetchVList(
        searchKey: String,
        listKey: String?,
    ): ArrayList<T>? {
        return fetchVList<T, ArrayList<*>?>(
            searchKey = searchKey,
            listKey = listKey,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonObject] a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @param listKey:   list key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonObject] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     * @implNote This method is useful when the value to fetch appears in multiple list
     */
    @Wrapper
    fun <T, V : ArrayList<*>?> fetchVList(
        searchKey: String,
        listKey: String?,
        defValue: V,
    ): ArrayList<T>? {
        return fetchVList<T, V>(
            json = jsonObjectSource,
            searchKey = searchKey,
            listKey = listKey,
            defValue = defValue
        )
    }

    var jSONObjectSource: JsonObject?
        /**
         * Method to get [.jsonObjectSource] instance <br></br>
         * No-any params required
         *
         * @return [.jsonObjectSource] instance as [JsonObject]
         */
        get() = jsonObjectSource
        /**
         * Method to set [.jsonObjectSource] instance
         *
         * @param jsonObjectSource : new `"JSON"` source for [.jsonObjectSource]
         * @throws IllegalArgumentException when `"jsonObjectSource"` inserted is not a valid `"JSON"` source
         */
        set(jsonObjectSource) {
            try {
                this.jsonObjectSource = JsonObject(jsonObjectSource)
            } catch (e: JSONException) {
                throw IllegalArgumentException("The JSON source inserted is not a valid source")
            }
        }

    /**
     * Method to set [.jsonObjectSource] instance
     *
     * @param jsonObjectSource : new `"JSON"` source for [.jsonObjectSource]
     * @throws IllegalArgumentException when `"jsonObjectSource"` inserted is not a valid `"JSON"` source
     * @apiNote if it will be thrown an [IllegalArgumentException] use directly the [.setJSONArraySource]
     * method or check validity of the `"JSON"` source inserted
     */
    fun <T> setJSONObjectSource(jsonObjectSource: T) {
        try {
            this.jsonObjectSource = JsonObject(jsonObjectSource)
        } catch (e: JSONException) {
            throw IllegalArgumentException("The JSON source inserted is not a valid source")
        }
    }

    /**
     * Method to set [.jsonObjectSource] instance
     *
     * @param jsonObjectSource : new `"JSON"` source for [.jsonObjectSource]
     * @throws IllegalArgumentException when `"jsonObjectSource"` inserted is not a valid `"JSON"` source
     */
    fun setJSONObjectSource(jsonObjectSource: JsonObject?) {
        try {
            this.jsonObjectSource = jsonObjectSource
        } catch (e: JSONException) {
            throw IllegalArgumentException("The JSON source inserted is not a valid source")
        }
    }

    var jSONArraySource: JsonArray?
        /**
         * Method to get [.jsonArraySource] instance <br></br>
         * No-any params required
         *
         * @return [.jsonArraySource] instance as [JsonArray]
         */
        get() = jsonArraySource
        /**
         * Method to set [.jsonArraySource] instance
         *
         * @param jsonArraySource: new `"JSON"` source for [.jsonArraySource]
         * @throws IllegalArgumentException when `"jsonArraySource"` inserted is not a valid `"JSON"` source
         */
        set(jsonArraySource) {
            try {
                this.jsonArraySource = JsonArray(jsonArraySource)
            } catch (e: JSONException) {
                throw IllegalArgumentException("The JSON source inserted is not a valid source")
            }
        }

    /**
     * Method to set [.jsonArraySource] instance
     *
     * @param jsonArraySource: new `"JSON"` source for [.jsonArraySource]
     * @throws IllegalArgumentException when `"jsonArraySource"` inserted is not a valid `"JSON"` source
     * @apiNote if it will be thrown an [IllegalArgumentException] use directly the [.setJSONArraySource]
     * method or check validity of the `"JSON"` source inserted
     */
    fun <T> setJSONArraySource(jsonArraySource: T) {
        try {
            this.jsonArraySource = JsonArray(jsonArraySource.toString())
        } catch (e: JSONException) {
            throw IllegalArgumentException("The JSON source inserted is not a valid source")
        }
    }

    /**
     * Method to set [.jsonArraySource] instance
     *
     * @param jsonArraySource: new `"JSON"` source for [.jsonArraySource]
     * @throws IllegalArgumentException when `"jsonArraySource"` inserted is not a valid `"JSON"` source
     */
    fun setJSONArraySource(jsonArraySource: JsonArray?) {
        try {
            this.jsonArraySource = jsonArraySource
        } catch (e: JSONException) {
            throw IllegalArgumentException("The JSON source inserted is not a valid source")
        }
    }

    /**
     * Method to get from [JsonArray] a list of values automatically
     *
     * @param source:    [JsonObject] source to search the [JsonArray] from fetch the list
     * @param searchKey: key for value to fetch
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     */
    @Wrapper
    fun <T> fetchList(
        source: JsonObject,
        searchKey: String,
    ): ArrayList<T>? {
        return fetchList<T, ArrayList<*>?>(
            source = source,
            searchKey = searchKey,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonArray] a list of values automatically
     *
     * @param source:    [JsonObject] source to search the [JsonArray] from fetch the list
     * @param searchKey: key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     */
    fun <T, V : ArrayList<*>?> fetchList(
        source: JsonObject,
        searchKey: String,
        defValue: V?,
    ): ArrayList<T>? {
        var listFetched: ArrayList<T>? = null
        if (containsKey(source, searchKey) && listFetched == null) {
            val subBranches: ArrayList<JsonObject> = ArrayList<JsonObject>()
            for (key in source.keySet()) {
                val value: Any = source.get(key)
                if (value is JsonArray && key == searchKey) listFetched =
                    assembleList<ArrayList<T>>(value)
                else if (value is JsonObject) subBranches.add(value)
            }
            for (subBranch in subBranches) if (listFetched == null) listFetched = fetchList<T>(subBranch, searchKey)
        }
        if (listFetched == null && defValue != null) listFetched = defValue as ArrayList<T>
        return listFetched
    }

    /**
     * Method to get from [JsonObject] a list of values automatically
     *
     * @param json:      [JsonObject] from fetch list
     * @param searchKey: key for value to fetch
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonObject] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     */
    @Wrapper
    fun <T> fetchVList(
        json: JsonObject,
        searchKey: String,
    ): ArrayList<T> {
        return fetchVList<T, ArrayList<*>?>(
            json = json,
            searchKey = searchKey,
            listKey = null,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonObject] a list of values automatically
     *
     * @param json:      [JsonObject] from fetch list
     * @param searchKey: key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonObject] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     */
    @Wrapper
    fun <T, V : ArrayList<*>?> fetchVList(
        json: JsonObject,
        searchKey: String,
        defValue: V,
    ): ArrayList<T> {
        return fetchVList<T, V>(
            json = json,
            searchKey = searchKey,
            listKey = null,
            defValue = defValue
        )
    }

    /**
     * Method to get from [JsonObject] a list of values automatically
     *
     * @param json:      [JsonObject] from fetch list
     * @param searchKey: key for value to fetch
     * @param listKey:   list key for value to fetch
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonObject] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     * @implNote This method is useful when the value to fetch appears in multiple list
     */
    @Wrapper
    fun <T> fetchVList(
        json: JsonObject,
        searchKey: String,
        listKey: String?,
    ): ArrayList<T> {
        return fetchVList<T, ArrayList<*>?>(
            json = json,
            searchKey = searchKey,
            listKey = listKey,
            defValue = null
        )
    }

    /**
     * Method to get from [JsonObject] a list of values automatically
     *
     * @param json:      [JsonObject] from fetch list
     * @param searchKey: key for value to fetch
     * @param listKey:   list key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonObject] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     * @implNote This method is useful when the value to fetch appears in multiple list
     */
    fun <T, V : ArrayList<*>?> fetchVList(
        json: JsonObject,
        searchKey: String,
        listKey: String?,
        defValue: V?,
    ): ArrayList<T>? {
        var vList: ArrayList<T>? = null
        if (containsKey(json, searchKey)) {
            val subBranches: ArrayList<JsonObject> = ArrayList<JsonObject>()
            for (key in json.keySet()) {
                val value: Any = json.get(key)
                if (value is JsonArray) {
                    if (listKey == null || listKey == key) {
                        if (containsKey(value, searchKey)) {
                            vList = ArrayList<T>()
                            for (j in 0 until value.length()) vList.add(
                                get(
                                    value.getJSONObject(j),
                                    searchKey
                                ) as T?
                            )
                        }
                    }
                } else if (value is JsonObject && containsKey(value, searchKey)) return fetchVList<T>(
                    value,
                    searchKey,
                    listKey
                )
            }
        }
        if (vList == null && defValue != null) vList = defValue as ArrayList<T>
        return vList
    }

    /**
     * Method to get from [JsonObject] a generic value
     *
     * @param json:      [JsonObject] from fetch data
     * @param searchKey: key for value to fetch
     * @return value as [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonObject] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     */
    private fun <T> autoSearch(
        json: JsonObject,
        searchKey: String,
    ): T? {
        if (containsKey(json, searchKey)) {
            val subKeys: ArrayList<String> = ArrayList()
            for (key in json.keySet()) {
                if (key == searchKey) return json.get(searchKey)
                else {
                    val JSONType = json.get(key) as T
                    if (((JSONType is JsonObject || JSONType is JsonArray)
                                && JSONType.toString().contains(searchKey))
                    ) subKeys.add(key)
                }
            }
            for (subKey in subKeys) {
                val JSONType = json.get(subKey) as T
                if (JSONType is JsonObject) return autoSearch<T>(json.getJSONObject(subKey), searchKey)
                else if (JSONType is JsonArray) return assembleList<T>(json.getJSONArray(subKey))
            }
        }
        return null
    }

    /**
     * Method to check if a `"JSON"` source contains a specific key
     *
     * @param json:      `"JSON"` source
     * @param searchKey: key for value to fetch
     * @return whether the source constants the key specified
     */
    private fun containsKey(
        json: Any,
        searchKey: String,
    ): Boolean {
        // TODO: TO REVIEW
        return json.toString().contains("\"$searchKey\":")
    }

    /**
     * Method to get from [JsonObject] a generic value
     *
     * @param json:      [JsonObject] from fetch data
     * @param searchKey: key for value to fetch
     * @param defValue:  default value to return if primary value not exists
     * @return value as [T], if it is not exist will return `defValue`
     * @apiNote this method does not need specific path of the [JsonObject] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     */
    private fun <T> autoSearch(
        json: JsonObject,
        searchKey: String,
        defValue: T,
    ): T {
        val search = autoSearch<T>(
            json = json,
            searchKey = searchKey
        )
        if (search == null)
            return defValue
        return search
    }

    /**
     * Method to assemble a list from [JsonArray] a generic value
     *
     * @param list : [JsonArray] from fetch data
     * @return value as [ArrayList] of [T], if it is not exist will return null
     * @apiNote this method does not need specific path of the [JsonArray] to fetch value, but it will in automatic
     * reach the value requested directly from the entire `"JSON"` file without path inserted by hand
     */
    private fun <T> assembleList(
        list: JsonArray?,
    ): T {
        val values = ArrayList<T>()
        if (list != null) for (j in 0 until list.length()) values.add(list.get(j) as T)
        return values
    }

}
