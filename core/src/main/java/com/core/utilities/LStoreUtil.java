package com.core.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.google.gson.Gson;
import com.interfaces.GGSettingCallback;
import com.model.App;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LStoreUtil {
    static String TAG = LStoreUtil.class.getSimpleName();
    //static String folderName = "Loitp";
    //static String folderPath;

    public static String FOLDER_TRANSLATE = ".Loitp";
    public static String FILE_TRANSLATE_FAV_SENTENCE = "Loitp.txt";

    private static final String EXTENSION = ".db";
    public static final String FOLDER_TRUYENTRANHTUAN = "zloitpbestcomic";
    public static final String FILE_NAME_MAIN_COMICS_LIST_HTML_CODE = "filenamemaincomicslisthtmlcode" + EXTENSION;
    public static final String FILE_NAME_MAIN_COMICS_LIST = "filenamemaincomicslist" + EXTENSION;
    public static final String FILE_NAME_MAIN_COMICS_LIST_FAVOURITE = "filenamemaincomicslistfavourite" + EXTENSION;
    public static final String FILE_NAME_TRUYENTRANHTUAN_DOWNLOADED_COMIC = "filenamedownloadedcomic" + EXTENSION;

    public static String getFileNameComic(String url) {
        url = url.replace("/", "");
        url = url.replace(".", "");
        url = url.replace(":", "");
        url = url.replace("-", "");
        return url + EXTENSION;
    }

    public static String createFileImage(int i) {
        return "p" + i + EXTENSION;
    }

    public interface CallbackReadFile {
        void onFinish(String result);
    }

    public interface CallbackWriteFile {
        void onFinish(boolean isSuccess);
    }

    public static String getFolderPath(final Context context, final String mfolderName) {
        String folderPath = "";
        if (isSdPresent()) {
            try {
                File sdPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mfolderName);
                if (!sdPath.exists()) {
                    sdPath.mkdirs();
                    folderPath = sdPath.getAbsolutePath();
                } else if (sdPath.exists()) {
                    folderPath = sdPath.getAbsolutePath();
                }
            } catch (Exception e) {
                LLog.d("TAG", "if getFolderPath: " + e.toString());
            }
            folderPath = Environment.getExternalStorageDirectory().getPath() + "/" + mfolderName + "/";
        } else {
            try {
                File cacheDir = new File(context.getCacheDir(), mfolderName + "/");
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                    folderPath = cacheDir.getAbsolutePath();
                } else if (cacheDir.exists()) {
                    folderPath = cacheDir.getAbsolutePath();
                }
            } catch (Exception e) {
                Log.e(TAG, "else getFolderPath: " + e.toString());
            }
        }
        return folderPath;
    }

    public static String getFolderPath(Context context) {
        return getFolderPath(context, "Loitp");
    }

    public static boolean isSdPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static int checkSDCardFreeSize() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        long megAvailable = bytesAvailable / (1024 * 1024);
        return (int) megAvailable;
    }

    /*
    save tring json to sdcard
    ex: writeToFile("module.json", strJson);
     */
    public static boolean writeToFile(Activity activity, String folder, String fileName, String body) {
        boolean isCompelete = true;
        FileOutputStream fos = null;
        try {
            String path = LStoreUtil.getFolderPath(activity);
            if (folder != null) {
                //path = path + "/" + folder;
                //path = path + folder;
                path = path + folder + "/";
            }
            LLog.d(TAG, "path: " + path);
            final File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    LLog.d(TAG, "could not create the directories");
                }
            }
            final File myFile = new File(dir, fileName);
            if (!myFile.exists()) {
                boolean isSuccess = myFile.createNewFile();
                LLog.d(TAG, "isSuccess: " + isSuccess);
            }
            fos = new FileOutputStream(myFile);
            fos.write(body.getBytes());
            fos.close();
        } catch (IOException e) {
            LLog.d(TAG, e.toString());
            isCompelete = false;
        }
        return isCompelete;
    }

    public static void writeToFile(final Activity activity, final String folder, final String fileName, final String body, final CallbackWriteFile callbackWriteFile) {
        new AsyncTask<Void, Void, Void>() {
            boolean isSuccess;

            @Override
            protected Void doInBackground(Void... params) {
                isSuccess = writeToFile(activity, folder, fileName, body);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (callbackWriteFile != null) {
                    callbackWriteFile.onFinish(isSuccess);
                }
            }
        }.execute();
    }

    /*
     * read text file from folder
     */
    public static String readTxtFromFolder(Activity activity, String folderName, String fileName) {
        String path = LStoreUtil.getFolderPath(activity) + (folderName == null ? "/" : (folderName + "/")) + fileName;
        LLog.d(TAG, "path: " + path);
        File txtFile = new File(path);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(txtFile));
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line + '\n');
            }
            reader.close();
        } catch (IOException e) {
            LLog.d(TAG, "readTxtFromFolder===" + e.toString());
        }
        return text.toString();
    }

    public static void readTxtFromFolder(final Activity activity, final String folderName, final String fileName, final CallbackReadFile callbackReadFile) {
        new AsyncTask<Void, Void, Void>() {
            String result = "";

            @Override
            protected Void doInBackground(Void... params) {
                result = readTxtFromFolder(activity, folderName, fileName);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callbackReadFile.onFinish(result);
            }
        }.execute();
    }

    public interface EventReadFromFolder {
        void onSuccess(String data);

        void onError();
    }

    /*
     * read text file from folder in background
     */
    public static void readTxtFromFolder(final Activity activity, final String folderName, final String fileName, final EventReadFromFolder eventReadFromFolder) {
        new AsyncTask<Void, Void, Void>() {
            private StringBuilder text = null;
            private boolean runTaskSuccess = true;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                String path = LStoreUtil.getFolderPath(activity) + (folderName == null ? "/" :
                        (folderName + "/")) + fileName;
                LLog.d(TAG, "path: " + path);
                File txtFile = new File(path);
                text = new StringBuilder();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(txtFile));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        text.append(line + '\n');
                    }
                    reader.close();
                } catch (IOException e) {
                    runTaskSuccess = false;
                    LLog.d(TAG, "readTxtFromFolder===" + e.toString());

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (runTaskSuccess) {
                    eventReadFromFolder.onSuccess(text.toString());
                } else {
                    eventReadFromFolder.onError();
                }
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    /*
     * read text file in raw folder
     */
    public static String readTxtFromRawFolder(Context context, int nameOfRawFile) {
        InputStream inputStream = context.getResources().openRawResource(nameOfRawFile);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (Exception e) {
            LLog.d(TAG, e.toString());
        }
        return byteArrayOutputStream.toString();
    }

    public interface CallbackReadFromRaw {
        void onFinish(String result);
    }

    public static void readTxtFromRawFolder(final Context context, final int nameOfRawFile, final CallbackReadFromRaw callbackReadFromRaw) {
        new AsyncTask<Void, Void, Void>() {
            String result;

            @Override
            protected Void doInBackground(Void... params) {
                result = readTxtFromRawFolder(context, nameOfRawFile);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callbackReadFromRaw.onFinish(result);
            }
        }.execute();
    }

    /*@param link: the url of the website
     * return true if save success
     * return false if save failed
     * */
    /*public static boolean saveHTMLCodeFromURLToSDCard(Context context, String link, String
    folderName, String fileName) {
        return saveHTMLCodeFromURLToSDCard((Activity) context, link, folderName, fileName);
    }*/

    public static boolean saveHTMLCodeFromURLToSDCard(Context context, String link, String folderName, String fileName) {
        boolean state = false;
        InputStream is = null;
        try {
            URL url = new URL(link);
            is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            br.close();
            is.close();
            //LLog.d(TAG, "saveHTMLCodeFromURLToSDCard success: " + stringBuilder.toString());
            writeToFile((Activity) context, folderName, fileName, stringBuilder.toString());
            state = true;
        } catch (Exception e) {
            //LLog.d(TAG, "saveHTMLCodeFromURLToSDCard failed: " + e.toString());
        }
        return state;
    }

    /*
      save bitmap to sdcard
      @param bitmap
      @param subDirName like Quote
       */
    /*public void saveBitmapToSDCard(Bitmap bitmap, String subDirName) {
        //String root = Environment.getExternalStorageDirectory().toString();
        //File myDir = new File(root + "/saved_images");
        String root = LStoreUtil.getFolderPath(mContext);
        File myDir = new File(root + "/" + subDirName);
        if (!myDir.exists())
            myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            //LToast.dialog(mContext, R.string.down_ok, 0);
        } catch (Exception e) {
            LLog.dialog(TAG, "saveBitmapToSDCard: " + e.toString());
        }
    }*/

    /*
     * get random quote
     */
    /*public String getRandomeQuote() {
        InputStream in;
        // quote
        String[] arr_Str = null;
        String str = "";
        try {
            in = mContext.getAssets().open("quote.txt");
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            String chuoi = new String(buffer);
            arr_Str = chuoi.split("###");

            // for (int i = 0; i < arr_Str.length; i++) {
            // // 1 ki tu dau tien cua moi chap
            // String char1;
            // String[] arrChar = arr_Str[i].split(" ");
            // try {
            // char1 = arrChar[1];
            // } catch (Exception e) {
            // char1 = arr_Str[i].substring(0, 1);
            // }
            // }

            // Random r = new Random();
            // int ran = r.nextInt(arr_Str.length);
            int ran = getRandomNumber(arr_Str.length);
            str = arr_Str[ran];

        } catch (Exception e) {
            // LLog.dialog("bug", e.toString());
        }
        return str;
    }*/

    /*
     * get random quote
     */
    public static String readTxtFromAsset(Context context, String assetFile) {
        InputStream in;
        String str = "";
        try {
            in = context.getAssets().open(assetFile);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            str = new String(buffer);

        } catch (Exception e) {
            LLog.e(TAG, "readTxtFromAsset: " + e.toString());
        }
        return str;
    }

    /*
     * get random number
     */
    public static int getRandomNumber(int length) {
        int x = 0;
        Random r = new Random();
        x = r.nextInt(length);
        return x;
    }

    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    //https://stackoverflow.com/questions/30703652/how-to-generate-light-and-pastel-colors-randomly-in-android
    public static int getRandomColorLight() {
        final Random mRandom = new Random(System.currentTimeMillis());
        // This is the base color which will be mixed with the generated one
        final int baseColor = Color.WHITE;

        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);

        final int red = (baseRed + mRandom.nextInt(256)) / 2;
        final int green = (baseGreen + mRandom.nextInt(256)) / 2;
        final int blue = (baseBlue + mRandom.nextInt(256)) / 2;

        return Color.rgb(red, green, blue);
    }

    public static String getPathOfFileNameMainComicsListHTMLCode(Context context) {
        return getFolderPath(context) + "/" + LStoreUtil.FOLDER_TRUYENTRANHTUAN + "/" + LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_HTML_CODE;
    }

    public static int[] getColors() {
        return new int[]{
                Color.parseColor("#1BFFFF"),
                Color.parseColor("#2E3192"),
                Color.parseColor("#ED1E79"),
                Color.parseColor("#009E00"),
                Color.parseColor("#FBB03B"),
                Color.parseColor("#D4145A"),
                Color.parseColor("#3AA17E"),
                Color.parseColor("#00537E"),
        };
    }

    public static String[] getTexts() {
        return new String[]{
                "Relax, its only ONES and ZEROS !",
                "Hardware: The parts of a computer system that can be kicked.",
                "Computer dating is fine, if you're a computer.",
                "Better to be a geek than an idiot.",
                "If you don't want to be replaced by a computer, don't act like one.",
                "I'm not anti-social; I'm just not user friendly",
                "Those who can't write programs, write help files.",
                "The more I C, the less I see.  "
        };
    }

    public static File getFileFromAssets(Context context, String fileName) {
        if (context == null) {
            return null;
        }
        File file = new File(context.getCacheDir() + "/" + fileName);
        if (!file.exists()) try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "getFileFromAssets " + e.toString());
            return null;
        }
        return file;
    }

    public static List<File> getListEpubFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    inFiles.addAll(getListEpubFiles(file));
                } else {
                    if (file.getName().endsWith(".epub")) {
                        inFiles.add(file);
                    }
                }
            }
        }
        return inFiles;
    }

    public static void getSettingFromGGDrive(final Context context, final String linkGGDriveSetting, final GGSettingCallback ggSettingCallback) {
        if (context == null || linkGGDriveSetting == null || linkGGDriveSetting.isEmpty()) {
            return;
        }
        LLog.d(TAG, "getSettingFromGGDrive");
        Request request = new Request.Builder().url(linkGGDriveSetting).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LLog.d(TAG, "getSettingFromGGDrive onFailure " + e.toString());
                if (ggSettingCallback != null) {
                    ggSettingCallback.onGGFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody == null) {
                        return;
                    }
                    String json = responseBody.string();
                    LLog.d(TAG, "getSettingFromGGDrive onResponse isSuccessful " + json);
                    if (json == null) {
                        return;
                    }
                    App app = new Gson().fromJson(json, App.class);
                    if (app == null) {
                        if (ggSettingCallback != null) {
                            ggSettingCallback.onGGResponse(null, false);
                        }
                    } else {
                        String localMsg = LPrefUtil.INSTANCE.getGGAppMsg(context);
                        String serverMsg = app.getConfig().getMsg();
                        LPrefUtil.INSTANCE.setGGAppMsg(context, serverMsg);
                        boolean isNeedToShowMsg;
                        if (serverMsg == null || serverMsg.isEmpty()) {
                            isNeedToShowMsg = false;
                        } else {
                            isNeedToShowMsg = !localMsg.trim().equalsIgnoreCase(serverMsg);
                        }
                        if (ggSettingCallback != null) {
                            ggSettingCallback.onGGResponse(app, isNeedToShowMsg);
                        }
                    }

                } else {
                    LLog.d(TAG, "getSettingFromGGDriveonResponse !isSuccessful: " + response.toString());
                    if (ggSettingCallback != null) {
                        ggSettingCallback.onGGResponse(null, false);
                    }
                }
            }
        });
    }
}