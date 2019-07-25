package vn.loitp.app.activity.api.truyentranhtuan.helper.favlist;

import android.app.Activity;
import android.os.AsyncTask;

import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;
import com.google.gson.reflect.TypeToken;
import com.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.app.LApplication;

/**
 * Created by www.muathu@gmail.com on 11/2/2017.
 */

public class GetFavListTask extends AsyncTask<Void, Void, Void> {
    private final String TAG = getClass().getSimpleName();

    private Activity mActivity;
    private AVLoadingIndicatorView avi;

    private List<Comic> comicList = new ArrayList<>();

    public interface Callback {
        public void onSuccess(List<Comic> comicList);
    }

    private Callback callback;

    public GetFavListTask(Activity activity, AVLoadingIndicatorView avi, Callback callback) {
        this.mActivity = activity;
        this.avi = avi;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        avi.smoothToShow();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        LLog.INSTANCE.d(TAG, "doInBackground");
        String json = LStoreUtil.readTxtFromFolder(mActivity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE);
        if (json == null || json.isEmpty()) {
            LLog.INSTANCE.d(TAG, "json == null || json.isEmpty()");
        } else {
            comicList = LApplication.Companion.getGson().fromJson(json, new TypeToken<List<Comic>>() {
            }.getType());
        }
        LLog.INSTANCE.d(TAG, "comicList size: " + comicList);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        avi.smoothToHide();
        if (callback != null) {
            callback.onSuccess(comicList);
        }
        super.onPostExecute(aVoid);
    }
}
