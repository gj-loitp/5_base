package vn.loitp.app.activity.api.truyentranhtuan.helper.chaplist;

import android.app.Activity;
import android.os.AsyncTask;

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
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;

/**
 * Created by www.muathu@gmail.com on 11/2/2017.
 */

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
        public void onSuccess(TTTChap tttChap);

        public void onError();
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
                LLog.d(TAG, ">>>chapList: " + LSApplication.getInstance().getGson().toJson(chapList));
                //kiem tra xem cac phan tu cua chapList co giong nhau ko
                try {
                    if (chapList.get(0).getTit().equals(chapList.get(1).getTit())) {
                        //co trung
                        for (int i = 0; i < chapList.size(); i++) {
                            chapList.get(i).setTit(chapList.get(i).getTit() + " * Chap (" + (chapList.size() - i) + ")");
                        }
                        LLog.d(TAG, ">>>chapList: " + LSApplication.getInstance().getGson().toJson(chapList));
                    }
                } catch (NullPointerException e) {
                    LLog.d(TAG, "NullPointerException " + e.toString());
                }

                stringInfo = tttChap.getInfo().getOtherName() + "\n\n" + tttChap.getInfo().getAuthor() + "\n\n" + tttChap.getInfo().getType() + "\n\n" + tttChap.getInfo().getNewChap() + "\n\n" + tttChap.getInfo().getSummary() + "\n\n";
                getChapSuccess = LStoreUtil.writeToFile(activity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.getFileNameComic(url), LSApplication.getInstance().getGson().toJson(tttChap));
            }

            //save url img cover to list comic -> tttChap.getInfo().getCover()
            /*if (tttChap.getInfo().getCover() != null) {
                updateInfoToComicList(url, tttChap.getInfo().getCover(), tttChap.getInfo().getType());
            }*/
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        LLog.d(TAG, "onPostExecute " + LSApplication.getInstance().getGson().toJson(tttChap));
        if (getChapSuccess) {
            if (callback != null) {
                callback.onSuccess(tttChap);
            }
        } else {
            if (callback != null) {
                callback.onError();
            }
        }
            /*if (getChapSuccess) {
                if (chapList != null && !chapList.isEmpty()) {
                    chapAdapter = new ChapAdapter(activity, tttChap.getChaps().getChap());

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                    //recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
                    //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, 1));
                    recyclerView.setAdapter(chapAdapter);
                } else {
                    LToast.show(activity, R.string.list_chap_of_comic_is_empty);
                }
                if (tttChap.getInfo().getCover() != null) {
                    Glide.with(activity).load(tttChap.getInfo().getCover()).centerCrop()
                            //.placeholder(R.drawable.loading_spinner)
                            .crossFade().into(toolbarImage);
                    Glide.with(activity).load(tttChap.getInfo().getCover()).centerCrop()
                            //.placeholder(R.drawable.loading_spinner)
                            .crossFade().into((ImageView) findViewById(R.id.iv_blur));
                }
                tvInfo.setText(stringInfo);
            } else {
                LToast.show(activity, R.string.err_connect_failed);
            }*/
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
