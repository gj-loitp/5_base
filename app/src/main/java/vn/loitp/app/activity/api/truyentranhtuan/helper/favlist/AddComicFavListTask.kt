package vn.loitp.app.activity.api.truyentranhtuan.helper.favlist

import android.content.Context
import android.os.AsyncTask
import com.core.base.BaseApplication
import com.core.utilities.LStoreUtil
import com.google.gson.reflect.TypeToken
import vn.loitp.app.activity.api.truyentranhtuan.helper.ComicUtils.Companion.isComicExistIn
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic
import java.util.*

//TODO convert croutine
class AddComicFavListTask(private val context: Context,
                          private val mComic: Comic,
                          private val callback: Callback?)
    : AsyncTask<Void, Void, Int>() {

    private val logTag = javaClass.simpleName
    private var comicList = ArrayList<Comic>()

    companion object {
        private const val RESULT_ADD_COMIC_SUCCESS = 69
        private const val RESULT_COMIC_IS_EXIST = 96
        private const val RESULT_ADD_COMIC_ERROR = 66
    }

    interface Callback {
        fun onAddComicSuccess(mComic: Comic, comicList: List<Comic>)
        fun onComicIsExist(mComic: Comic, comicList: List<Comic>)
        fun onAddComicError()
    }

    override fun doInBackground(vararg voids: Void): Int {
        val json = LStoreUtil.readTxtFromFolder(folderName = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE)
        var mResult = 0

        if (json.isEmpty()) {
            comicList.add(mComic)
            val newJson = BaseApplication.gson.toJson(comicList)
            val fileSaved = LStoreUtil.writeToFile(folder = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE, body = newJson)
            mResult = if (fileSaved == null) {
                RESULT_ADD_COMIC_ERROR
            } else {
                RESULT_ADD_COMIC_SUCCESS
            }
        } else {
            comicList = BaseApplication.gson.fromJson(json, object : TypeToken<List<Comic?>?>() {}.type)
            val isExist = isComicExistIn(comic = mComic, comicList = comicList)
            mResult = if (!isExist) {
                comicList.add(mComic)
                val newJson = BaseApplication.gson.toJson(comicList)
                val fileSaved = LStoreUtil.writeToFile(folder = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE, body = newJson)
                if (fileSaved == null) {
                    RESULT_ADD_COMIC_ERROR
                } else {
                    RESULT_ADD_COMIC_SUCCESS
                }
            } else {
                RESULT_COMIC_IS_EXIST
            }
        }
        return mResult
    }

    override fun onPostExecute(mResult: Int) {
        if (callback != null) {
            when (mResult) {
                RESULT_COMIC_IS_EXIST -> {
                    callback.onComicIsExist(mComic = mComic, comicList = comicList)
                }
                RESULT_ADD_COMIC_ERROR -> {
                    callback.onAddComicError()
                }
                RESULT_ADD_COMIC_SUCCESS -> {
                    callback.onAddComicSuccess(mComic = mComic, comicList = comicList)
                }
            }
        }
        super.onPostExecute(mResult)
    }

}
