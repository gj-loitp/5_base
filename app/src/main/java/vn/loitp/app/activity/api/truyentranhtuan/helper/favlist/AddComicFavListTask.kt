package vn.loitp.app.activity.api.truyentranhtuan.helper.favlist

import android.content.Context
import android.os.AsyncTask
import com.core.utilities.LStoreUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import vn.loitp.app.activity.api.truyentranhtuan.helper.ComicUtils.Companion.isComicExistIn
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic
import java.util.*

//TODO convert croutine
class AddComicFavListTask(private val context: Context,
                          private val mComic: Comic,
                          private val callback: Callback?)
    : AsyncTask<Void, Void, Int>() {

    private val TAG = javaClass.simpleName
    private var comicList = ArrayList<Comic>()
    private val RESULT_ADD_COMIC_SUCCESS = 69
    private val RESULT_COMIC_IS_EXIST = 96
    private val RESULT_ADD_COMIC_ERROR = 66

    interface Callback {
        fun onAddComicSuccess(mComic: Comic, comicList: List<Comic>)
        fun onComicIsExist(mComic: Comic, comicList: List<Comic>)
        fun onAddComicError()
    }

    override fun doInBackground(vararg voids: Void): Int {
        val json = LStoreUtil.readTxtFromFolder(context = context, folderName = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE)
        val gson = Gson()
        var mResult = 0

        if (json.isEmpty()) {
            comicList.add(mComic)
            val newJson = gson.toJson(comicList)
            val isSaved = LStoreUtil.writeToFile(context = context, folder = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE, body = newJson)
            mResult = if (isSaved) {
                RESULT_ADD_COMIC_SUCCESS
            } else {
                RESULT_ADD_COMIC_ERROR
            }
        } else {
            comicList = gson.fromJson(json, object : TypeToken<List<Comic?>?>() {}.type)
            val isExist = isComicExistIn(comic = mComic, comicList = comicList)
            mResult = if (!isExist) {
                comicList.add(mComic)
                val newJson = gson.toJson(comicList)
                val isSaved = LStoreUtil.writeToFile(context = context, folder = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE, body = newJson)
                if (isSaved) {
                    RESULT_ADD_COMIC_SUCCESS
                } else {
                    RESULT_ADD_COMIC_ERROR
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
