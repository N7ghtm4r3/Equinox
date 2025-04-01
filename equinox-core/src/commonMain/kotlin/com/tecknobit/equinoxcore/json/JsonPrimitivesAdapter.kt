package com.tecknobit.equinoxcore.json

import kotlinx.serialization.json.*

/**
 * Method used to treats a [JsonElement] value as null-safe [Boolean]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Boolean] value
 *
 * @return the [JsonElement] as null-safe [Boolean]
 */
fun JsonElement?.treatsAsBoolean(
    defValue: Boolean = false,
): Boolean {
    return this.treatsAsNullableBoolean(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [Boolean]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Boolean] value
 *
 * @return the [JsonElement] as nullable [Boolean]
 */
fun JsonElement?.treatsAsNullableBoolean(
    defValue: Boolean? = null,
): Boolean? {
    return this?.jsonPrimitive?.booleanOrNull ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [Byte]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Byte] value
 *
 * @return the [JsonElement] as null-safe [Byte]
 */
fun JsonElement?.treatsAsByte(
    defValue: Byte = 0,
): Byte {
    return treatsAsNullableByte(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [Byte]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Byte] value
 *
 * @return the [JsonElement] as nullable [Byte]
 */
fun JsonElement?.treatsAsNullableByte(
    defValue: Byte? = null,
): Byte? {
    return this.getContent()?.toByteOrNull() ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [UByte]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [UByte] value
 *
 * @return the [JsonElement] as null-safe [UByte]
 */
fun JsonElement?.treatsAsUByte(
    defValue: UByte = 0u,
): UByte {
    return treatsAsNullableUByte(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [UByte]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [UByte] value
 *
 * @return the [JsonElement] as nullable [UByte]
 */
fun JsonElement?.treatsAsNullableUByte(
    defValue: UByte? = null,
): UByte? {
    return this.getContent()?.toUByteOrNull() ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [Short]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Short] value
 *
 * @return the [JsonElement] as null-safe [Short]
 */
fun JsonElement?.treatsAsShort(
    defValue: Short = 0,
): Short {
    return treatsAsNullableShort(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [Short]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Short] value
 *
 * @return the [JsonElement] as nullable [Short]
 */
fun JsonElement?.treatsAsNullableShort(
    defValue: Short? = null,
): Short? {
    return this.getContent()?.toShortOrNull() ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [UShort]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [UShort] value
 *
 * @return the [JsonElement] as null-safe [UShort]
 */
fun JsonElement?.treatsAsUShort(
    defValue: UShort = 0u,
): UShort {
    return treatsAsNullableUShort(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [UShort]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [UShort] value
 *
 * @return the [JsonElement] as nullable [UShort]
 */
fun JsonElement?.treatsAsNullableUShort(
    defValue: UShort? = null,
): UShort? {
    return this.getContent()?.toUShortOrNull() ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [Int]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Int] value
 *
 * @return the [JsonElement] as null-safe [Int]
 */
fun JsonElement?.treatsAsInt(
    defValue: Int = 0,
): Int {
    return treatsAsNullableInt(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [Int]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Int] value
 *
 * @return the [JsonElement] as nullable [Int]
 */
fun JsonElement?.treatsAsNullableInt(
    defValue: Int? = null,
): Int? {
    return this?.jsonPrimitive?.intOrNull ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [UInt]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [UInt] value
 *
 * @return the [JsonElement] as null-safe [UInt]
 */
fun JsonElement?.treatsAsUInt(
    defValue: UInt = 0u,
): UInt {
    return treatsAsNullableUInt(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [UInt]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [UInt] value
 *
 * @return the [JsonElement] as nullable [UInt]
 */
fun JsonElement?.treatsAsNullableUInt(
    defValue: UInt? = null,
): UInt? {
    return this.getContent()?.toUIntOrNull() ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [Float]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Float] value
 *
 * @return the [JsonElement] as null-safe [Float]
 */
fun JsonElement?.treatsAsFloat(
    defValue: Float = 0f,
): Float {
    return treatsAsNullableFloat(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [Float]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Float] value
 *
 * @return the [JsonElement] as nullable [Float]
 */
fun JsonElement?.treatsAsNullableFloat(
    defValue: Float? = null,
): Float? {
    return this?.jsonPrimitive?.floatOrNull ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [Double]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Double] value
 *
 * @return the [JsonElement] as null-safe [Double]
 */
fun JsonElement?.treatsAsDouble(
    defValue: Double = 0.0,
): Double {
    return treatsAsNullableDouble(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [Double]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Double] value
 *
 * @return the [JsonElement] as nullable [Double]
 */
fun JsonElement?.treatsAsNullableDouble(
    defValue: Double? = null,
): Double? {
    return this?.jsonPrimitive?.doubleOrNull ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [Long]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Long] value
 *
 * @return the [JsonElement] as null-safe [Long]
 */
fun JsonElement?.treatsAsLong(
    defValue: Long = 0,
): Long {
    return treatsAsNullableLong(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [Long]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [Long] value
 *
 * @return the [JsonElement] as nullable [Long]
 */
fun JsonElement?.treatsAsNullableLong(
    defValue: Long? = null,
): Long? {
    return this?.jsonPrimitive?.longOrNull ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [ULong]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [ULong] value
 *
 * @return the [JsonElement] as null-safe [ULong]
 */
fun JsonElement?.treatsAsULong(
    defValue: ULong = 0u,
): ULong {
    return treatsAsNullableULong(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [ULong]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [ULong] value
 *
 * @return the [JsonElement] as nullable [ULong]
 */
fun JsonElement?.treatsAsNullableULong(
    defValue: ULong? = null,
): ULong? {
    return this.getContent()?.toULongOrNull() ?: defValue
}

/**
 * Method used to treats a [JsonElement] value as null-safe [String]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [String] value
 *
 * @return the [JsonElement] as null-safe [String]
 */
fun JsonElement?.treatsAsString(
    defValue: String = "",
): String {
    return this.treatsAsNullableString(
        defValue = defValue
    )!!
}

/**
 * Method used to treats a [JsonElement] value as nullable [String]
 *
 * @param defValue The default value to return if the [JsonElement] is null or is not a [String] value
 *
 * @return the [JsonElement] as nullable [String]
 */
fun JsonElement?.treatsAsNullableString(
    defValue: String? = null,
): String? {
    return this.getContent() ?: defValue
}

/**
 * Method used to get the nullable content from a [JsonPrimitive] element
 *
 * @return the nullable content as [String] or null if not exists
 */
private fun JsonElement?.getContent(): String? {
    return this?.jsonPrimitive?.contentOrNull
}