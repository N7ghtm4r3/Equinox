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
 * The {@code JsonHelper} class is a useful tool class to works with {@code "JSON"} data format
 *
 * @author N7ghtm4r3 - Tecknobit 
 * @apiNote this class is helpful to avoid the {@link JSONException} because if searched value is not present in {@code "JSON"}, will be
 * returned a default value or a custom value choose by you. For all the operations to get values you don't need to pass
 * the correct {@code "JSON"} path ro reach the wanted value because it will be automatically reached autonomously
 * @implSpec this class uses as default {@link org.json} library to work with {@code "JSON"}, the classes are:
 * <ul>
 *     <li>
 *         {@link JSONObject}
 *     </li>
 *     <li>
 *         {@link JSONArray}
 *     </li>
 * </ul>
 * This means that all methods where a {@code "JSON"}'s structure is requested will be returned one of that classes. <br>
 * In other methods, if this class has been instantiated using the constructor with {@link String} parameter, will be
 * possible work on {@code "JSON"} and return the result requested
 */
public class JsonHelper{

    /**
     * {@code NUMERIC_DEF_VALUE_IF_MISSED} is constant that memorizes default numeric value if the main is missed
     */
    public static final int NUMERIC_DEF_VALUE_IF_MISSED = -1234567890;

    /**
     * {@code NUMERIC_CLASS_CAST_ERROR_VALUE} is constant that memorizes default numeric value if {@link ClassCastException}
     * has been thrown
     */
    public static final int NUMERIC_CLASS_CAST_ERROR_VALUE = -987654321;

    /**
     * {@code jsonObjectSource} is instance that memorizes {@link JSONObject} to work on
     */
    private JSONObject jsonObjectSource;

    /**
     * {@code jsonArraySource} is instance that memorizes {@link JSONArray} to work on
     */
    private JSONArray jsonArraySource;

    /**
     * Constructor to init {@link JsonHelper} tool class
     *
     * @param jsonSource: the source of {@code "JSON"} to work on, it can be formatted as object or array {@code "JSON"} structures
     * @throws IllegalArgumentException when {@code "jsonSource"} inserted is not a valid {@code "JSON"} source
     * @apiNote will be called the {@code "toString()"} method, if it will be thrown an {@link IllegalArgumentException}
     * use directly the {@code "JsonHelper(String jsonSource)"} constructor or check validity of the {@code "JSON"} source
     * inserted
     */
    public <T> JsonHelper(T jsonSource) {
        this(jsonSource.toString());
    }

    /**
     * Constructor to init {@link JsonHelper} tool class
     *
     * @param jsonObjectSource: jsonObject used to fetch data
     */
    public JsonHelper(JSONObject jsonObjectSource) {
        this.jsonObjectSource = jsonObjectSource;
        jsonArraySource = new JSONArray();
    }

    /**
     * Constructor to init {@link JsonHelper} tool class
     *
     * @param jsonArraySource: jsonArray used to fetch data
     */
    public JsonHelper(JSONArray jsonArraySource) {
        this.jsonArraySource = jsonArraySource;
        jsonObjectSource = new JSONObject();
    }

    /**
     * Constructor to init {@link JsonHelper} tool class
     *
     * @param jsonSource: the source of {@code "JSON"} to work on, it can be formatted as object or array {@code "JSON"} structures
     * @throws IllegalArgumentException when {@code "jsonSource"} inserted is not a valid {@code "JSON"} source
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
     * Constructor to init {@link JsonHelper} tool class
     *
     * @param jsonObjectSource: jsonObject used to fetch data
     * @param jsonArraySource:  jsonArray used to fetch data
     */
    public JsonHelper(JSONObject jsonObjectSource, JSONArray jsonArraySource) {
        this.jsonObjectSource = jsonObjectSource;
        this.jsonArraySource = jsonArraySource;
    }

    /**
     * Method to get from {@link JSONObject} a string value
     *
     * @param key: key of string value to get from json
     * @return value as {@link String}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * Method to get from {@link JSONObject} a string value
     *
     * @param key:      key of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    public String getString(String key, String defValue) {
        try {
            return autoSearch(jsonObjectSource, key, defValue);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Method to get from {@link JSONObject} a string value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of string value to get from json
     * @return value as {@link String}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static String getString(JSONObject jsonDetails, String key) {
        return getString(jsonDetails, key, null);
    }

    /**
     * Method to get from {@link JSONObject} a string value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of string value to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    public static String getString(JSONObject jsonDetails, String key, String defValue) {
        try {
            return autoSearch(jsonDetails, key, defValue);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Method to get from {@link JSONArray} a string value
     *
     * @param index: index of string value to get from json
     * @return value as {@link String}, if it is not exist will return null value
     */
    @Wrapper
    public String getString(int index) {
        return getString(index, null);
    }

    /**
     * Method to get from {@link JSONArray} a string value
     *
     * @param index:    index of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     */
    public String getString(int index, String defValue) {
        try {
            return jsonArraySource.getString(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} a double value
     *
     * @param key: key of double value to get from json
     * @return value as double, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
     */
    @Wrapper
    public double getDouble(String key) {
        return getDouble(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONObject} a double value
     *
     * @param key:      key of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as double, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
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
     * Method to get from {@link JSONObject} a double value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of double value to get from json
     * @return value as double, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static double getDouble(JSONObject jsonDetails, String key) {
        return getDouble(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONObject} a double value
     *
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key: key of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as double, if it is not exist will return {@code defValue}
     * */
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
     * Method to get from {@link JSONArray} a double value
     *
     * @param index: index of double value to get from json
     * @return value as double, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     */
    @Wrapper
    public double getDouble(int index) {
        return getDouble(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONArray} a double value
     *
     * @param index:    index of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as double, if it is not exist will return {@code defValue}
     */
    public double getDouble(int index, double defValue) {
        try {
            return jsonArraySource.getDouble(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} an int value
     *
     * @param key: key of int value to get from json
     * @return value as int, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
     */
    @Wrapper
    public int getInt(String key) {
        return getInt(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONObject} an int value
     *
     * @param key:      key of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as int, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
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
     * Method to get from {@link JSONObject} an int value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of int value to get from json
     * @return value as int, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static int getInt(JSONObject jsonDetails, String key) {
        return getInt(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONObject} an int value
     *
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key: key of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as int, if it is not exist will return {@code defValue}
     * */
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
     * Method to get from {@link JSONArray} an int value
     *
     * @param index: index of int value to get from json
     * @return value as int, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     */
    @Wrapper
    public int getInt(int index) {
        return getInt(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONArray} an int value
     *
     * @param index:    index of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as int, if it is not exist will return {@code defValue}
     */
    public int getInt(int index, int defValue) {
        try {
            return jsonArraySource.getInt(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} a float value
     *
     * @param key: key of float value to get from json
     * @return value as float, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
     */
    @Wrapper
    public float getFloat(String key) {
        return getFloat(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONObject} a float value
     *
     * @param key:      key of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as float, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
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
     * Method to get from {@link JSONObject} a float value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of float value to get from json
     * @return value as float, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static float getFloat(JSONObject jsonDetails, String key) {
        return getFloat(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONObject} a float value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key: key of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as float, if it is not exist will return {@code defValue}
     * */
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
     * Method to get from {@link JSONArray} a float value
     *
     * @param index: index of float value to get from json
     * @return value as float, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     */
    @Wrapper
    public float getFloat(int index) {
        return getFloat(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONArray} a float value
     *
     * @param index:    index of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as float, if it is not exist will return {@code defValue}
     */
    public float getFloat(int index, float defValue) {
        try {
            return jsonArraySource.getFloat(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} a long value
     *
     * @param key: key of long value to get from json
     * @return value as long, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
     */
    @Wrapper
    public long getLong(String key) {
        return getLong(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONObject} a long value
     *
     * @param key:      key of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as long, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
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
     * Method to get from {@link JSONObject} a long value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of long value to get from json
     * @return value as long, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static long getLong(JSONObject jsonDetails, String key) {
        return getLong(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONObject} a long value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key: key of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as long, if it is not exist will return {@code defValue}
     * */
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
     * Method to get from {@link JSONArray} a long value
     *
     * @param index: index of long value to get from json
     * @return value as long, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     */
    @Wrapper
    public long getLong(int index) {
        return getLong(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONArray} a long value
     *
     * @param index:    index of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as long, if it is not exist will return {@code defValue}
     */
    public long getLong(int index, long defValue) {
        try {
            return jsonArraySource.getLong(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} a BigDecimal value
     *
     * @param key: key of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    public BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, null);
    }

    /**
     * Method to get from {@link JSONObject} a BigDecimal value
     *
     * @param key:      key of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
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
     * Method to get from {@link JSONObject} a BigDecimal value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static BigDecimal getBigDecimal(JSONObject jsonDetails, String key) {
        return getBigDecimal(jsonDetails, key, null);
    }

    /**
     * Method to get from {@link JSONObject} a BigDecimal value
     *
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key: key of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * */
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
     * Method to get from {@link JSONArray} a BigDecimal value
     *
     * @param index: index of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     */
    @Wrapper
    public BigDecimal getBigDecimal(int index) {
        return getBigDecimal(index, null);
    }

    /**
     * Method to get from {@link JSONArray} a BigDecimal value
     *
     * @param index:    index of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     */
    public BigDecimal getBigDecimal(int index, BigDecimal defValue) {
        try {
            return jsonArraySource.getBigDecimal(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} a BigInteger value
     *
     * @param key: key of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    public BigInteger getBigInteger(String key) {
        return getBigInteger(key, null);
    }

    /**
     * Method to get from {@link JSONObject} a BigInteger value
     *
     * @param key:      key of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
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
     * Method to get from {@link JSONObject} a BigInteger value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static BigInteger getBigInteger(JSONObject jsonDetails, String key) {
        return getBigInteger(jsonDetails, key, null);
    }

    /**
     * Method to get from {@link JSONObject} a BigInteger value
     *
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key: key of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * */
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
     * Method to get from {@link JSONArray} a BigInteger value
     *
     * @param index: index of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     */
    @Wrapper
    public BigInteger getBigInteger(int index) {
        return getBigInteger(index, null);
    }

    /**
     * Method to get from {@link JSONArray} a BigInteger value
     *
     * @param index:    index of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     */
    public BigInteger getBigInteger(int index, BigInteger defValue) {
        try {
            return jsonArraySource.getBigInteger(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} a Number value
     *
     * @param key: key of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    public Number getNumber(String key) {
        return getNumber(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONObject} a Number value
     *
     * @param key:      key of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
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
     * Method to get from {@link JSONObject} a Number value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static Number getNumber(JSONObject jsonDetails, String key) {
        return getNumber(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONObject} a Number value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of Number value to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
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
     * Method to get from {@link JSONArray} a Number value
     *
     * @param index: index of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     */
    @Wrapper
    public Number getNumber(int index) {
        return getNumber(index, null);
    }

    /**
     * Method to get from {@link JSONArray} a Number value
     *
     * @param index:    index of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     */
    public Number getNumber(int index, Number defValue) {
        try {
            return jsonArraySource.getNumber(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} an object value
     *
     * @param key: key of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    public Object get(String key) {
        return get(key, null);
    }

    /**
     * Method to get from {@link JSONObject} an object value
     *
     * @param key:      key of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    public Object get(String key, Object defValue) {
        try {
            return autoSearch(jsonObjectSource, key, defValue);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Method to get from {@link JSONObject} an Object value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static Object get(JSONObject jsonDetails, String key) {
        return get(jsonDetails, key, null);
    }

    /**
     * Method to get from {@link JSONObject} an Object value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of Object value to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    public static Object get(JSONObject jsonDetails, String key, Object defValue) {
        try {
            return autoSearch(jsonDetails, key, defValue);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Method to get from {@link JSONArray} an object value
     *
     * @param index: index of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     */
    @Wrapper
    public Object get(int index) {
        return get(index, null);
    }

    /**
     * Method to get from {@link JSONArray} an object value
     *
     * @param index:    index of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     */
    public Object get(int index, Object defValue) {
        try {
            return jsonArraySource.get(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} a boolean value
     *
     * @param key: key of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * @throws ClassCastException: when this exception has been trowed will be returned false as default value
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * Method to get from {@link JSONObject} a boolean value
     *
     * @param key:      key of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned false as default value
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
     * Method to get from {@link JSONArray} a boolean value
     *
     * @param index: index of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     */
    @Wrapper
    public boolean getBoolean(int index) {
        return getBoolean(index, false);
    }

    /**
     * Method to get from {@link JSONArray} a boolean value
     *
     * @param index:    index of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return {@code defValue}
     */
    public boolean getBoolean(int index, boolean defValue) {
        try {
            return jsonArraySource.getBoolean(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} a boolean value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * @throws ClassCastException: when this exception has been trowed will be returned false as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static boolean getBoolean(JSONObject jsonDetails, String key) {
        return getBoolean(jsonDetails, key, false);
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key: key of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned false as default value
     * @return value as boolean, if it is not exist will return {@code defValue}
     * */
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
     * Method to get from {@link JSONObject}  a list of values
     *
     * @param key: key of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    public JSONArray getJSONArray(String key) {
        return getJSONArray(key, null);
    }

    /**
     * Method to get from {@link JSONObject}  a list of values
     *
     * @param key:      key of JSONArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    public JSONArray getJSONArray(String key, JSONArray defValue) {
        Object value = autoSearch(jsonObjectSource, key, defValue);
        if (value instanceof JSONArray)
            return (JSONArray) value;

        return defValue;
    }

    /**
     * Method to get from {@link JSONObject}  a list of values
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static JSONArray getJSONArray(JSONObject jsonDetails, String key) {
        return getJSONArray(jsonDetails, key, null);
    }

    /**
     * Method to get from {@link JSONObject}  a list of values
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of JSONArray to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    public static JSONArray getJSONArray(JSONObject jsonDetails, String key, JSONArray defValue) {
        Object value = autoSearch(jsonDetails, key, defValue);
        if (value instanceof JSONArray)
            return (JSONArray) value;

        return defValue;
    }

    /**
     * Method to get from {@link JSONArray}  a list of values
     *
     * @param index: index of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     */
    @Wrapper
    public JSONArray getJSONArray(int index) {
        return getJSONArray(index, null);
    }

    /**
     * Method to get from {@link JSONArray}  a list of values
     *
     * @param index:    index of JSONArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     */
    public JSONArray getJSONArray(int index, JSONArray defValue) {
        try {
            return jsonArraySource.getJSONArray(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONObject} a jsonObject
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of {@link JSONObject} to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static JSONObject getJSONObject(JSONObject jsonDetails, String key) {
        return getJSONObject(jsonDetails, key, null);
    }

    /**
     * Method to get from {@link JSONObject} a jsonObject
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of {@link JSONObject} to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    public static JSONObject getJSONObject(JSONObject jsonDetails, String key, JSONObject defValue) {
        Object value = autoSearch(jsonDetails, key, defValue);
        if (value instanceof JSONObject)
            return (JSONObject) value;
        return defValue;
    }

    /**
     * Method to get from {@link JSONArray} a jsonObject
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of {@link JSONObject} to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static JSONObject getJSONObject(JSONArray jsonDetails, int index) {
        return getJSONObject(jsonDetails, index, null);
    }

    /** Method to get from {@link JSONArray} a jsonObject
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index: index of {@link JSONObject} to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * */
    public static JSONObject getJSONObject(JSONArray jsonDetails, int index, JSONObject defValue){
        try {
            return jsonDetails.getJSONObject(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONArray} a string value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of string value to get from json
     * @return value as {@link String}, if it is not exist will return null value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static String getString(JSONArray jsonDetails, int index) {
        return getString(jsonDetails, index, null);
    }

    /**
     * Method to get from {@link JSONObject} a string value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       key of string value to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     */
    public static String getString(JSONArray jsonDetails, int index, String defValue) {
        try {
            return jsonDetails.getString(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONArray} a double value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of double value to get from json
     * @return value as double, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static double getDouble(JSONArray jsonDetails, int index) {
        return getDouble(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONArray} a double value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of double value to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as double, if it is not exist will return {@code defValue}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
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
     * Method to get from {@link JSONArray} an int value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of int value to get from json
     * @return value as int, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    public static int getInt(JSONArray jsonDetails, int index) {
        return getInt(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONArray} an int value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of int value to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as int, if it is not exist will return {@code defValue}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    public static int getInt(JSONArray jsonDetails, int index, int defValue) {
        try {
            return jsonDetails.getInt(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONArray} a float value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of float value to get from json
     * @return value as float, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static float getFloat(JSONArray jsonDetails, int index) {
        return getFloat(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONArray} a float value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of float value to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as float, if it is not exist will return {@code defValue}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    public static float getFloat(JSONArray jsonDetails, int index, float defValue) {
        try {
            return jsonDetails.getFloat(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONArray} a long value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of long value to get from json
     * @return value as long, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static long getLong(JSONArray jsonDetails, int index) {
        return getLong(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /**
     * Method to get from {@link JSONArray} a long value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of long value to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as long, if it is not exist will return {@code defValue}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    public static long getLong(JSONArray jsonDetails, int index, long defValue) {
        try {
            return jsonDetails.getLong(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONArray} a BigDecimal value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static BigDecimal getBigDecimal(JSONArray jsonDetails, int index) {
        return getBigDecimal(jsonDetails, index, null);
    }

    /**
     * Method to get from {@link JSONArray} a BigDecimal value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of BigDecimal value to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    public static BigDecimal getBigDecimal(JSONArray jsonDetails, int index, BigDecimal defValue) {
        try {
            return jsonDetails.getBigDecimal(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONArray} a BigInteger value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static BigInteger getBigInteger(JSONArray jsonDetails, int index) {
        return getBigInteger(jsonDetails, index, null);
    }

    /** Method to get from {@link JSONArray} a BigInteger value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index: index of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * */
    public static BigInteger getBigInteger(JSONArray jsonDetails, int index, BigInteger defValue){
        try {
            return jsonDetails.getBigInteger(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONArray} a Number value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static Number getNumber(JSONArray jsonDetails, int index) {
        return getNumber(jsonDetails, index, null);
    }

    /**
     * Method to get from {@link JSONArray} a Number value
     *
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index: index of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * */
    public static Number getNumber(JSONArray jsonDetails, int index, Number defValue){
        try {
            return jsonDetails.getNumber(index);
        }catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONArray} an object value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static Object get(JSONArray jsonDetails, int index){
        return get(jsonDetails, index, null);
    }

    /** Method to get from {@link JSONArray} an object value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index: index of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * */
    public static Object get(JSONArray jsonDetails, int index, Object defValue){
        try {
            return jsonDetails.get(index);
        }catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONArray} a boolean value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static boolean getBoolean(JSONArray jsonDetails, int index) {
        return getBoolean(jsonDetails, index, false);
    }

    /**
     * Method to get from {@link JSONArray} a boolean value
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of boolean value to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return {@code defValue}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    public static boolean getBoolean(JSONArray jsonDetails, int index, boolean defValue){
        try {
            return jsonDetails.getBoolean(index);
        }catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from {@link JSONArray}  a list of values
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static JSONArray getJSONArray(JSONArray jsonDetails, int index) {
        return getJSONArray(jsonDetails, index, null);
    }

    /**
     * Method to get from {@link JSONArray}  a list of values
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of JSONArray to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     */
    public static JSONArray getJSONArray(JSONArray jsonDetails, int index, JSONArray defValue) {
        try {
            return jsonDetails.getJSONArray(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get a list from the {@link #jsonArraySource}<br>
     * No-any params required
     *
     * @return list value as {@link List} of {@link T}
     * @implNote The {@link #jsonArraySource} must have the explicit type to create the correct list type, for example:
     * <ul>
     *     <li>
     *         With the list formatted as:
     *         <pre>
     *             {@code
     *                  [
     *                    true,
     *                    true,
     *                    false,
     *                    true
     *                 ]
     *             }
     *         </pre>
     *         the method will return a {@link List} of {@link Boolean}
     *     </li>
     *     <li>
     *         With the list formatted as:
     *         <pre>
     *             {@code
     *                  [
     *                    "true",
     *                    "true",
     *                    "false",
     *                    "true"
     *                 ]
     *             }
     *         </pre>
     *         the method will return a {@link List} of {@link String}
     *     </li>
     * </ul>
     */
    @Wrapper
    public <T> List<T> toList() {
        return toList(jsonArraySource);
    }

    /**
     * Method to get a list from a {@link JSONArray}
     *
     * @param jsonArray: the {@link JSONArray} source from fetch the list
     * @return list value as {@link List} of {@link T}
     * @implNote The {@link #jsonArraySource} must have the explicit type to create the correct list type, for example:
     * <ul>
     *     <li>
     *         With the list formatted as:
     *         <pre>
     *             {@code
     *                  [
     *                    true,
     *                    true,
     *                    false,
     *                    true
     *                 ]
     *             }
     *         </pre>
     *         the method will return a {@link List} of {@link Boolean}
     *     </li>
     *     <li>
     *         With the list formatted as:
     *         <pre>
     *             {@code
     *                  [
     *                    "true",
     *                    "true",
     *                    "false",
     *                    "true"
     *                 ]
     *             }
     *         </pre>
     *         the method will return a {@link List} of {@link String}
     *     </li>
     * </ul>
     */
    public static <T> List<T> toList(JSONArray jsonArray) {
        return (List<T>) jsonArray.toList();
    }

    /**
     * Method to get from {@link JSONObject} a jsonObject
     *
     * @param key: key of {@link JSONObject} to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    @Wrapper
    public JSONObject getJSONObject(String key) {
        return getJSONObject(key, null);
    }

    /**
     * Method to get from {@link JSONObject} a jsonObject
     *
     * @param key:      key of {@link JSONObject} to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * @throws ClassCastException: when this exception has been trowed will be returned null as default value
     */
    public JSONObject getJSONObject(String key, JSONObject defValue) {
        Object value = autoSearch(jsonObjectSource, key, defValue);
        if (value instanceof JSONObject)
            return (JSONObject) value;

        return defValue;
    }

    /**
     * Method to get from {@link JSONArray} a jsonObject
     *
     * @param index: index of {@link JSONObject} to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     */
    @Wrapper
    public JSONObject getJSONObject(int index) {
        return getJSONObject(index, null);
    }

    /**
     * Method to get from {@link JSONArray} a jsonObject
     *
     * @param index:    index of {@link JSONObject} to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     */
    public JSONObject getJSONObject(int index, JSONObject defValue) {
        try {
            return jsonArraySource.getJSONObject(index);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of {@link JsonHelper} to get from json
     * @return value as {@link T}, if it is not exist will return null
     * @implNote this static method is useful when you have to fetch a single value from JSON avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * JSON source is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static <T> T getJsonHelper(JSONObject jsonDetails, String key) {
        return getJsonHelper(jsonDetails, key, null);
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param key:         key of {@link JsonHelper} to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as {@link JsonHelper}, if it is not exist will return {@code defValue}
     * @implNote this static method is useful when you have to fetch a single value from JSON avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * JSON source is recommended instantiate {@link JsonHelper} class first.
     */
    public static <T> T getJsonHelper(JSONObject jsonDetails, String key, T defValue) {
        Object value = get(jsonDetails, key);
        if (value != null)
            return (T) new JsonHelper(value);
        else
            return defValue;
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of {@link JsonHelper} to get from json
     * @return value as {@link T}, if it is not exist will return null
     * @implNote this static method is useful when you have to fetch a single value from JSON avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * JSON source is recommended instantiate {@link JsonHelper} class first.
     */
    @Wrapper
    public static <T> T getJsonHelper(JSONArray jsonDetails, int index) {
        return getJsonHelper(jsonDetails, index, null);
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param jsonDetails: {@code "JSON"} from fetch data
     * @param index:       index of {@link JsonHelper} to get from json
     * @param defValue:    default value to return if primary value not exists
     * @return value as {@link JsonHelper}, if it is not exist will return {@code defValue}
     * @implNote this static method is useful when you have to fetch a single value from JSON avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * JSON source is recommended instantiate {@link JsonHelper} class first.
     */
    public static <T> T getJsonHelper(JSONArray jsonDetails, int index, T defValue) {
        Object value = get(jsonDetails, index);
        if (value != null)
            return (T) new JsonHelper(value);
        else
            return defValue;
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param key: key of {@link JsonHelper} to get from json
     * @return value as {@link JsonHelper}, if it is not exist will return null
     */
    @Wrapper
    public JsonHelper getJsonHelper(String key) {
        return getJsonHelper(key, null);
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param key:      key of {@link JsonHelper} to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JsonHelper} if exists, if it is not exist will return {@code defValue} as {@link T}
     */
    public <T> T getJsonHelper(String key, T defValue) {
        Object value = get(key);
        if (value != null)
            return (T) new JsonHelper(value);
        else
            return defValue;
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param index: index of {@link JsonHelper} to get from json
     * @return value as {@link JsonHelper}, if it is not exist will return null
     */
    @Wrapper
    public JsonHelper getJsonHelper(int index) {
        return getJsonHelper(index, null);
    }

    /**
     * Method to get from JSON a jsonHelper
     *
     * @param index:    index of {@link JsonHelper} to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JsonHelper} if exists, if it is not exist will return {@code defValue} as {@link T}
     */
    public <T> T getJsonHelper(int index, T defValue) {
        Object value = get(index);
        if (value != null)
            return (T) new JsonHelper(value);
        else
            return defValue;
    }

    /**
     * Method to get from {@link JSONArray} a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONArray} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     */
    @Wrapper
    public <T> ArrayList<T> fetchList(String searchKey) {
        return fetchList(searchKey, null);
    }

    /**
     * Method to get from {@link JSONArray} a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONArray} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     */
    @Wrapper
    public <T, V extends ArrayList<?>> ArrayList<T> fetchList(String searchKey, V defValue) {
        return fetchList(jsonObjectSource, searchKey, defValue);
    }

    /**
     * Method to get from {@link JSONArray} a list of values automatically
     *
     * @param source:    {@link JSONObject} source to search the {@link JSONArray} from fetch the list
     * @param searchKey: key for value to fetch
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     */
    @Wrapper
    public static <T> ArrayList<T> fetchList(JSONObject source, String searchKey) {
        return fetchList(source, searchKey, null);
    }

    /**
     * Method to get from {@link JSONArray} a list of values automatically
     *
     * @param source:    {@link JSONObject} source to search the {@link JSONArray} from fetch the list
     * @param searchKey: key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
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
     * Method to get from {@link JSONObject} a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     */
    @Wrapper
    public <T> ArrayList<T> fetchVList(String searchKey) {
        return fetchVList(jsonObjectSource, searchKey, null, null);
    }

    /**
     * Method to get from {@link JSONObject} a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     */
    @Wrapper
    public <T, V extends ArrayList<?>> ArrayList<T> fetchVList(String searchKey, V defValue) {
        return fetchVList(jsonObjectSource, searchKey, null, defValue);
    }

    /**
     * Method to get from {@link JSONObject} a list of values automatically
     *
     * @param json:      {@link JSONObject} from fetch list
     * @param searchKey: key for value to fetch
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     */
    @Wrapper
    public static <T> ArrayList<T> fetchVList(JSONObject json, String searchKey) {
        return fetchVList(json, searchKey, null, null);
    }

    /**
     * Method to get from {@link JSONObject} a list of values automatically
     *
     * @param json:      {@link JSONObject} from fetch list
     * @param searchKey: key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     */
    @Wrapper
    public static <T, V extends ArrayList<?>> ArrayList<T> fetchVList(JSONObject json, String searchKey, V defValue) {
        return fetchVList(json, searchKey, null, defValue);
    }

    /**
     * Method to get from {@link JSONObject} a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @param listKey:   list key for value to fetch
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     * @implNote This method is useful when the value to fetch appears in multiple list
     */
    @Wrapper
    public <T> ArrayList<T> fetchVList(String searchKey, String listKey) {
        return fetchVList(searchKey, listKey, null);
    }

    /**
     * Method to get from {@link JSONObject} a list of values automatically
     *
     * @param searchKey: key for value to fetch
     * @param listKey:   list key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     * @implNote This method is useful when the value to fetch appears in multiple list
     */
    @Wrapper
    public <T, V extends ArrayList<?>> ArrayList<T> fetchVList(String searchKey, String listKey, V defValue) {
        return fetchVList(jsonObjectSource, searchKey, listKey, defValue);
    }

    /**
     * Method to get from {@link JSONObject} a list of values automatically
     *
     * @param json:      {@link JSONObject} from fetch list
     * @param searchKey: key for value to fetch
     * @param listKey:   list key for value to fetch
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     * @implNote This method is useful when the value to fetch appears in multiple list
     */
    @Wrapper
    public static <T> ArrayList<T> fetchVList(JSONObject json, String searchKey, String listKey) {
        return fetchVList(json, searchKey, listKey, null);
    }

    /**
     * Method to get from {@link JSONObject} a list of values automatically
     *
     * @param json:      {@link JSONObject} from fetch list
     * @param searchKey: key for value to fetch
     * @param listKey:   list key for value to fetch
     * @param defValue:  default value to return if not found what searched
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     * @implNote This method is useful when the value to fetch appears in multiple list
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
     * Method to get from {@link JSONObject} a generic value
     *
     * @param json:      {@link JSONObject} from fetch data
     * @param searchKey: key for value to fetch
     * @return value as {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
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
     * Method to check if a {@code "JSON"} source contains a specific key
     *
     * @param json:      {@code "JSON"} source
     * @param searchKey: key for value to fetch
     * @return whether the source constants the key specified
     */
    private static boolean containsKey(Object json, String searchKey) {
        return json.toString().contains("\"" + searchKey + "\":");
    }

    /**
     * Method to get from {@link JSONObject} a generic value
     *
     * @param json:      {@link JSONObject} from fetch data
     * @param searchKey: key for value to fetch
     * @param defValue:  default value to return if primary value not exists
     * @return value as {@link T}, if it is not exist will return {@code defValue}
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     */
    private static <T> T autoSearch(JSONObject json, String searchKey, T defValue) {
        T search = autoSearch(json, searchKey);
        if (search == null)
            return defValue;
        return search;
    }

    /**
     * Method to assemble a list from {@link JSONArray} a generic value
     *
     * @param list : {@link JSONArray} from fetch data
     * @return value as {@link ArrayList} of {@link T}, if it is not exist will return null
     * @apiNote this method does not need specific path of the {@link JSONArray} to fetch value, but it will in automatic
     * reach the value requested directly from the entire {@code "JSON"} file without path inserted by hand
     */
    private static <T> T assembleList(JSONArray list) {
        ArrayList<T> values = new ArrayList<>();
        if (list != null)
            for (int j = 0; j < list.length(); j++)
                values.add((T) list.get(j));
        return (T) values;
    }

    /**
     * Method to get {@link #jsonObjectSource} instance <br>
     * No-any params required
     *
     * @return {@link #jsonObjectSource} instance as {@link JSONObject}
     */
    public JSONObject getJSONObjectSource() {
        return jsonObjectSource;
    }

    /**
     * Method to set {@link #jsonObjectSource} instance
     *
     * @param jsonObjectSource : new {@code "JSON"} source for {@link #jsonObjectSource}
     * @throws IllegalArgumentException when {@code "jsonObjectSource"} inserted is not a valid {@code "JSON"} source
     * @apiNote if it will be thrown an {@link IllegalArgumentException} use directly the {@link #setJSONArraySource(String)}
     * method or check validity of the {@code "JSON"} source inserted
     */
    public <T> void setJSONObjectSource(T jsonObjectSource) {
        try {
            this.jsonObjectSource = new JSONObject(jsonObjectSource);
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method to set {@link #jsonObjectSource} instance
     *
     * @param jsonObjectSource : new {@code "JSON"} source for {@link #jsonObjectSource}
     * @throws IllegalArgumentException when {@code "jsonObjectSource"} inserted is not a valid {@code "JSON"} source
     */
    public void setJSONObjectSource(String jsonObjectSource) {
        try {
            this.jsonObjectSource = new JSONObject(jsonObjectSource);
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method to set {@link #jsonObjectSource} instance
     *
     * @param jsonObjectSource : new {@code "JSON"} source for {@link #jsonObjectSource}
     * @throws IllegalArgumentException when {@code "jsonObjectSource"} inserted is not a valid {@code "JSON"} source
     */
    public void setJSONObjectSource(JSONObject jsonObjectSource) {
        try {
            this.jsonObjectSource = jsonObjectSource;
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method to get {@link #jsonArraySource} instance <br>
     * No-any params required
     *
     * @return {@link #jsonArraySource} instance as {@link JSONArray}
     */
    public JSONArray getJSONArraySource() {
        return jsonArraySource;
    }

    /**
     * Method to set {@link #jsonArraySource} instance
     *
     * @param jsonArraySource: new {@code "JSON"} source for {@link #jsonArraySource}
     * @throws IllegalArgumentException when {@code "jsonArraySource"} inserted is not a valid {@code "JSON"} source
     * @apiNote if it will be thrown an {@link IllegalArgumentException} use directly the {@link #setJSONArraySource(String)}
     * method or check validity of the {@code "JSON"} source inserted
     */
    public <T> void setJSONArraySource(T jsonArraySource) {
        try {
            this.jsonArraySource = new JSONArray(jsonArraySource.toString());
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method to set {@link #jsonArraySource} instance
     *
     * @param jsonArraySource: new {@code "JSON"} source for {@link #jsonArraySource}
     * @throws IllegalArgumentException when {@code "jsonArraySource"} inserted is not a valid {@code "JSON"} source
     */
    public void setJSONArraySource(String jsonArraySource) {
        try {
            this.jsonArraySource = new JSONArray(jsonArraySource);
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Method to set {@link #jsonArraySource} instance
     *
     * @param jsonArraySource: new {@code "JSON"} source for {@link #jsonArraySource}
     * @throws IllegalArgumentException when {@code "jsonArraySource"} inserted is not a valid {@code "JSON"} source
     */
    public void setJSONArraySource(JSONArray jsonArraySource) {
        try {
            this.jsonArraySource = jsonArraySource;
        } catch (JSONException e) {
            throw new IllegalArgumentException("The JSON source inserted is not a valid source");
        }
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}