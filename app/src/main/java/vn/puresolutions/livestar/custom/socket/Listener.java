package vn.puresolutions.livestar.custom.socket;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.socket.emitter.Emitter;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/23/2015
 */
public abstract class Listener<T> implements Emitter.Listener {
    private Gson gson;
    private Class<T> type;
    private Handler handler;

    public Listener(Class<T> type) {
        gson = new GsonBuilder().create();
        handler = new Handler();
        this.type = type;
    }

    @Override
    public void call(Object... args) {
        if (args != null && args.length > 0) {
            try {
                String json = args[0].toString();
                final T data = gson.fromJson(json, type);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        call(data);
                    }
                });
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Parse json failed: " + e.getMessage());
            }
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    call((T) null);
                }
            });
        }
    }

    public abstract void call(T data);
}
