package vn.puresolutions.livestar.core.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import vn.puresolutions.livestar.core.api.parser.DateTypeDeserializer;
/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/29/2015
 */
public class FileDataProvider<T> implements IDataProvider<T> {

    private Context context;
    private Gson gson;
    protected String filename;

    public FileDataProvider(Context context, String filename) {
        this.context = context;
        this.filename = filename;
        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeDeserializer())
                .create();
    }

    @Override
    public void save(T data) throws Exception {
        String jsonStr = gson.toJson(data);
        writeFile(jsonStr);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T load(Class clazz) throws Exception {
        String json = readFile();
        return (T) gson.fromJson(json, clazz);
    }

    private void writeFile(String data) throws IOException {
        FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        byte[] utf8Bytes = data.getBytes("UTF8");
        outputStream.write(utf8Bytes, 0, utf8Bytes.length);
        outputStream.close();
    }

    private String readFile() throws IOException {
        StringBuilder builder = new StringBuilder();

        FileInputStream inputStream = context.openFileInput(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        String line = reader.readLine();
        while (line != null) {
            builder.append(line);
            line = reader.readLine();
        }

        return builder.toString();
    }
}