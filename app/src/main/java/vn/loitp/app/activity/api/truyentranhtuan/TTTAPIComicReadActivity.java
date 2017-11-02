package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

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
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.TTTComic;
import vn.loitp.app.activity.customviews.progress_loadingview.avloading_indicator_view._lib.avi.AVLoadingIndicatorView;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.common.Constants;
import vn.loitp.app.utilities.LLog;
import vn.loitp.app.utilities.LStoreUtil;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class TTTAPIComicReadActivity extends BaseActivity {
    private TextView tvTitle;
    private TextView tv;
    private AVLoadingIndicatorView avi;
    private Button btSelect;

    private TTTChap tttChap;
    private List<Chap> chapList = new ArrayList<>();
    private final int MAX_TRY_AGAIN = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btSelect = (Button) findViewById(R.id.bt_select);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tv = (TextView) findViewById(R.id.tv);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        String urlComic = "http://truyentranhtuan.com/one-piece/";
        new GetChapTask(urlComic).execute();
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
        return R.layout.activity_api_ttt_comic_read;
    }


    private class GetChapTask extends AsyncTask<Void, Void, Void> {
        private String url;
        private String stringInfo = "";
        private boolean getChapSuccess = false;

        public GetChapTask(String url) {
            this.url = url;
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

                    stringInfo = tttChap.getInfo().getOtherName() + "\n\n" + tttChap.getInfo().getAuthor() + "\n\n" + tttChap.getInfo().getType() + "\n\n" + tttChap.getInfo().getNewChap() + "\n\n"
                            + tttChap.getInfo().getSummary() + "\n\n";
                    LStoreUtil.writeToFile(activity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.getFileNameComic(url), LSApplication.getInstance().getGson().toJson(tttChap), null);
                    getChapSuccess = true;
                }

                //save url img cover to list comic -> tttChap.getInfo().getCover()
                if (tttChap.getInfo().getCover() != null) {
                    updateInfoToComicList(url, tttChap.getInfo().getCover(), tttChap.getInfo().getType());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            LLog.d(TAG, "onPostExecute " + LSApplication.getInstance().getGson().toJson(tttChap));
            LLog.d(TAG, "getChapSuccess " + getChapSuccess);
            LUIUtil.printBeautyJson(tttChap, tv);
            avi.smoothToHide();
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

            //get oldChapList (json exist)
            List<Chap> oldChapList = getOldChapList(link);

            Elements eChap = document.select("span[class=chapter-name]");
            for (Element x : eChap) {
                Chap chap = new Chap();
                chap.setReaded(false);
                //LLog.d(TAG, "tvTitle: " + x.text());//+Anima 74
                chap.setTit(x.text());
                Elements linkChap = x.select("a");
                //LLog.d(TAG, linkChap.attr("href"));//http://truyentranhtuan
                // .com/anima-chuong-74/
                chap.setUrl(linkChap.attr("href"));

                //chapList.add(chap);
                Chap checkedChap = checkChapExist(chap, oldChapList);
                if (checkedChap == null) {
                    //LLog.d(TAG, "chap is !exist");
                    chapList.add(chap);
                } else {
                    //LLog.d(TAG, "chap is exist");
                    chapList.add(checkedChap);
                }
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

    private Chap checkChapExist(Chap newChap, List<Chap> oldListChap) {
        Chap existChap = null;
        for (Chap oldChap : oldListChap) {
            String oldTit = oldChap.getTit();
            String newTit = newChap.getTit();
            String oldUrl = oldChap.getUrl();
            String newUrl = newChap.getUrl();
            //LLog.d(TAG, "check: " + oldTit + newTit);
            //LLog.d(TAG, "check: " + oldUrl + newUrl);
            if (oldTit.equalsIgnoreCase(newTit) && oldUrl.equalsIgnoreCase(newUrl)) {
                existChap = oldChap;
                break;
            }
        }
        return existChap;
    }


    private List<Chap> getOldChapList(String link) {
        /*LFileUtil.readTxtFromFolder(activity, LFileUtil.FOLDER_TRUYENTRANHTUAN, LFileUtil
                .getFileNameComic(link), new EventReadFromFolder() {
            @Override
            public void onSuccess(String oldJson) {
                LLog.d(TAG, "getOldChapList onSuccess");
                if (oldJson != null && !oldJson.isEmpty()) {
                    TTTChap oldTTTChap = LApplication.getGson().fromJson(oldJson, TTTChap.class);
                    if (oldTTTChap != null) {
                        if (oldTTTChap.getChaps() != null) {
                            if (oldTTTChap.getChaps().getChap() != null) {
                                oldChapList = oldTTTChap.getChaps().getChap();
                            }
                        }
                    }
                }
            }

            @Override
            public void onError() {
                LLog.d(TAG, "getOldChapList onError");
            }
        });*/

        List<Chap> oldChapList = new ArrayList<>();
        String oldJson = LStoreUtil.readTxtFromFolder(activity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.getFileNameComic(link));
        if (oldJson != null && !oldJson.isEmpty()) {
            TTTChap oldTTTChap = LSApplication.getInstance().getGson().fromJson(oldJson, TTTChap.class);
            if (oldTTTChap != null) {
                if (oldTTTChap.getChaps() != null) {
                    if (oldTTTChap.getChaps().getChap() != null) {
                        oldChapList = oldTTTChap.getChaps().getChap();
                    }
                }
            }
        }
        return oldChapList;
    }

    private void updateInfoToComicList(final String urlComic, final String imgCover, final String type) {
        LLog.d(TAG, "urlComic: " + urlComic + ",imgCover: " + imgCover);

        //save to comic list
        LStoreUtil.readTxtFromFolder(activity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST, new LStoreUtil.EventReadFromFolder() {
            @Override
            public void onSuccess(String jsonTTTComic) {
                LLog.d(TAG, "updateInfoToComicList if >>> jsonTTTComic: " + jsonTTTComic);
                if (jsonTTTComic != null && !jsonTTTComic.isEmpty()) {
                    TTTComic tttComic = LSApplication.getInstance().getGson().fromJson(jsonTTTComic, TTTComic.class);
                    if (tttComic.getComics() != null) {
                        List<Comic> comicList = tttComic.getComics().getComic();
                        LLog.d(TAG, "comicList size: " + comicList.size());
                        int pos = Constants.NOT_FOUND;
                        for (int i = 0; i < comicList.size(); i++) {
                            if (comicList.get(i).getUrl().equals(urlComic)) {
                                pos = i;
                                break;
                            }
                        }
                        LLog.d(TAG, "find at: " + pos);
                        if (pos != Constants.NOT_FOUND) {
                            comicList.get(pos).setUrlImg(imgCover);
                            comicList.get(pos).setType(type);
                            LLog.d(TAG, "setUrlImg: " + LSApplication.getInstance().getGson().toJson(comicList.get(pos)));
                        }
                        LStoreUtil.writeToFile(activity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST, LSApplication.getInstance().getGson().toJson(tttComic), null);
                    }
                } else {
                    LLog.d(TAG, "updateInfoToComicList else");
                }
            }

            @Override
            public void onError() {
                LLog.d(TAG, "updateInfoToComicList onError");
            }
        });

        //save to favourite list
        LStoreUtil.readTxtFromFolder(activity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE, new LStoreUtil.EventReadFromFolder() {
            @Override
            public void onSuccess(String jsonFavouriteComic) {
                LLog.d(TAG, "FILE_NAME_MAIN_COMICS_LIST_FAVOURITE onSuccess: " + jsonFavouriteComic);
                List<Comic> tmpComicList = new ArrayList<>();
                if (jsonFavouriteComic == null || jsonFavouriteComic.isEmpty()) {
                    LLog.d(TAG, "jsonFavouriteComic is null or empty -> do nothing");
                } else {
                    LLog.d(TAG, "jsonFavouriteComic is fine");
                    tmpComicList = LSApplication.getInstance().getGson().fromJson(jsonFavouriteComic, new TypeToken<List<Comic>>() {
                    }.getType());
                    LLog.d(TAG, "tmpComicList size: " + tmpComicList.size());
                    int pos = Constants.NOT_FOUND;
                    for (int i = 0; i < tmpComicList.size(); i++) {
                        if (tmpComicList.get(i).getUrl().equals(urlComic)) {
                            pos = i;
                            break;
                        }
                    }
                    LLog.d(TAG, "find at: " + pos);
                    if (pos != Constants.NOT_FOUND) {
                        tmpComicList.get(pos).setUrlImg(imgCover);
                        LLog.d(TAG, "setUrlImg: " + LSApplication.getInstance().getGson().toJson(tmpComicList.get(pos)));
                        LStoreUtil.writeToFile(activity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE, LSApplication.getInstance().getGson().toJson(tmpComicList), null);
                    } else {
                        LLog.d(TAG, "find at NOT_FOUND >>> do nothing");
                    }
                }
            }

            @Override
            public void onError() {
                LLog.d(TAG, "updateInfoToComicList FILE_NAME_MAIN_COMICS_LIST_FAVOURITE onError");
            }
        });
    }
}
