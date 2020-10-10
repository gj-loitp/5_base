package vn.loitp.app.activity.api.truyentranhtuan.helper.favlist

import android.os.AsyncTask
import com.core.base.BaseApplication
import com.core.common.Constants
import com.core.utilities.LStoreUtil
import com.google.gson.reflect.TypeToken
import vn.loitp.app.activity.api.truyentranhtuan.helper.ComicUtils.Companion.isComicExistAt
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic
import java.util.*

//TODO convert croutine
class RemoveComicFavListTask(
        private val mComic: Comic,
        private val callback: Callback?) :
        AsyncTask<Void, Void, Int>() {

    private val logTag = javaClass.simpleName
    private var comicList = ArrayList<Comic>()

    companion object {
        private const val RESULT_REMOVE_COMIC_SUCCESS = 69
        private const val RESULT_COMIC_IS_NOT_EXIST = 96
        private const val RESULT_ADD_COMIC_ERROR = 66
    }

    interface Callback {
        fun onRemoveComicSuccess(mComic: Comic, comicList: List<Comic>)
        fun onComicIsNotExist(mComic: Comic, comicList: List<Comic>)
        fun onRemoveComicError()
    }

    override fun doInBackground(vararg voids: Void): Int {
        val json = LStoreUtil.readTxtFromFolder(folderName = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE)
        var mResult = 0

        if (json.isEmpty()) {
            mResult = RESULT_COMIC_IS_NOT_EXIST
        } else {
            comicList = BaseApplication.gson.fromJson(json, object : TypeToken<List<Comic?>?>() {}.type)
            val pos = isComicExistAt(mComic, comicList)
            mResult = if (pos != Constants.NOT_FOUND) {
                comicList.removeAt(pos)
                val newJson = BaseApplication.gson.toJson(comicList)
                val fileSaved = LStoreUtil.writeToFile(folder = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE, body = newJson)
                if (fileSaved == null) {
                    RESULT_ADD_COMIC_ERROR
                } else {
                    RESULT_REMOVE_COMIC_SUCCESS
                }
            } else {
                RESULT_COMIC_IS_NOT_EXIST
            }
        }
        return mResult
    }

    override fun onPostExecute(mResult: Int) {
        when (mResult) {
            RESULT_COMIC_IS_NOT_EXIST -> {
                callback?.onComicIsNotExist(mComic = mComic, comicList = comicList)
            }
            RESULT_ADD_COMIC_ERROR -> {
                callback?.onRemoveComicError()
            }
            RESULT_REMOVE_COMIC_SUCCESS -> {
                callback?.onRemoveComicSuccess(mComic = mComic, comicList = comicList)
            }
        }
        super.onPostExecute(mResult)
    }

}
