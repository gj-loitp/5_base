package vn.loitp.app.activity.api.truyentranhtuan.helper.chaplist;

import android.app.Activity;
import android.os.AsyncTask;

import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Chap;
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Chaps;
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Info;
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.TTTChap;
import vn.loitp.app.app.LApplication;

//TODO convert coroutine
public class GetChapTask extends AsyncTask<Void, Void, Void> {
    private final String TAG = getClass().getSimpleName();
    private TTTChap tttChap;
    private List<Chap> chapList = new ArrayList<>();
    private final int MAX_TRY_AGAIN = 5;

    private Activity activity;
    private String url;
    private String stringInfo = "";
    private boolean getChapSuccess = false;

    public interface Callback {
        void onSuccess(TTTChap tttChap);

        void onError();
    }

    private Callback callback;

    public GetChapTask(Activity activity, String url, Callback callback) {
        this.activity = activity;
        this.url = url;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        tttChap = doTask(url);
        if (tttChap != null) {
            Chaps chaps = tttChap.getChaps();
            if (chaps != null) {
                chapList = chaps.getChap();
                LLog.d(TAG, ">>>chapList: " + LApplication.Companion.getGson().toJson(chapList));
                //kiem tra xem cac phan tu cua chapList co giong nhau ko
                try {
                    if (chapList.get(0).getTit().equals(chapList.get(1).getTit())) {
                        //co trung
                        for (int i = 0; i < chapList.size(); i++) {
                            chapList.get(i).setTit(chapList.get(i).getTit() + " * Chap (" + (chapList.size() - i) + ")");
                        }
                        LLog.d(TAG, ">>>chapList: " + LApplication.Companion.getGson().toJson(chapList));
                    }
                } catch (NullPointerException e) {
                    LLog.d(TAG, "NullPointerException " + e.toString());
                }

                stringInfo = tttChap.getInfo().getOtherName() + "\n\n" + tttChap.getInfo().getAuthor() + "\n\n" + tttChap.getInfo().getType() + "\n\n" + tttChap.getInfo().getNewChap() + "\n\n" + tttChap.getInfo().getSummary() + "\n\n";
                getChapSuccess = LStoreUtil.Companion.writeToFile(activity, LStoreUtil.Companion.getFOLDER_TRUYENTRANHTUAN(), LStoreUtil.Companion.getFileNameComic(url), LApplication.Companion.getGson().toJson(tttChap));
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        LLog.d(TAG, "onPostExecute " + LApplication.Companion.getGson().toJson(tttChap));
        if (getChapSuccess) {
            if (callback != null) {
                callback.onSuccess(tttChap);
            }
        } else {
            if (callback != null) {
                callback.onError();
            }
        }
        super.onPostExecute(aVoid);
    }

    private int numberOfTryAgain = 0;

    private TTTChap doTask(String link) {
        Document document;
        TTTChap tttChap = new TTTChap();
        try {
            Info info = new Info();
            List<Chap> chapList = new ArrayList<Chap>();
            document = Jsoup.connect(link).get();
            Elements eInforBox = document.select("div#infor-box");

            //img cover
            Elements coverImage = eInforBox.select("img[src]");
            //LLog.d(TAG, "img: " + coverImage.attr("src"));//http://truyentranhtuan
            // .com/wp-content/uploads/2014/08/anima-anh-bia.jpg
            info.setCover(coverImage.attr("src"));

            //infocomic
            Elements infoComics = eInforBox.select("p");
            ArrayList<String> infoComicList = new ArrayList<>();
            for (Element x : infoComics) {
                infoComicList.add(x.text());
            }
            try {
                info.setOtherName(infoComicList.get(0));
                info.setAuthor(infoComicList.get(1));
                info.setType(infoComicList.get(2));
                info.setNewChap(infoComicList.get(3));
                info.setSummary(infoComicList.get(4));
            } catch (Exception e) {
                LLog.d(TAG, "Exception get info: " + e.toString());
            }

            Elements eChap = document.select("span[class=chapter-name]");
            for (Element x : eChap) {
                Chap chap = new Chap();
                //LLog.d(TAG, "tvTitle: " + x.text());//+Anima 74
                chap.setTit(x.text());
                Elements linkChap = x.select("a");
                //LLog.d(TAG, linkChap.attr("href"));//http://truyentranhtuan
                // .com/anima-chuong-74/
                chap.setUrl(linkChap.attr("href"));

                chapList.add(chap);
            }

            //url, ngay release chap, tam thoi chua can dung
              /*Elements url = document.select("span[class=url-name]");
              for (Element d : url) {
                LLog.d(TAG, "url: " + d.text());//24.07.2015
              }*/
            Chaps chaps = new Chaps();
            chaps.setChap(chapList);

            tttChap.setInfo(info);
            tttChap.setChaps(chaps);

            //LLog.d(TAG, "gson.toJson(tttChap): " + gson.toJson(tttChap));
        } catch (Exception e) {
            numberOfTryAgain++;
            if (numberOfTryAgain > MAX_TRY_AGAIN) {
                return tttChap;
            }
            LLog.d(TAG, "err : " + e.toString() + " doTask again");
            doTask(link);
        }
        return tttChap;
    }
}
