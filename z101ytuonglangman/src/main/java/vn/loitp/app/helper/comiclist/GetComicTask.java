package vn.loitp.app.helper.comiclist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.home.allmanga.DatabaseHandler;
import vn.loitp.app.model.comic.Comic;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;

/**
 * Created by www.muathu@gmail.com on 11/2/2017.
 */

public class GetComicTask extends AsyncTask<Void, Integer, Void> {
    private final String TAG = getClass().getSimpleName();

    private int numberOfParseDataTryAgain = 0;
    private int numberOfDoTaskTryAgain = 0;
    private final int MAX_TRY_AGAIN = 4;

    private boolean getComicSuccess = false;

    private String link;

    private List<Comic> comicList = new ArrayList<>();

    private Activity activity;

    private DatabaseHandler db;
    private ProgressDialog progressDialog;

    public interface Callback {
        public void onSuccess(List<Comic> comicList);

        public void onError();
    }

    private Callback callback;

    public GetComicTask(Activity activity, DatabaseHandler db, String link, Callback callback) {
        this.activity = activity;
        this.db = db;
        this.link = link;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        //LLog.d(TAG, "GetComicTask onPreExecute");
        if (db.getComicCount() == 0) {
            progressDialog = LDialogUtil.showProgressDialog(activity, 100, activity.getString(R.string.app_name), activity.getString(R.string.loading), false, ProgressDialog.STYLE_HORIZONTAL, null, null);
        } else {
            progressDialog = LDialogUtil.showProgressDialog(activity, 100, activity.getString(R.string.app_name), activity.getString(R.string.loading), false, ProgressDialog.STYLE_SPINNER, null, null);
        }
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //LLog.d(TAG, "GetComicTask doInBackground");
        if (db.getComicCount() == 0) {
            //LLog.d(TAG, "db.getComicCount() == 0");
            comicList = doTask(link);
            //LLog.d(TAG, "comicList.size(): " + comicList.size());
            if (!comicList.isEmpty()) {
                for (int i = 0; i < comicList.size(); i++) {
                    long result = db.addComic(comicList.get(i));
                    //LLog.d(TAG, "addComic result: " + result);
                    publishProgress(i * 100 / comicList.size());
                }
            }
        } else {
            //LLog.d(TAG, "db.getComicCount() != 0");
            comicList = db.getAllComic();
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LLog.e(TAG, "InterruptedException " + e.toString());
            }*/
        }
        if (comicList.size() < 1) {
            getComicSuccess = false;
        } else {
            getComicSuccess = true;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //LLog.d(TAG, "onProgressUpdate " + values[0]);
        if (progressDialog != null) {
            if (values[0] != progressDialog.getProgress()) {
                progressDialog.setProgress(values[0]);
                //LLog.d(TAG, ">>>>setProgress");
            }
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //LLog.d(TAG, "GetComicTask onPostExecute getComicSuccess: " + getComicSuccess);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
            progressDialog = null;
        }
        if (getComicSuccess) {
            if (callback != null) {
                callback.onSuccess(comicList);
            }
        } else {
            if (callback != null) {
                callback.onError();
            }
        }
        super.onPostExecute(aVoid);
    }

    private List<Comic> doTask(String link) {
        List<Comic> comicList = new ArrayList<Comic>();
        //luu tru danh sach truyen duoi dang html code
        boolean state = LStoreUtil.saveHTMLCodeFromURLToSDCard(activity, link, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_HTML_CODE);
        //state = true -> luu tru thanh cong
        //state = false -> luu tru that bai
        if (state) {
            comicList = parseData();
        } else {
            numberOfDoTaskTryAgain++;
            if (numberOfDoTaskTryAgain > MAX_TRY_AGAIN) {
                return comicList;//return list empty
            }
            doTask(link);
        }
        return comicList;
    }

    private List<Comic> parseData() {
        List<Comic> comicList = new ArrayList<Comic>();
        Document document;
        try {
            /*parse jsoup from file in asset folder*/
            //is = getAssets().open("comic.txt");
            //document = Jsoup.parse(is, "UTF-8", link);

            /*parse jsoup from url*/
            //document = Jsoup.connect(link).get();

            /*parse jsoup from sdcard*/
            String pathMainComicsListHTMLCode = LStoreUtil.getPathOfFileNameMainComicsListHTMLCode(activity);
            //LLog.d(TAG, "pathMainComicsListHTMLCode: " + pathMainComicsListHTMLCode);
            File input = new File(pathMainComicsListHTMLCode);
            document = Jsoup.parse(input, "UTF-8", "http://example.com/");

            for (Element eMangaFocus : document.select("div[class=manga-focus]")) {
                Comic comic = new Comic();
                Elements eTitle = eMangaFocus.select("span[class=manga]");
                //LLog.d(TAG, "tieu de: " + eTitle.text());//Tieu de: +Anima
                comic.setTitle(eTitle.text());

                Elements urlComic = eTitle.select("a");
                //LLog.d(TAG, "tmpLink: " + urlComic.attr("href"));//url:
                // http://truyentranhtuan.com/anima/
                comic.setUrl(urlComic.attr("href"));

                Elements eDate = eMangaFocus.select("span[class=current-date]");
                //LLog.d(TAG, "eDate: " + eDate.text());//04.10.2016
                comic.setDate(eDate.text());
                comicList.add(comic);
            }
        } catch (Exception e) {
            numberOfParseDataTryAgain++;
            if (numberOfParseDataTryAgain > MAX_TRY_AGAIN) {
                return comicList;//return list empty
            }
            //LLog.d(TAG, "err : " + e.toString() + " doTask again");
            parseData();
        }
        return comicList;
    }
}
