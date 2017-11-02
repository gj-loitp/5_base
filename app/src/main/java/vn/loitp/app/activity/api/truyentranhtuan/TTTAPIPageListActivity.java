package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Chap;
import vn.loitp.app.activity.customviews.progress_loadingview.avloading_indicator_view._lib.avi.AVLoadingIndicatorView;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LLog;
import vn.loitp.livestar.R;

public class TTTAPIPageListActivity extends BaseActivity {
    private TextView tv;
    private AVLoadingIndicatorView avi;

    private List<Chap> chapList = new ArrayList<>();
    private List<String> imagesListOfOneChap = new ArrayList<>();//list img per chap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        String currentLink = "http://truyentranhtuan.com/one-piece-chuong-69/";
        new GetReadImgTask(currentLink).execute();
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
        return R.layout.activity_api_ttt_page_list;
    }

    private class GetReadImgTask extends AsyncTask<Void, Void, Void> {
        private String link = "";

        GetReadImgTask(String link) {
            this.link = link;
        }

        @Override
        protected void onPreExecute() {
            if (imagesListOfOneChap != null) {
                imagesListOfOneChap.clear();
            }
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            LLog.d(TAG, "doInBackground");
            imagesListOfOneChap = doTask(link);
            LLog.d(TAG, ">>>imagesListOfOneChap: " + LSApplication.getInstance().getGson().toJson
                    (imagesListOfOneChap));
            if (imagesListOfOneChap != null && !imagesListOfOneChap.isEmpty()) {
                for (int i = 0; i < imagesListOfOneChap.size(); i++) {
                    String urlImg = imagesListOfOneChap.get(i);
                    if (urlImg.contains("http://images2-focus-opensocial.googleusercontent.com")) {
                        int index = urlImg.lastIndexOf("url=http");
                        String tmp = urlImg.substring(index + 4, urlImg.length());
                        LLog.d(TAG, "tmp: " + tmp);
                        imagesListOfOneChap.set(i, tmp);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            LLog.d(TAG, "onPostExecute: " + LSApplication.getInstance().getGson().toJson(imagesListOfOneChap));

            /*if (imagesListOfOneChap != null && !imagesListOfOneChap.isEmpty()) {
                initViewPager();
            } else {
                showDialogConfirmCannotRead();
            }*/
            super.onPostExecute(aVoid);
        }
    }

    private List<String> doTask(String link) {
        String originalString = "";
        String stringAfterSplit = "";
        String arrString[];
        Document document;
        List<String> imgList = new ArrayList<>();
        try {
            boolean needToSortList = false;

            String subFirstString_0 = "var slides_page_url_path = [";
            String subLastString_0 = "if (slides_page_url_path.length > 0)";

            String subFirstString_1 = "var slides_page_path =";
            String subLastString_1 = "var use_server_gg";

            document = Jsoup.connect(link).get();
            Elements scriptElements = document.getElementsByTag("script");
            for (Element element : scriptElements) {
                for (DataNode node : element.dataNodes()) {
                    originalString = node.getWholeData();
                    //LLog.d(TAG, "string: " + string);
                    //fileHelper.writeToFile(null, "test.txt", string);
                    if (originalString.contains("slides_page_url_path")) {
                        //LLog.d(TAG, "string: " + string);
                        int firstIndex = originalString.indexOf(subFirstString_0);
                        int lastIndex = originalString.indexOf(subLastString_0);
                        LLog.d(TAG, "firstIndex: " + firstIndex + ", lastIndex: " + lastIndex);
                        stringAfterSplit = originalString.substring(firstIndex + subFirstString_0.length(), lastIndex);
                        stringAfterSplit = stringAfterSplit.replace("];", "");
                        stringAfterSplit = stringAfterSplit.replace("\"", "");
                        stringAfterSplit = stringAfterSplit.trim();
                        LLog.d(TAG, "stringAfterSplit:" + stringAfterSplit);
                        if (stringAfterSplit.isEmpty()) {
                            LLog.d(TAG, "stringAfterSplit.isEmpty()");
                            firstIndex = originalString.indexOf(subFirstString_1);
                            lastIndex = originalString.indexOf(subLastString_1);
                            LLog.d(TAG, "firstIndex: " + firstIndex + ", lastIndex: " + lastIndex);
                            stringAfterSplit = originalString.substring(firstIndex + subFirstString_1.length(), lastIndex);
                            stringAfterSplit = stringAfterSplit.replace("];", "");
                            stringAfterSplit = stringAfterSplit.replace("[", "");
                            stringAfterSplit = stringAfterSplit.replace("\"", "");
                            stringAfterSplit = stringAfterSplit.trim();
                            LLog.d(TAG, "stringAfterSplit: " + stringAfterSplit);
                            needToSortList = true;
                        }
                        arrString = stringAfterSplit.split(",");
                        LLog.d(TAG, "gson stringAfterSplit: " + LSApplication.getInstance().getGson().toJson(arrString));
                        Collections.addAll(imgList, arrString);
                        if (needToSortList) {
                            Collections.sort(imgList, new Comparator<String>() {

                                @Override
                                public int compare(String o1, String o2) {
                                    return o1.compareToIgnoreCase(o2);
                                }

                            });
                        }
                        LLog.d(TAG, "gson stringAfterSplit after sort: " + LSApplication.getInstance().getGson().toJson(arrString));
                        return imgList;
                    } else {
                        LLog.d(TAG, "doTask else");
                    }
                }
            }
            return null;
        } catch (Exception e) {
            LLog.d(TAG, "err : " + e.toString() + " doTask again");
            return null;
        }
    }
}
