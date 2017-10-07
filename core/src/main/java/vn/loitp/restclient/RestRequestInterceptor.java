package vn.loitp.restclient;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RestRequestInterceptor implements Interceptor {
    private Hashtable<String, String> headers;

    public RestRequestInterceptor() {
        headers = new Hashtable<>();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void removeHeader(String key) {
        headers.remove(key);
    }

    public boolean hasHeader(String key) {
        return headers.containsKey(key);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (headers != null && headers.size() > 0) {
            Enumeration<String> keys = headers.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = headers.get(key);
                builder.header(key, value);
            }
        }
        return chain.proceed(builder.build());
    }
}
