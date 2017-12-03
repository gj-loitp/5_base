package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

public class LStoreUtil {
    static String TAG = LStoreUtil.class.getSimpleName();
    static String folderName = "Loitp";
    static String folderPath;

    public static String FOLDER_TRANSLATE = ".Loitp";
    public static String FILE_TRANSLATE_FAV_SENTENCE = "Loitp.txt";

    private static final String EXTENSION = ".db";
    public static final String FOLDER_TRUYENTRANHTUAN = "foldertruyentranhtuan";
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
        public void onFinish(String result);
    }

    public interface CallbackWriteFile {
        public void onFinish(boolean isSuccess);
    }

    public static String getFolderPath(Context context) {
        if (isSdPresent()) {
            try {
                File sdPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folderName);
                if (!sdPath.exists()) {
                    sdPath.mkdirs();
                    folderPath = sdPath.getAbsolutePath();
                } else if (sdPath.exists()) {
                    folderPath = sdPath.getAbsolutePath();
                }
            } catch (Exception e) {
                LLog.d("TAG", "if getFolderPath: " + e.toString());
            }
            folderPath = Environment.getExternalStorageDirectory().getPath() + "/" + folderName + "/";
        } else {
            try {
                File cacheDir = new File(context.getCacheDir(), folderName + "/");
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                    folderPath = cacheDir.getAbsolutePath();
                } else if (cacheDir.exists()) {
                    folderPath = cacheDir.getAbsolutePath();
                }
            } catch (Exception e) {
                LLog.d("TAG", "else getFolderPath: " + e.toString());
            }
        }
        return folderPath;
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
        public void onSuccess(String data);

        public void onError();
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
        public void onFinish(String result);
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
    /*public static String getRandomeQuote(Context context) {
        InputStream in;
        String[] arr_Str = null;
        String str = "";
        try {
            in = context.getAssets().open("raw/quote.txt");
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            String chuoi = new String(buffer);
            arr_Str = chuoi.split("###");
            int ran = getRandomNumber(arr_Str.length);
            str = arr_Str[ran];

        } catch (Exception e) {
            LLog.d(TAG, "getRandomeQuote: " + e.toString());
        }
        return str;
    }*/

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

    public static String getPathOfFileNameMainComicsListHTMLCode(Context context) {
        return getFolderPath(context) + "/" + LStoreUtil.FOLDER_TRUYENTRANHTUAN + "/" + LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_HTML_CODE;
    }
}