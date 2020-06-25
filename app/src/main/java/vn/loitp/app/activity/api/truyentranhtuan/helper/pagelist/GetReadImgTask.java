package vn.loitp.app.activity.api.truyentranhtuan.helper.pagelist;

import android.os.AsyncTask;

import com.core.utilities.LLog;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vn.loitp.app.app.LApplication;

/**
 * Created by www.muathu@gmail.com on 11/2/2017.
 */

public class GetReadImgTask extends AsyncTask<Void, Void, Void> {
    private final String TAG = getClass().getSimpleName();
    //private List<Chap> chapList = new ArrayList<>();
    private List<String> imagesListOfOneChap = new ArrayList<>();//list img per chap
    private String link = "";

    private AVLoadingIndicatorView avLoadingIndicatorView;

    public interface Callback {
        void onSuccess(List<String> imagesListOfOneChap);

        void onError();
    }

    private Callback callback;

    public GetReadImgTask(String link, AVLoadingIndicatorView avLoadingIndicatorView, Callback callback) {
        this.link = link;
        this.avLoadingIndicatorView = avLoadingIndicatorView;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        if (imagesListOfOneChap != null) {
            imagesListOfOneChap.clear();
        }
        avLoadingIndicatorView.smoothToShow();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        LLog.d(TAG, "doInBackground");
        imagesListOfOneChap = doTask(link);
        LLog.d(TAG, ">>>imagesListOfOneChap: " + LApplication.Companion.getGson().toJson(imagesListOfOneChap));
        if (imagesListOfOneChap != null && !imagesListOfOneChap.isEmpty()) {
            for (int i = 0; i < imagesListOfOneChap.size(); i++) {
                String urlImg = imagesListOfOneChap.get(i);
                if (urlImg.contains("http://images2-focus-opensocial.googleusercontent.com")) {
                    int index = urlImg.lastIndexOf("url=http");
                    String tmp = urlImg.substring(index + 4);
                    LLog.d(TAG, "tmp: " + tmp);
                    imagesListOfOneChap.set(i, tmp);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        LLog.d(TAG, "onPostExecute: " + LApplication.Companion.getGson().toJson(imagesListOfOneChap));
        if (imagesListOfOneChap != null && !imagesListOfOneChap.isEmpty()) {
            if (callback != null) {
                callback.onSuccess(imagesListOfOneChap);
            }
        } else {
            if (callback != null) {
                callback.onError();
            }
        }
        avLoadingIndicatorView.smoothToHide();
        super.onPostExecute(aVoid);
    }

    private List<String> doTask(String link) {
        String originalString = "";
        String stringAfterSplit = "";
        String[] arrString;
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
                        LLog.d(TAG, "gson stringAfterSplit: " + LApplication.Companion.getGson().toJson(arrString));
                        Collections.addAll(imgList, arrString);
                        if (needToSortList) {
                            Collections.sort(imgList, new Comparator<String>() {

                                @Override
                                public int compare(String o1, String o2) {
                                    return o1.compareToIgnoreCase(o2);
                                }

                            });
                        }
                        LLog.d(TAG, "gson stringAfterSplit after sort: " + LApplication.Companion.getGson().toJson(arrString));
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
