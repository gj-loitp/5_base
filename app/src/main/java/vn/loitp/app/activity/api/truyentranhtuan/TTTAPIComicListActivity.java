package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import loitp.utils.util.ToastUtils;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comics;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.TTTComic;
import vn.loitp.app.activity.customviews.progress_loadingview.avloading_indicator_view._lib.avi.AVLoadingIndicatorView;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.common.Constants;
import vn.loitp.app.utilities.LLog;
import vn.loitp.app.utilities.LStoreUtil;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class TTTAPIComicListActivity extends BaseActivity {
    private List<Comic> comicList = new ArrayList<>();
    private int numberOfParseDataTryAgain = 0;
    private int numberOfDoTaskTryAgain = 0;
    private final int MAX_TRY_AGAIN = 4;

    private TextView tv;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv = (TextView) findViewById(R.id.tv);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        new GetComicTask(Constants.MAIN_LINK_TRUYENTRANHTUAN).execute();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_api_ttt_comic_list;
    }

    private class GetComicTask extends AsyncTask<Void, Void, Void> {
        private final String TAG = "GetComicTask";
        private boolean getComicSuccess = false;
        private String link;

        public GetComicTask(String link) {
            this.link = link;
        }

        @Override
        protected void onPreExecute() {
            LLog.d(TAG, "GetComicTask onPreExecute");
            avi.smoothToShow();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            LLog.d(TAG, "GetComicTask doInBackground");
            comicList = doTask(link);
            if (comicList.size() < 1) {
                getComicSuccess = false;
            } else {
                //restore comic list with img cover url
                String jsonTTTComic = LStoreUtil.readTxtFromFolder(activity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST);
                if (jsonTTTComic == null || jsonTTTComic.isEmpty()) {
                    LLog.d(TAG, ">>>1 jsonTTTComic is null or empty >> first to create comic list");

                    TTTComic tttComic = new TTTComic();
                    Comics comics = new Comics();
                    comics.setComic(comicList);
                    tttComic.setComics(comics);
                    jsonTTTComic = LSApplication.getInstance().getGson().toJson(tttComic);
                    LLog.d(TAG, "jsonTTTComic: " + jsonTTTComic);
                    LStoreUtil.writeToFile(activity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST, jsonTTTComic, null);
                    getComicSuccess = true;
                } else {
                    LLog.d(TAG, "restore readTxtFromFolder jsonTTTComic: " + jsonTTTComic);
                    if (jsonTTTComic != null && !jsonTTTComic.isEmpty()) {
                        TTTComic tttComic = LSApplication.getInstance().getGson().fromJson(jsonTTTComic, TTTComic.class);
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
                        jsonTTTComic = LSApplication.getInstance().getGson().toJson(tttComic);
                        LLog.d(TAG, "jsonTTTComic: " + jsonTTTComic);
                        LStoreUtil.writeToFile(activity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST, jsonTTTComic, null);
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
                LUIUtil.printBeautyJson(comicList, tv);
            } else {
                ToastUtils.showShort("Error");
            }
            avi.smoothToHide();
            super.onPostExecute(aVoid);
        }
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
            LLog.d(TAG, "pathMainComicsListHTMLCode: " + pathMainComicsListHTMLCode);
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
            LLog.d(TAG, "err : " + e.toString() + " doTask again");
            parseData();
        }
        return comicList;
    }
}
