package vn.puresolutions.livestar.corev3.data;

import android.content.Context;
import android.util.Log;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/29/2015
 */
public class CacheHelper<T> {

    private Context context;

    public CacheHelper(Context context) {
        this.context = context;
    }

    public void save(String name, T data) {
        try {
            FileDataProvider<T> dataProvider = new FileDataProvider<T>(context, name);
            dataProvider.save(data);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Save cache failed: " + e.getMessage());
        }
    }

    public T load(String name, Class<?> clzz) {
        try {
            FileDataProvider<T> dataProvider = new FileDataProvider<T>(context, name);
            return dataProvider.load(clzz);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Load cache failed: " + e.getMessage());
            return  null;
        }
    }

    public static void delete(Context context, String name) {
        context.deleteFile(name);
    }
}
