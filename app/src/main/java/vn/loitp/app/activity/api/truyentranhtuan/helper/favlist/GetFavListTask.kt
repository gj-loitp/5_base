package vn.loitp.app.activity.api.truyentranhtuan.helper.favlist

import android.app.Activity
import android.os.AsyncTask
import com.core.base.BaseApplication
import com.core.utilities.LStoreUtil
import com.google.gson.reflect.TypeToken
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic

//TODO convert croutine
class GetFavListTask(private val mActivity: Activity,
                     private val callback: Callback?)
    : AsyncTask<Void, Void, Void>() {

    private val logTag = javaClass.simpleName
    private var comicList = ArrayList<Comic>()

    interface Callback {
        fun onSuccess(comicList: List<Comic>)
    }

    override fun doInBackground(vararg voids: Void): Void? {
        val json = LStoreUtil.readTxtFromFolder(folderName = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE)
        if (json.isEmpty()) {
        } else {
            comicList = BaseApplication.gson.fromJson(json, object : TypeToken<List<Comic?>?>() {}.type)
        }
        return null
    }

    override fun onPostExecute(aVoid: Void?) {
        callback?.onSuccess(comicList)
        super.onPostExecute(aVoid)
    }

}
