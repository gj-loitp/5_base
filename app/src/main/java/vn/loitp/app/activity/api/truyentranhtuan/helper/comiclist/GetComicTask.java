package vn.loitp.app.activity.api.truyentranhtuan.helper.comiclist;

import android.app.Activity;
import android.os.AsyncTask;

import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comics;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.TTTComic;
import vn.loitp.app.app.LApplication;

//TODO convert rx
public class GetComicTask extends AsyncTask<Void, Void, Void> {
    private final String TAG = getClass().getSimpleName();
    private int numberOfParseDataTryAgain = 0;
    private int numberOfDoTaskTryAgain = 0;
    private final int MAX_TRY_AGAIN = 4;
    private boolean getComicSuccess = false;
    private String link;
    private List<Comic> comicList = new ArrayList<>();
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private Activity activity;

    public interface Callback {
        void onSuccess(List<Comic> comicList);

        void onError();
    }

    private Callback callback;

    public GetComicTask(Activity activity, String link, AVLoadingIndicatorView avLoadingIndicatorView, Callback callback) {
        this.activity = activity;
        this.link = link;
        this.avLoadingIndicatorView = avLoadingIndicatorView;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        LLog.d(TAG, "GetComicTask onPreExecute");
        avLoadingIndicatorView.smoothToShow();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        LLog.d(TAG, "GetComicTask doInBackground");
        comicList = doTask(link);
        //LLog.d(TAG, "doInBackground comicList size: " + comicList.size());
        if (comicList.size() < 1) {
            getComicSuccess = false;
        } else {
            //restore comic list with img cover url
            String jsonTTTComic = LStoreUtil.INSTANCE.readTxtFromFolder(activity, LStoreUtil.INSTANCE.getFOLDER_TRUYENTRANHTUAN(), LStoreUtil.INSTANCE.getFILE_NAME_MAIN_COMICS_LIST());
            if (jsonTTTComic == null || jsonTTTComic.isEmpty()) {
                LLog.d(TAG, ">>>1 jsonTTTComic is null or empty >> first to create comic list");

                TTTComic tttComic = new TTTComic();
                Comics comics = new Comics();
                comics.setComic(comicList);
                tttComic.setComics(comics);
                jsonTTTComic = LApplication.Companion.getGson().toJson(tttComic);
                LLog.d(TAG, "jsonTTTComic: " + jsonTTTComic);
                LStoreUtil.INSTANCE.writeToFile(activity, LStoreUtil.INSTANCE.getFOLDER_TRUYENTRANHTUAN(), LStoreUtil.INSTANCE.getFILE_NAME_MAIN_COMICS_LIST(), jsonTTTComic);
                getComicSuccess = true;
            } else {
                LLog.d(TAG, "restore readTxtFromFolder jsonTTTComic: " + jsonTTTComic);
                if (jsonTTTComic != null && !jsonTTTComic.isEmpty()) {
                    TTTComic tttComic = LApplication.Companion.getGson().fromJson(jsonTTTComic, TTTComic.class);
                    try {
                        List<Comic> oldComicList = tttComic.getComics().getComic();
                        LLog.d(TAG, ">>>2 oldComicList size: " + oldComicList.size());
                        if (!oldComicList.isEmpty()) {
                            //restore url img cover
                            //lay tat ca nhung comic da co san img cover url trong oldComicList
                            List<Comic> savedInfoComicList = new ArrayList<Comic>();
                            for (Comic comic : oldComicList) {
                                if (comic.getUrlImg() != null) {
                                    savedInfoComicList.add(comic);
                                }
                            }
                            LLog.d(TAG, "urlCoverComicList size: " + savedInfoComicList.size());

                            //gan du lieu url img cover cho comic list moi vua tai ve
                            for (int i = 0; i < savedInfoComicList.size(); i++) {
                                for (int j = 0; j < comicList.size(); j++) {
                                    if (savedInfoComicList.get(i).getUrl().equals(comicList.get(j).getUrl())) {
                                        comicList.get(j).setUrlImg(savedInfoComicList.get(i).getUrlImg());
                                        comicList.get(j).setType(savedInfoComicList.get(i).getType());
                                    }
                                }
                            }
                            //end restore url img cover
                            LLog.d(TAG, "restore success!");
                        }
                        getComicSuccess = true;
                        LLog.d(TAG, "restore success >> set getComicSuccess = true");
                    } catch (NullPointerException e) {
                        LLog.d(TAG, "NullPointerException " + e.toString());
                        getComicSuccess = false;
                    }
                } else {
                    LLog.d(TAG, ">>>3 tttComic is null or empty >> first to create comic list");

                    TTTComic tttComic = new TTTComic();
                    Comics comics = new Comics();
                    comics.setComic(comicList);
                    tttComic.setComics(comics);
                    jsonTTTComic = LApplication.Companion.getGson().toJson(tttComic);
                    LLog.d(TAG, "jsonTTTComic: " + jsonTTTComic);
                    LStoreUtil.INSTANCE.writeToFile(activity, LStoreUtil.INSTANCE.getFOLDER_TRUYENTRANHTUAN(), LStoreUtil.INSTANCE.getFILE_NAME_MAIN_COMICS_LIST(), jsonTTTComic);
                    getComicSuccess = true;
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        LLog.d(TAG, "GetComicTask onPostExecute getComicSuccess: " + getComicSuccess);
        if (getComicSuccess) {
            if (callback != null) {
                callback.onSuccess(comicList);
            }
        } else {
            if (callback != null) {
                callback.onError();
            }
        }
        avLoadingIndicatorView.smoothToHide();
        super.onPostExecute(aVoid);
    }

    private List<Comic> doTask(String link) {
        List<Comic> comicList = new ArrayList<>();
        //luu tru danh sach truyen duoi dang html code
        boolean state = LStoreUtil.INSTANCE.saveHTMLCodeFromURLToSDCard(activity, link, LStoreUtil.INSTANCE.getFOLDER_TRUYENTRANHTUAN(), LStoreUtil.INSTANCE.getFILE_NAME_MAIN_COMICS_LIST_HTML_CODE());
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
            String pathMainComicsListHTMLCode = LStoreUtil.INSTANCE.getPathOfFileNameMainComicsListHTMLCode(activity);
            LLog.d(TAG, "parseData pathMainComicsListHTMLCode: " + pathMainComicsListHTMLCode);
            File input = new File(pathMainComicsListHTMLCode);
            LLog.d(TAG, "file input is exist: " + input.exists());
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
            LLog.e(TAG, "parseData err : " + e.toString() + " doTask again");
            parseData();
        }
        return comicList;
    }
}
