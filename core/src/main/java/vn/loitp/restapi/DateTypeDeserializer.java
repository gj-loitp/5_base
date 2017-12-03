package vn.loitp.restapi;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class DateTypeDeserializer implements JsonDeserializer<Date> {

    private static final String TAG = DateTypeDeserializer.class.getName();
    private static final String[] DATE_FORMATS = new String[]{
            "dd-MM-yyyy hh:mm",
            "dd/MM/yyyy",
            "yyyy-MM-dd"
    };

    @Override
    public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) throws JsonParseException {
        for (String format : DATE_FORMATS) {
            try {
                return new SimpleDateFormat(format, Locale.getDefault()).parse(jsonElement.getAsString());
            } catch (ParseException e) {
                Log.v(TAG, "Error when deserialize date");
            }
        }
        throw new JsonParseException("Cant parse date: \"" + jsonElement.getAsString()
                + "\". Supported formats: \n" + Arrays.toString(DATE_FORMATS));
    }
}