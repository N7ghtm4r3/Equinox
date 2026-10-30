package com.tecknobit.equinoxbackend.apis.json;

import com.tecknobit.equinoxcore.annotations.Wrapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code JsonHelper} class is useful to read JSON configuration and payloads used by Equinox backends
 *
 * <p>Instances retain a {@link JSONObject} or {@link JSONArray} source, while static methods operate
 * on a supplied source. Key-based lookups search nested structures without requiring a full path;
 * index-based lookups read the selected array element
 *
 * <p>Typed accessors provide overloads with fallback values. Missing values and conversion failures
 * are handled according to each accessor's contract, including the numeric sentinel constants
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public class JsonHelper{

    /**
     * {@code NUMERIC_DEF_VALUE_IF_MISSED} the default sentinel used by primitive numeric lookups for missing values
     */
    public static final int NUMERIC_DEF_VALUE_IF_MISSED = -1234567890;

    /**
     * {@code NUMERIC_CLASS_CAST_ERROR_VALUE} the sentinel used when a key-based numeric conversion fails
     */
    public static final int NUMERIC_CLASS_CAST_ERROR_VALUE = -987654321;

    /**
     * {@code jsonObjectSource} the object source used by instance key-based lookups
     */
    private JSONObject jsonObjectSource;

    /**
     * {@code jsonArraySource} the array source used by instance index-based lookups
     */
    private JSONArray jsonArraySource;

    /**
     * Constructor to init the {@link JsonHelper} class from an object's string representation
     *
     * @param jsonSource The value whose string representation contains a JSON object or array
     * @param <T>        The type of the supplied source
     * @throws IllegalArgumentException when the string representation is not a valid JSON object or array
     */
    public <T> JsonHelper(T jsonSource) {
        this(jsonSource.toString());
    }

    /**
     * Constructor to init the {@link JsonHelper} class with an object source and an empty array source
     *
     * @param jsonObjectSource The JSON object used for key-based lookups
     */
    public JsonHelper(JSONObject jsonObjectSource) {
        this.jsonObjectSource = jsonObjectSource;
        jsonArraySource = new JSONArray();
    }

    /**
     * Constructor to init the {@link JsonHelper} class with an array source and an empty object source
     *
     * @param jsonArraySource The JSON array used for index-based lookups
     */
    public JsonHelper(JSONArray jsonArraySource) {
        this.jsonArraySource = jsonArraySource;
        jsonObjectSource = new JSONObject();
    }

    /**
     * Constructor to init the {@link JsonHelper} class by parsing a JSON object or array
     *
     * @param jsonSource The JSON object or array to parse
     * @throws IllegalArgumentException when the source is not a valid JSON object or array
     */
    public JsonHelper(String jsonSource) throws IllegalArgumentException {
        try {
            jsonObjectSource = new JSONObject(jsonSource);
        } catch (JSONException exception) {
            try {
                jsonArraySource = new JSONArray(jsonSource);
            } catch (JSONException e) {
                throw new IllegalArgumentException("The JSON string source inserted is not a valid JSON source");
            }
        }
    }

    /**
     * Constructor to init the {@link JsonHelper} class with object and array sources
     *
     * @param jsonObjectSource The JSON object used for key-based lookups
     * @param jsonArraySource  The JSON array used for index-based lookups
     */
    public JsonHelper(JSONObject jsonObjectSource, JSONArray jsonArraySource) {
        this.jsonObjectSource = jsonObjectSource;
        this.jsonArraySource = jsonArraySource;
    }

    /**
     * Method used to retrieve a string by searching the JSON object for a key
     *
     * <p>A failed cast returns {@code null}, including when a fallback was supplied
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@code null} when the key is not found, as {@link String}
     */
    @Wrapper
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * Method used to retrieve a string by searching the JSON object for a key
     *
     * <p>A failed cast returns {@code null}, including when a fallback was supplied
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@link String}
     */
    public String getString(String key, String defValue) {
        try {
            return autoSearch(jsonObjectSource, key, defValue);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Method used to retrieve a string by searching the JSON object for a key
     *
     * <p>A failed cast returns {@code null}, including when a fallback was supplied
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@code null} when the key is not found, as {@link String}
     */
    @Wrapper
    public static String getString(JSONObject jsonDetails, String key) {
        return getString(jsonDetails, key, null);
    }

    /**
     * Method used to retrieve a string by searching the JSON object for a key
     *
     * <p>A failed cast returns {@code null}, including when a fallback was supplied
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@link String}
     */
    public static String getString(JSONObject jsonDetails, String key, String defValue) {
        try {
            return autoSearch(jsonDetails, key, defValue);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Method used to retrieve a string at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link String}
     */
    @Wrapper
    public String getString(int index) {
        return getString(index, null);
    }

    /**
     * Method used to retrieve a string at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link String}
     */
    public String getString(int index, String defValue) {
        try {
            return jsonArraySource.getString(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a double value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the key is not found, as {@code double}
     */
    @Wrapper
    public double getDouble(String key) {
        return getDouble(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a double value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@code double}
     */
    public double getDouble(String key, double defValue) {
        try {
            Object value = autoSearch(jsonObjectSource, key, defValue);
            if (value instanceof Number)
                return ((Number) value).doubleValue();
            else
                return Double.parseDouble(value.toString());
        } catch (ClassCastException | NumberFormatException e) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /**
     * Method used to retrieve a double value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the key is not found, as {@code double}
     */
    @Wrapper
    public static double getDouble(JSONObject jsonDetails, String key) {
        return getDouble(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a double value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@code double}
     */
    public static double getDouble(JSONObject jsonDetails, String key, double defValue) {
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if (value instanceof Number)
                return ((Number) value).doubleValue();
            else
                return Double.parseDouble(value.toString());
        } catch (ClassCastException | NumberFormatException e) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /**
     * Method used to retrieve a double value at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the element cannot be read, as {@code double}
     */
    @Wrapper
    public double getDouble(int index) {
        return getDouble(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a double value at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@code double}
     */
    public double getDouble(int index, double defValue) {
        try {
            return jsonArraySource.getDouble(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve an integer value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the key is not found, as {@code int}
     */
    @Wrapper
    public int getInt(String key) {
        return getInt(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve an integer value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@code int}
     */
    public int getInt(String key, int defValue) {
        try {
            Object value = autoSearch(jsonObjectSource, key, defValue);
            if (value instanceof Number)
                return ((Number) value).intValue();
            else
                return Integer.parseInt(value.toString());
        } catch (ClassCastException | NumberFormatException e) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /**
     * Method used to retrieve an integer value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the key is not found, as {@code int}
     */
    @Wrapper
    public static int getInt(JSONObject jsonDetails, String key) {
        return getInt(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve an integer value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@code int}
     */
    public static int getInt(JSONObject jsonDetails, String key, int defValue) {
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if (value instanceof Number)
                return ((Number) value).intValue();
            else
                return Integer.parseInt(value.toString());
        } catch (ClassCastException | NumberFormatException e) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /**
     * Method used to retrieve an integer value at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the element cannot be read, as {@code int}
     */
    @Wrapper
    public int getInt(int index) {
        return getInt(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve an integer value at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@code int}
     */
    public int getInt(int index, int defValue) {
        try {
            return jsonArraySource.getInt(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a float value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the key is not found, as {@code float}
     */
    @Wrapper
    public float getFloat(String key) {
        return getFloat(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a float value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@code float}
     */
    public float getFloat(String key, float defValue) {
        try {
            Object value = autoSearch(jsonObjectSource, key, defValue);
            if (value instanceof Number)
                return ((Number) value).floatValue();
            else
                return Float.parseFloat(value.toString());
        } catch (ClassCastException | NumberFormatException e) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /**
     * Method used to retrieve a float value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the key is not found, as {@code float}
     */
    @Wrapper
    public static float getFloat(JSONObject jsonDetails, String key) {
        return getFloat(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a float value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@code float}
     */
    public static float getFloat(JSONObject jsonDetails, String key, float defValue) {
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if (value instanceof Number)
                return ((Number) value).floatValue();
            else
                return Float.parseFloat(value.toString());
        } catch (ClassCastException | NumberFormatException e) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /**
     * Method used to retrieve a float value at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the element cannot be read, as {@code float}
     */
    @Wrapper
    public float getFloat(int index) {
        return getFloat(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a float value at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@code float}
     */
    public float getFloat(int index, float defValue) {
        try {
            return jsonArraySource.getFloat(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a long value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the key is not found, as {@code long}
     */
    @Wrapper
    public long getLong(String key) {
        return getLong(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a long value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@code long}
     */
    public long getLong(String key, long defValue) {
        try {
            Object value = autoSearch(jsonObjectSource, key, defValue);
            if (value instanceof Number)
                return ((Number) value).longValue();
            else
                return Long.parseLong(value.toString());
        } catch (ClassCastException | NumberFormatException e) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /**
     * Method used to retrieve a long value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the key is not found, as {@code long}
     */
    @Wrapper
    public static long getLong(JSONObject jsonDetails, String key) {
        return getLong(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a long value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@code long}
     */
    public static long getLong(JSONObject jsonDetails, String key, long defValue) {
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if (value instanceof Number)
                return ((Number) value).longValue();
            else
                return Long.parseLong(value.toString());
        } catch (ClassCastException | NumberFormatException e) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /**
     * Method used to retrieve a long value at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the element cannot be read, as {@code long}
     */
    @Wrapper
    public long getLong(int index) {
        return getLong(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a long value at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@code long}
     */
    public long getLong(int index, long defValue) {
        try {
            return jsonArraySource.getLong(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a decimal value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return a {@link BigDecimal} representation of {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}. A null fallback also produces this sentinel
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or the numeric conversion-error sentinel when no value can be converted, as {@link BigDecimal}
     */
    @Wrapper
    public BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, null);
    }

    /**
     * Method used to retrieve a decimal value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return a {@link BigDecimal} representation of {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}. A null fallback also produces this sentinel
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@link BigDecimal}
     */
    public BigDecimal getBigDecimal(String key, BigDecimal defValue) {
        try {
            Object value = autoSearch(jsonObjectSource, key, defValue);
            if (value instanceof BigDecimal)
                return (BigDecimal) value;
            return BigDecimal.valueOf(Double.parseDouble(value.toString()));
        } catch (NullPointerException | ClassCastException | NumberFormatException e) {
            return BigDecimal.valueOf(NUMERIC_CLASS_CAST_ERROR_VALUE);
        }
    }

    /**
     * Method used to retrieve a decimal value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return a {@link BigDecimal} representation of {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}. A null fallback also produces this sentinel
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or the numeric conversion-error sentinel when no value can be converted, as {@link BigDecimal}
     */
    @Wrapper
    public static BigDecimal getBigDecimal(JSONObject jsonDetails, String key) {
        return getBigDecimal(jsonDetails, key, null);
    }

    /**
     * Method used to retrieve a decimal value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return a {@link BigDecimal} representation of {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}. A null fallback also produces this sentinel
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@link BigDecimal}
     */
    public static BigDecimal getBigDecimal(JSONObject jsonDetails, String key, BigDecimal defValue) {
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if (value instanceof BigDecimal)
                return (BigDecimal) value;
            return BigDecimal.valueOf(Double.parseDouble(value.toString()));
        } catch (NullPointerException | ClassCastException | NumberFormatException e) {
            return BigDecimal.valueOf(NUMERIC_CLASS_CAST_ERROR_VALUE);
        }
    }

    /**
     * Method used to retrieve a decimal value at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link BigDecimal}
     */
    @Wrapper
    public BigDecimal getBigDecimal(int index) {
        return getBigDecimal(index, null);
    }

    /**
     * Method used to retrieve a decimal value at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link BigDecimal}
     */
    public BigDecimal getBigDecimal(int index, BigDecimal defValue) {
        try {
            return jsonArraySource.getBigDecimal(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve an integer value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return a {@link BigInteger} representation of {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}. A null fallback also produces this sentinel
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or the numeric conversion-error sentinel when no value can be converted, as {@link BigInteger}
     */
    @Wrapper
    public BigInteger getBigInteger(String key) {
        return getBigInteger(key, null);
    }

    /**
     * Method used to retrieve an integer value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return a {@link BigInteger} representation of {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}. A null fallback also produces this sentinel
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@link BigInteger}
     */
    public BigInteger getBigInteger(String key, BigInteger defValue) {
        try {
            Object value = autoSearch(jsonObjectSource, key, defValue);
            if (value instanceof BigDecimal)
                return (BigInteger) value;
            return BigInteger.valueOf(Integer.parseInt(value.toString()));
        } catch (NullPointerException | ClassCastException | NumberFormatException e) {
            return BigInteger.valueOf(NUMERIC_CLASS_CAST_ERROR_VALUE);
        }
    }

    /**
     * Method used to retrieve an integer value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return a {@link BigInteger} representation of {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}. A null fallback also produces this sentinel
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or the numeric conversion-error sentinel when no value can be converted, as {@link BigInteger}
     */
    @Wrapper
    public static BigInteger getBigInteger(JSONObject jsonDetails, String key) {
        return getBigInteger(jsonDetails, key, null);
    }

    /**
     * Method used to retrieve an integer value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return a {@link BigInteger} representation of {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}. A null fallback also produces this sentinel
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@link BigInteger}
     */
    public static BigInteger getBigInteger(JSONObject jsonDetails, String key, BigInteger defValue) {
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if (value instanceof BigDecimal)
                return (BigInteger) value;
            return BigInteger.valueOf(Integer.parseInt(value.toString()));
        } catch (NullPointerException | ClassCastException | NumberFormatException e) {
            return BigInteger.valueOf(NUMERIC_CLASS_CAST_ERROR_VALUE);
        }
    }

    /**
     * Method used to retrieve an integer value at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link BigInteger}
     */
    @Wrapper
    public BigInteger getBigInteger(int index) {
        return getBigInteger(index, null);
    }

    /**
     * Method used to retrieve an integer value at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link BigInteger}
     */
    public BigInteger getBigInteger(int index, BigInteger defValue) {
        try {
            return jsonArraySource.getBigInteger(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a numeric value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the key is not found, as {@link Number}
     */
    @Wrapper
    public Number getNumber(String key) {
        return getNumber(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a numeric value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@link Number}
     */
    public Number getNumber(String key, Number defValue) {
        try {
            Object value = autoSearch(jsonObjectSource, key, defValue);
            return ((Number) value);
        } catch (ClassCastException | NumberFormatException e) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /**
     * Method used to retrieve a numeric value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the key is not found, as {@link Number}
     */
    @Wrapper
    public static Number getNumber(JSONObject jsonDetails, String key) {
        return getNumber(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a numeric value by searching the JSON object for a key
     *
     * <p>Failed numeric conversions return {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@link Number}
     */
    public static Number getNumber(JSONObject jsonDetails, String key, Number defValue) {
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            return ((Number) value);
        } catch (ClassCastException | NumberFormatException e) {
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /**
     * Method used to retrieve a numeric value at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link Number}
     */
    @Wrapper
    public Number getNumber(int index) {
        return getNumber(index, null);
    }

    /**
     * Method used to retrieve a numeric value at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link Number}
     */
    public Number getNumber(int index, Number defValue) {
        try {
            return jsonArraySource.getNumber(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a value by searching the JSON object for a key
     *
     * <p>A failed cast returns {@code null}, including when a fallback was supplied
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@code null} when the key is not found, as {@link Object}
     */
    @Wrapper
    public Object get(String key) {
        return get(key, null);
    }

    /**
     * Method used to retrieve a value by searching the JSON object for a key
     *
     * <p>A failed cast returns {@code null}, including when a fallback was supplied
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@link Object}
     */
    public Object get(String key, Object defValue) {
        try {
            return autoSearch(jsonObjectSource, key, defValue);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Method used to retrieve a value by searching the JSON object for a key
     *
     * <p>A failed cast returns {@code null}, including when a fallback was supplied
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@code null} when the key is not found, as {@link Object}
     */
    @Wrapper
    public static Object get(JSONObject jsonDetails, String key) {
        return get(jsonDetails, key, null);
    }

    /**
     * Method used to retrieve a value by searching the JSON object for a key
     *
     * <p>A failed cast returns {@code null}, including when a fallback was supplied
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@link Object}
     */
    public static Object get(JSONObject jsonDetails, String key, Object defValue) {
        try {
            return autoSearch(jsonDetails, key, defValue);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Method used to retrieve a value at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link Object}
     */
    @Wrapper
    public Object get(int index) {
        return get(index, null);
    }

    /**
     * Method used to retrieve a value at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link Object}
     */
    public Object get(int index, Object defValue) {
        try {
            return jsonArraySource.get(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a boolean value by searching the JSON object for a key
     *
     * <p>Boolean values are used directly; other values are parsed as text. A failed cast returns {@code false}
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@code false} when the key is not found, as {@code boolean}
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * Method used to retrieve a boolean value by searching the JSON object for a key
     *
     * <p>Boolean values are used directly; other values are parsed as text. A failed cast returns {@code defValue}
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@code boolean}
     */
    public boolean getBoolean(String key, boolean defValue) {
        try {
            Object value = autoSearch(jsonObjectSource, key, defValue);
            if (value instanceof Boolean)
                return (boolean) value;
            else
                return Boolean.parseBoolean(value.toString());
        } catch (ClassCastException e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a boolean value at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@code false} when the element cannot be read, as {@code boolean}
     */
    @Wrapper
    public boolean getBoolean(int index) {
        return getBoolean(index, false);
    }

    /**
     * Method used to retrieve a boolean value at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@code boolean}
     */
    public boolean getBoolean(int index, boolean defValue) {
        try {
            return jsonArraySource.getBoolean(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a boolean value by searching the JSON object for a key
     *
     * <p>Boolean values are used directly; other values are parsed as text. A failed cast returns {@code false}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the retrieved value, or {@code false} when the key is not found, as {@code boolean}
     */
    @Wrapper
    public static boolean getBoolean(JSONObject jsonDetails, String key) {
        return getBoolean(jsonDetails, key, false);
    }

    /**
     * Method used to retrieve a boolean value by searching the JSON object for a key
     *
     * <p>Boolean values are used directly; other values are parsed as text. A failed cast returns {@code defValue}
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the key is not found, as {@code boolean}
     */
    public static boolean getBoolean(JSONObject jsonDetails, String key, boolean defValue) {
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if (value instanceof Boolean)
                return (boolean) value;
            else
                return Boolean.parseBoolean(value.toString());
        } catch (ClassCastException e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve an array by searching the JSON object for a key
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the matching array, or {@code null} when absent or of another type, as {@link JSONArray}
     */
    @Wrapper
    public JSONArray getJSONArray(String key) {
        return getJSONArray(key, null);
    }

    /**
     * Method used to retrieve an array by searching the JSON object for a key
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the matching array, or {@code defValue} when absent or of another type, as {@link JSONArray}
     */
    public JSONArray getJSONArray(String key, JSONArray defValue) {
        Object value = autoSearch(jsonObjectSource, key, defValue);
        if (value instanceof JSONArray)
            return (JSONArray) value;

        return defValue;
    }

    /**
     * Method used to retrieve an array by searching the JSON object for a key
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the matching array, or {@code null} when absent or of another type, as {@link JSONArray}
     */
    @Wrapper
    public static JSONArray getJSONArray(JSONObject jsonDetails, String key) {
        return getJSONArray(jsonDetails, key, null);
    }

    /**
     * Method used to retrieve an array by searching the JSON object for a key
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the matching array, or {@code defValue} when absent or of another type, as {@link JSONArray}
     */
    public static JSONArray getJSONArray(JSONObject jsonDetails, String key, JSONArray defValue) {
        Object value = autoSearch(jsonDetails, key, defValue);
        if (value instanceof JSONArray)
            return (JSONArray) value;

        return defValue;
    }

    /**
     * Method used to retrieve an array at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link JSONArray}
     */
    @Wrapper
    public JSONArray getJSONArray(int index) {
        return getJSONArray(index, null);
    }

    /**
     * Method used to retrieve an array at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link JSONArray}
     */
    public JSONArray getJSONArray(int index, JSONArray defValue) {
        try {
            return jsonArraySource.getJSONArray(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve an object by searching the JSON object for a key
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     *
     * @return the matching object, or {@code null} when absent or of another type, as {@link JSONObject}
     */
    @Wrapper
    public static JSONObject getJSONObject(JSONObject jsonDetails, String key) {
        return getJSONObject(jsonDetails, key, null);
    }

    /**
     * Method used to retrieve an object by searching the JSON object for a key
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key to search for in the object and its nested structures
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the matching object, or {@code defValue} when absent or of another type, as {@link JSONObject}
     */
    public static JSONObject getJSONObject(JSONObject jsonDetails, String key, JSONObject defValue) {
        Object value = autoSearch(jsonDetails, key, defValue);
        if (value instanceof JSONObject)
            return (JSONObject) value;
        return defValue;
    }

    /**
     * Method used to retrieve an object at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link JSONObject}
     */
    @Wrapper
    public static JSONObject getJSONObject(JSONArray jsonDetails, int index) {
        return getJSONObject(jsonDetails, index, null);
    }

    /**
     * Method used to retrieve an object at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link JSONObject}
     */
    public static JSONObject getJSONObject(JSONArray jsonDetails, int index, JSONObject defValue){
        try {
            return jsonDetails.getJSONObject(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /**
     * Method used to retrieve a string at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link String}
     */
    @Wrapper
    public static String getString(JSONArray jsonDetails, int index) {
        return getString(jsonDetails, index, null);
    }

    /**
     * Method used to retrieve a string at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link String}
     */
    public static String getString(JSONArray jsonDetails, int index, String defValue) {
        try {
            return jsonDetails.getString(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a double value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the element cannot be read, as {@code double}
     */
    @Wrapper
    public static double getDouble(JSONArray jsonDetails, int index) {
        return getDouble(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a double value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@code double}
     */
    @Wrapper
    public static double getDouble(JSONArray jsonDetails, int index, double defValue) {
        try {
            return jsonDetails.getDouble(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve an integer value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the element cannot be read, as {@code int}
     */
    public static int getInt(JSONArray jsonDetails, int index) {
        return getInt(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve an integer value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@code int}
     */
    public static int getInt(JSONArray jsonDetails, int index, int defValue) {
        try {
            return jsonDetails.getInt(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a float value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the element cannot be read, as {@code float}
     */
    @Wrapper
    public static float getFloat(JSONArray jsonDetails, int index) {
        return getFloat(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a float value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@code float}
     */
    public static float getFloat(JSONArray jsonDetails, int index, float defValue) {
        try {
            return jsonDetails.getFloat(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a long value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@link #NUMERIC_DEF_VALUE_IF_MISSED} when the element cannot be read, as {@code long}
     */
    @Wrapper
    public static long getLong(JSONArray jsonDetails, int index) {
        return getLong(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method used to retrieve a long value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@code long}
     */
    public static long getLong(JSONArray jsonDetails, int index, long defValue) {
        try {
            return jsonDetails.getLong(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a decimal value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link BigDecimal}
     */
    @Wrapper
    public static BigDecimal getBigDecimal(JSONArray jsonDetails, int index) {
        return getBigDecimal(jsonDetails, index, null);
    }

    /**
     * Method used to retrieve a decimal value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link BigDecimal}
     */
    public static BigDecimal getBigDecimal(JSONArray jsonDetails, int index, BigDecimal defValue) {
        try {
            return jsonDetails.getBigDecimal(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve an integer value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link BigInteger}
     */
    @Wrapper
    public static BigInteger getBigInteger(JSONArray jsonDetails, int index) {
        return getBigInteger(jsonDetails, index, null);
    }

    /**
     * Method used to retrieve an integer value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link BigInteger}
     */
    public static BigInteger getBigInteger(JSONArray jsonDetails, int index, BigInteger defValue){
        try {
            return jsonDetails.getBigInteger(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /**
     * Method used to retrieve a numeric value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link Number}
     */
    @Wrapper
    public static Number getNumber(JSONArray jsonDetails, int index) {
        return getNumber(jsonDetails, index, null);
    }

    /**
     * Method used to retrieve a numeric value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link Number}
     */
    public static Number getNumber(JSONArray jsonDetails, int index, Number defValue){
        try {
            return jsonDetails.getNumber(index);
        }catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link Object}
     */
    @Wrapper
    public static Object get(JSONArray jsonDetails, int index){
        return get(jsonDetails, index, null);
    }

    /**
     * Method used to retrieve a value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link Object}
     */
    public static Object get(JSONArray jsonDetails, int index, Object defValue){
        try {
            return jsonDetails.get(index);
        }catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve a boolean value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@code false} when the element cannot be read, as {@code boolean}
     */
    @Wrapper
    public static boolean getBoolean(JSONArray jsonDetails, int index) {
        return getBoolean(jsonDetails, index, false);
    }

    /**
     * Method used to retrieve a boolean value at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@code boolean}
     */
    public static boolean getBoolean(JSONArray jsonDetails, int index, boolean defValue){
        try {
            return jsonDetails.getBoolean(index);
        }catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to retrieve an array at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link JSONArray}
     */
    @Wrapper
    public static JSONArray getJSONArray(JSONArray jsonDetails, int index) {
        return getJSONArray(jsonDetails, index, null);
    }

    /**
     * Method used to retrieve an array at a zero-based JSON array index
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the array element
     * @param defValue    The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link JSONArray}
     */
    public static JSONArray getJSONArray(JSONArray jsonDetails, int index, JSONArray defValue) {
        try {
            return jsonDetails.getJSONArray(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to convert the retained array source to a Java list
     *
     * <p>Element types follow the JSON values. Nested arrays and objects are converted by the JSON
     * library to lists and maps; the type parameter does not convert or validate element types
     *
     * @param <T> The expected list element type
     *
     * @return the converted elements as {@link List} of {@code T}
     */
    @Wrapper
    public <T> List<T> toList() {
        return toList(jsonArraySource);
    }

    /**
     * Method used to convert a supplied JSON array to a Java list
     *
     * <p>Element types follow the JSON values. Nested arrays and objects are converted by the JSON
     * library to lists and maps; the type parameter does not convert or validate element types
     *
     * @param jsonArray The JSON array to convert
     * @param <T>       The expected list element type
     *
     * @return the converted elements as {@link List} of {@code T}
     */
    public static <T> List<T> toList(JSONArray jsonArray) {
        return (List<T>) jsonArray.toList();
    }

    /**
     * Method used to retrieve an object by searching the JSON object for a key
     *
     * @param key The key to search for in the object and its nested structures
     *
     * @return the matching object, or {@code null} when absent or of another type, as {@link JSONObject}
     */
    @Wrapper
    public JSONObject getJSONObject(String key) {
        return getJSONObject(key, null);
    }

    /**
     * Method used to retrieve an object by searching the JSON object for a key
     *
     * @param key      The key to search for in the object and its nested structures
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the matching object, or {@code defValue} when absent or of another type, as {@link JSONObject}
     */
    public JSONObject getJSONObject(String key, JSONObject defValue) {
        Object value = autoSearch(jsonObjectSource, key, defValue);
        if (value instanceof JSONObject)
            return (JSONObject) value;

        return defValue;
    }

    /**
     * Method used to retrieve an object at a zero-based JSON array index
     *
     * @param index The zero-based index of the array element
     *
     * @return the retrieved value, or {@code null} when the element cannot be read, as {@link JSONObject}
     */
    @Wrapper
    public JSONObject getJSONObject(int index) {
        return getJSONObject(index, null);
    }

    /**
     * Method used to retrieve an object at a zero-based JSON array index
     *
     * @param index    The zero-based index of the array element
     * @param defValue The fallback to use when the requested value is unavailable
     *
     * @return the retrieved value, or {@code defValue} when the element cannot be read, as {@link JSONObject}
     */
    public JSONObject getJSONObject(int index, JSONObject defValue) {
        try {
            return jsonArraySource.getJSONObject(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method used to wrap a nested JSON object or array in a helper
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key used to locate the nested JSON source
     * @param <T>         The result type, compatible with JsonHelper and the supplied fallback
     *
     * @return the nested helper, or {@code null} when no source is found, as {@code T}
     * @throws IllegalArgumentException when a found value cannot be parsed as a JSON object or array
     */
    @Wrapper
    public static <T> T getJsonHelper(JSONObject jsonDetails, String key) {
        return getJsonHelper(jsonDetails, key, null);
    }

    /**
     * Method used to wrap a nested JSON object or array in a helper
     *
     * @param jsonDetails The JSON object to read
     * @param key         The key used to locate the nested JSON source
     * @param defValue    The fallback to return when no source is found
     * @param <T>         The result type, compatible with JsonHelper and the supplied fallback
     *
     * @return the nested helper, or {@code defValue} when no source is found, as {@code T}
     * @throws IllegalArgumentException when a found value cannot be parsed as a JSON object or array
     */
    public static <T> T getJsonHelper(JSONObject jsonDetails, String key, T defValue) {
        Object value = get(jsonDetails, key);
        if (value != null)
            return (T) new JsonHelper(value);
        else
            return defValue;
    }

    /**
     * Method used to wrap a nested JSON object or array in a helper
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the nested JSON source
     * @param <T>         The result type, compatible with JsonHelper and the supplied fallback
     *
     * @return the nested helper, or {@code null} when no source is found, as {@code T}
     * @throws IllegalArgumentException when a found value cannot be parsed as a JSON object or array
     */
    @Wrapper
    public static <T> T getJsonHelper(JSONArray jsonDetails, int index) {
        return getJsonHelper(jsonDetails, index, null);
    }

    /**
     * Method used to wrap a nested JSON object or array in a helper
     *
     * @param jsonDetails The JSON array to read
     * @param index       The zero-based index of the nested JSON source
     * @param defValue    The fallback to return when no source is found
     * @param <T>         The result type, compatible with JsonHelper and the supplied fallback
     *
     * @return the nested helper, or {@code defValue} when no source is found, as {@code T}
     * @throws IllegalArgumentException when a found value cannot be parsed as a JSON object or array
     */
    public static <T> T getJsonHelper(JSONArray jsonDetails, int index, T defValue) {
        Object value = get(jsonDetails, index);
        if (value != null)
            return (T) new JsonHelper(value);
        else
            return defValue;
    }

    /**
     * Method used to wrap a nested JSON object or array in a helper
     *
     * @param key The key used to locate the nested JSON source
     *
     * @return the nested helper, or {@code null} when no source is found, as {@link JsonHelper}
     * @throws IllegalArgumentException when a found value cannot be parsed as a JSON object or array
     */
    @Wrapper
    public JsonHelper getJsonHelper(String key) {
        return getJsonHelper(key, null);
    }

    /**
     * Method used to wrap a nested JSON object or array in a helper
     *
     * @param key      The key used to locate the nested JSON source
     * @param defValue The fallback to return when no source is found
     * @param <T>      The result type, compatible with JsonHelper and the supplied fallback
     *
     * @return the nested helper, or {@code defValue} when no source is found, as {@code T}
     * @throws IllegalArgumentException when a found value cannot be parsed as a JSON object or array
     */
    public <T> T getJsonHelper(String key, T defValue) {
        Object value = get(key);
        if (value != null)
            return (T) new JsonHelper(value);
        else
            return defValue;
    }

    /**
     * Method used to wrap a nested JSON object or array in a helper
     *
     * @param index The zero-based index of the nested JSON source
     *
     * @return the nested helper, or {@code null} when no source is found, as {@link JsonHelper}
     * @throws IllegalArgumentException when a found value cannot be parsed as a JSON object or array
     */
    @Wrapper
    public JsonHelper getJsonHelper(int index) {
        return getJsonHelper(index, null);
    }

    /**
     * Method used to wrap a nested JSON object or array in a helper
     *
     * @param index    The zero-based index of the nested JSON source
     * @param defValue The fallback to return when no source is found
     * @param <T>      The result type, compatible with JsonHelper and the supplied fallback
     *
     * @return the nested helper, or {@code defValue} when no source is found, as {@code T}
     * @throws IllegalArgumentException when a found value cannot be parsed as a JSON object or array
     */
    public <T> T getJsonHelper(int index, T defValue) {
        Object value = get(index);
        if (value != null)
            return (T) new JsonHelper(value);
        else
            return defValue;
    }

    /**
     * Method used to retrieve the elements of a named JSON array
     *
     * <p>The search follows nested object branches and returns the elements of the first matching array.
     * Element values retain their JSON types
     *
     * @param searchKey The key identifying the array whose elements are requested
     * @param <T>       The expected element type, compatible with the retrieved values
     *
     * @return the retrieved values, or null when no array is selected, as {@link ArrayList} of {@code T}
     */
    @Wrapper
    public <T> ArrayList<T> fetchList(String searchKey) {
        return fetchList(searchKey, null);
    }

    /**
     * Method used to retrieve the elements of a named JSON array
     *
     * <p>The search follows nested object branches and returns the elements of the first matching array.
     * Element values retain their JSON types
     *
     * @param searchKey The key identifying the array whose elements are requested
     * @param defValue  The fallback list used when the search reaches its no-result fallback
     * @param <T>       The expected element type, compatible with the retrieved values
     * @param <V>       The type of the fallback list
     *
     * @return the retrieved values, or the fallback or null when no array is selected, as {@link ArrayList} of {@code T}
     */
    @Wrapper
    public <T, V extends ArrayList<?>> ArrayList<T> fetchList(String searchKey, V defValue) {
        return fetchList(jsonObjectSource, searchKey, defValue);
    }

    /**
     * Method used to retrieve the elements of a named JSON array
     *
     * <p>The search follows nested object branches and returns the elements of the first matching array.
     * Element values retain their JSON types
     *
     * @param source    The JSON object whose object branches are searched
     * @param searchKey The key identifying the array whose elements are requested
     * @param <T>       The expected element type, compatible with the retrieved values
     *
     * @return the retrieved values, or null when no array is selected, as {@link ArrayList} of {@code T}
     */
    @Wrapper
    public static <T> ArrayList<T> fetchList(JSONObject source, String searchKey) {
        return fetchList(source, searchKey, null);
    }

    /**
     * Method used to retrieve the elements of a named JSON array
     *
     * <p>The search follows nested object branches and returns the elements of the first matching array.
     * Element values retain their JSON types
     *
     * @param source    The JSON object whose object branches are searched
     * @param searchKey The key identifying the array whose elements are requested
     * @param defValue  The fallback list used when the search reaches its no-result fallback
     * @param <T>       The expected element type, compatible with the retrieved values
     * @param <V>       The type of the fallback list
     *
     * @return the retrieved values, or the fallback or null when no array is selected, as {@link ArrayList} of {@code T}
     */
    public static <T, V extends ArrayList<?>> ArrayList<T> fetchList(JSONObject source, String searchKey, V defValue) {
        ArrayList<T> listFetched = null;
        if (containsKey(source, searchKey) && listFetched == null) {
            ArrayList<JSONObject> subBranches = new ArrayList<>();
            for (String key : source.keySet()) {
                Object value = source.get(key);
                if (value instanceof JSONArray && key.equals(searchKey))
                    listFetched = assembleList((JSONArray) value);
                else if (value instanceof JSONObject)
                    subBranches.add((JSONObject) value);
            }
            for (JSONObject subBranch : subBranches)
                if (listFetched == null)
                    listFetched = fetchList(subBranch, searchKey);
        }
        if (listFetched == null && defValue != null)
            listFetched = (ArrayList<T>) defValue;
        return listFetched;
    }

    /**
     * Method used to retrieve a key value from each object in a matching JSON array
     *
     * <p>Matching arrays must contain JSON objects. Values retain their JSON types and array order
     *
     * @param searchKey The key whose value is retrieved from each array element
     * @param <T>       The expected element type, compatible with the retrieved values
     *
     * @return the retrieved values, or null when no array is selected, as {@link ArrayList} of {@code T}
     * @throws JSONException when a matching array contains an element that is not a JSON object
     */
    @Wrapper
    public <T> ArrayList<T> fetchVList(String searchKey) {
        return fetchVList(jsonObjectSource, searchKey, null, null);
    }

    /**
     * Method used to retrieve a key value from each object in a matching JSON array
     *
     * <p>Matching arrays must contain JSON objects. Values retain their JSON types and array order
     *
     * <p>Recursive searches in nested objects do not propagate the supplied fallback
     *
     * @param searchKey The key whose value is retrieved from each array element
     * @param defValue  The fallback list used when the search reaches its no-result fallback
     * @param <T>       The expected element type, compatible with the retrieved values
     * @param <V>       The type of the fallback list
     *
     * @return the retrieved values, or the fallback or null when no array is selected, as {@link ArrayList} of {@code T}
     * @throws JSONException when a matching array contains an element that is not a JSON object
     */
    @Wrapper
    public <T, V extends ArrayList<?>> ArrayList<T> fetchVList(String searchKey, V defValue) {
        return fetchVList(jsonObjectSource, searchKey, null, defValue);
    }

    /**
     * Method used to retrieve a key value from each object in a matching JSON array
     *
     * <p>Matching arrays must contain JSON objects. Values retain their JSON types and array order
     *
     * @param json      The JSON object whose object branches are searched
     * @param searchKey The key whose value is retrieved from each array element
     * @param <T>       The expected element type, compatible with the retrieved values
     *
     * @return the retrieved values, or null when no array is selected, as {@link ArrayList} of {@code T}
     * @throws JSONException when a matching array contains an element that is not a JSON object
     */
    @Wrapper
    public static <T> ArrayList<T> fetchVList(JSONObject json, String searchKey) {
        return fetchVList(json, searchKey, null, null);
    }

    /**
     * Method used to retrieve a key value from each object in a matching JSON array
     *
     * <p>Matching arrays must contain JSON objects. Values retain their JSON types and array order
     *
     * <p>Recursive searches in nested objects do not propagate the supplied fallback
     *
     * @param json      The JSON object whose object branches are searched
     * @param searchKey The key whose value is retrieved from each array element
     * @param defValue  The fallback list used when the search reaches its no-result fallback
     * @param <T>       The expected element type, compatible with the retrieved values
     * @param <V>       The type of the fallback list
     *
     * @return the retrieved values, or the fallback or null when no array is selected, as {@link ArrayList} of {@code T}
     * @throws JSONException when a matching array contains an element that is not a JSON object
     */
    @Wrapper
    public static <T, V extends ArrayList<?>> ArrayList<T> fetchVList(JSONObject json, String searchKey, V defValue) {
        return fetchVList(json, searchKey, null, defValue);
    }

    /**
     * Method used to retrieve a key value from each object in a matching JSON array
     *
     * <p>Matching arrays must contain JSON objects. Values retain their JSON types and array order
     *
     * <p>The array key restricts which matching array supplies the values
     *
     * @param searchKey The key whose value is retrieved from each array element
     * @param listKey   The array key to select, or null to accept any matching array
     * @param <T>       The expected element type, compatible with the retrieved values
     *
     * @return the retrieved values, or null when no array is selected, as {@link ArrayList} of {@code T}
     * @throws JSONException when a matching array contains an element that is not a JSON object
     */
    @Wrapper
    public <T> ArrayList<T> fetchVList(String searchKey, String listKey) {
        return fetchVList(searchKey, listKey, null);
    }

    /**
     * Method used to retrieve a key value from each object in a matching JSON array
     *
     * <p>Matching arrays must contain JSON objects. Values retain their JSON types and array order
     *
     * <p>The array key restricts which matching array supplies the values
     *
     * <p>Recursive searches in nested objects do not propagate the supplied fallback
     *
     * @param searchKey The key whose value is retrieved from each array element
     * @param listKey   The array key to select, or null to accept any matching array
     * @param defValue  The fallback list used when the search reaches its no-result fallback
     * @param <T>       The expected element type, compatible with the retrieved values
     * @param <V>       The type of the fallback list
     *
     * @return the retrieved values, or the fallback or null when no array is selected, as {@link ArrayList} of {@code T}
     * @throws JSONException when a matching array contains an element that is not a JSON object
     */
    @Wrapper
    public <T, V extends ArrayList<?>> ArrayList<T> fetchVList(String searchKey, String listKey, V defValue) {
        return fetchVList(jsonObjectSource, searchKey, listKey, defValue);
    }

    /**
     * Method used to retrieve a key value from each object in a matching JSON array
     *
     * <p>Matching arrays must contain JSON objects. Values retain their JSON types and array order
     *
     * <p>The array key restricts which matching array supplies the values
     *
     * @param json      The JSON object whose object branches are searched
     * @param searchKey The key whose value is retrieved from each array element
     * @param listKey   The array key to select, or null to accept any matching array
     * @param <T>       The expected element type, compatible with the retrieved values
     *
     * @return the retrieved values, or null when no array is selected, as {@link ArrayList} of {@code T}
     * @throws JSONException when a matching array contains an element that is not a JSON object
     */
    @Wrapper
    public static <T> ArrayList<T> fetchVList(JSONObject json, String searchKey, String listKey) {
        return fetchVList(json, searchKey, listKey, null);
    }

    /**
     * Method used to retrieve a key value from each object in a matching JSON array
     *
     * <p>Matching arrays must contain JSON objects. Values retain their JSON types and array order
     *
     * <p>The array key restricts which matching array supplies the values
     *
     * <p>Recursive searches in nested objects do not propagate the supplied fallback
     *
     * @param json      The JSON object whose object branches are searched
     * @param searchKey The key whose value is retrieved from each array element
     * @param listKey   The array key to select, or null to accept any matching array
     * @param defValue  The fallback list used when the search reaches its no-result fallback
     * @param <T>       The expected element type, compatible with the retrieved values
     * @param <V>       The type of the fallback list
     *
     * @return the retrieved values, or the fallback or null when no array is selected, as {@link ArrayList} of {@code T}
     * @throws JSONException when a matching array contains an element that is not a JSON object
     */
    public static <T, V extends ArrayList<?>> ArrayList<T> fetchVList(JSONObject json, String searchKey, String listKey,
                                                                      V defValue) {
        ArrayList<T> vList = null;
        if (containsKey(json, searchKey)) {
            ArrayList<JSONObject> subBranches = new ArrayList<>();
            for (String key : json.keySet()) {
                Object value = json.get(key);
                if (value instanceof JSONArray list) {
                    if (listKey == null || listKey.equals(key)) {
                        if (containsKey(list, searchKey)) {
                            vList = new ArrayList<>();
                            for (int j = 0; j < list.length(); j++)
                                vList.add((T) JsonHelper.get(list.getJSONObject(j), searchKey));
                        }
                    }
                } else if (value instanceof JSONObject item && containsKey(item, searchKey))
                    return fetchVList(item, searchKey, listKey);
            }
        }
        if (vList == null && defValue != null)
            vList = (ArrayList<T>) defValue;
        return vList;
    }

    /**
     * Method used to locate a value by key in an object or a selected nested branch
     *
     * <p>Direct object keys take precedence. A selected nested object is searched recursively; a selected
     * array is returned as a list of its elements
     *
     * @param json      The JSON object to search
     * @param searchKey The key text used to select a value or nested branch
     * @param <T>       The expected result type, compatible with the selected value or list
     *
     * @return the selected value or list, or null when no value is found, as {@code T}
     */
    private static <T> T autoSearch(JSONObject json, String searchKey) {
        if (containsKey(json, searchKey)) {
            ArrayList<String> subKeys = new ArrayList<>();
            for (String key : json.keySet()) {
                if (key.equals(searchKey))
                    return (T) json.get(searchKey);
                else {
                    T JSONType = (T) json.get(key);
                    if (((JSONType instanceof JSONObject || JSONType instanceof JSONArray)
                            && JSONType.toString().contains(searchKey)))
                        subKeys.add(key);
                }
            }
            for (String subKey : subKeys) {
                T JSONType = (T) json.get(subKey);
                if (JSONType instanceof JSONObject)
                    return autoSearch(json.getJSONObject(subKey), searchKey);
                else if (JSONType instanceof JSONArray)
                    return assembleList(json.getJSONArray(subKey));
            }
        }
        return null;
    }

    /**
     * Method used to check whether a JSON representation contains the quoted key text
     *
     * @param json      The value whose JSON representation is inspected
     * @param searchKey The key text to search for between double quotes
     *
     * @return whether the quoted text occurs, including when it appears as a value, as {@code boolean}
     */
    private static boolean containsKey(Object json, String searchKey) {
        return json.toString().contains("\"" + searchKey + "\":");
    }

    /**
     * Method used to locate a JSON value with a fallback for an unsuccessful search
     *
     * @param json      The JSON object to search
     * @param searchKey The key text used to select a value or nested branch
     * @param defValue  The fallback to return when the selected search finds no value
     * @param <T>       The expected result type, compatible with the selected value and fallback
     *
     * @return the selected value or fallback as {@code T}
     */
    private static <T> T autoSearch(JSONObject json, String searchKey, T defValue) {
        T search = autoSearch(json, searchKey);
        if (search == null)
            return defValue;
        return search;
    }

    /**
     * Method used to copy JSON array elements into a Java array list
     *
     * @param list The array to copy, or null to produce an empty list
     * @param <T>  The expected result type, compatible with ArrayList
     *
     * @return the copied elements, or an empty list for null input, as {@code T}
     */
    private static <T> T assembleList(JSONArray list) {
        ArrayList<T> values = new ArrayList<>();
        if (list != null)
            for (int j = 0; j < list.length(); j++)
                values.add((T) list.get(j));
        return (T) values;
    }

    /**
     * Method used to get the object source retained for key-based lookups
     *
     * @return the object source, or null when not initialized, as {@link JSONObject}
     */
    public JSONObject getJSONObjectSource() {
        return jsonObjectSource;
    }

    /**
     * Method used to replace the object source using a supplied bean
     *
     * @param jsonObjectSource The bean whose readable properties supply the JSON object
     * @param <T>              The bean type
     * @throws IllegalArgumentException when the JSON library rejects the supplied source
     */
    public <T> void setJSONObjectSource(T jsonObjectSource) {
        try {
            this.jsonObjectSource = new JSONObject(jsonObjectSource);
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method used to replace the object source by parsing JSON text
     *
     * @param jsonObjectSource The JSON object text to parse
     * @throws IllegalArgumentException when the text is not a valid JSON object
     */
    public void setJSONObjectSource(String jsonObjectSource) {
        try {
            this.jsonObjectSource = new JSONObject(jsonObjectSource);
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method used to replace the object source retained for key-based lookups
     *
     * @param jsonObjectSource The JSON object to retain
     */
    public void setJSONObjectSource(JSONObject jsonObjectSource) {
        try {
            this.jsonObjectSource = jsonObjectSource;
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method used to get the array source retained for index-based lookups
     *
     * @return the array source, or null when not initialized, as {@link JSONArray}
     */
    public JSONArray getJSONArraySource() {
        return jsonArraySource;
    }

    /**
     * Method used to replace the array source by parsing a value's string representation
     *
     * @param jsonArraySource The value whose string representation contains a JSON array
     * @param <T>             The type of the supplied source
     * @throws IllegalArgumentException when the string representation is not a valid JSON array
     */
    public <T> void setJSONArraySource(T jsonArraySource) {
        try {
            this.jsonArraySource = new JSONArray(jsonArraySource.toString());
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method used to replace the array source by parsing JSON text
     *
     * @param jsonArraySource The JSON array text to parse
     * @throws IllegalArgumentException when the text is not a valid JSON array
     */
    public void setJSONArraySource(String jsonArraySource) {
        try {
            this.jsonArraySource = new JSONArray(jsonArraySource);
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method used to replace the array source retained for index-based lookups
     *
     * @param jsonArraySource The JSON array to retain
     */
    public void setJSONArraySource(JSONArray jsonArraySource) {
        try {
            this.jsonArraySource = jsonArraySource;
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method used to serialize the helper's exposed source properties as JSON
     *
     * @return the JSON representation of the helper as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}