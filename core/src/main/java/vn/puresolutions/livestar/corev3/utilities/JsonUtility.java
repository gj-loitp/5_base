package vn.puresolutions.livestar.corev3.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Phu Tran on 11/9/2015.
 */
public class JsonUtility {

    // TODO: Try new way
    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static JSONObject mapToJson(Map<?, ?> data) {
        JSONObject object = new JSONObject();
        for (Map.Entry<?, ?> entry : data.entrySet()) {
            /*
             * Deviate from the original by checking that keys are non-null and
             * of the proper type. (We still defer validating the values).
             */
            String key = (String) entry.getKey();
            if (key == null) {
                throw new NullPointerException("key == null");
            }
            try {
                object.put(key, wrap(entry.getValue()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public static JSONArray collectionToJson(Collection data) {
        JSONArray jsonArray = new JSONArray();
        if (data != null) {
            for (Object aData : data) {
                jsonArray.put(wrap(aData));
            }
        }
        return jsonArray;
    }

    public static JSONArray arrayToJson(Object data) throws JSONException {
        if (!data.getClass().isArray()) {
            throw new JSONException("Not a primitive data: " + data.getClass());
        }
        final int length = Array.getLength(data);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < length; ++i) {
            jsonArray.put(wrap(Array.get(data, i)));
        }

        return jsonArray;
    }

    private static Object wrap(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof JSONArray || object instanceof JSONObject) {
            return object;
        }
        try {
            if (object instanceof Collection) {
                return collectionToJson((Collection) object);
            } else if (object.getClass().isArray()) {
                return arrayToJson(object);
            }
            if (object instanceof Map) {
                return mapToJson((Map) object);
            }
            if (object instanceof Boolean ||
                    object instanceof Byte ||
                    object instanceof Character ||
                    object instanceof Double ||
                    object instanceof Float ||
                    object instanceof Integer ||
                    object instanceof Long ||
                    object instanceof Short ||
                    object instanceof String) {
                return object;
            }
            if (object.getClass().getPackage().getName().startsWith("java.")) {
                return object.toString();
            }
        } catch (Exception ignored) {

        }
        return null;
    }
}
