package com.core.utilities

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Environment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.interfaces.*
import com.model.App
import com.model.GG
import okhttp3.*
import java.io.*
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class LStoreUtil {
    companion object {
        internal var TAG = LStoreUtil::class.java.simpleName

        var FOLDER_TRANSLATE = ".Loitp"
        var FILE_TRANSLATE_FAV_SENTENCE = "Loitp.txt"

        private val EXTENSION = ".txt"
        val FOLDER_TRUYENTRANHTUAN = "zloitpbestcomic"
        val FILE_NAME_MAIN_COMICS_LIST_HTML_CODE = "filenamemaincomicslisthtmlcode$EXTENSION"
        val FILE_NAME_MAIN_COMICS_LIST = "filenamemaincomicslist$EXTENSION"
        val FILE_NAME_MAIN_COMICS_LIST_FAVOURITE = "filenamemaincomicslistfavourite$EXTENSION"
        val FILE_NAME_TRUYENTRANHTUAN_DOWNLOADED_COMIC = "filenamedownloadedcomic$EXTENSION"

        val isSdPresent: Boolean
            get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        val randomColor: Int
            get() {
                val rnd = Random()
                return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            }

        //https://stackoverflow.com/questions/30703652/how-to-generate-light-and-pastel-colors-randomly-in-android
        // This is the base color which will be mixed with the generated one
        val randomColorLight: Int
            get() {
                val mRandom = Random(System.currentTimeMillis())
                val baseColor = Color.WHITE

                val baseRed = Color.red(baseColor)
                val baseGreen = Color.green(baseColor)
                val baseBlue = Color.blue(baseColor)

                val red = (baseRed + mRandom.nextInt(256)) / 2
                val green = (baseGreen + mRandom.nextInt(256)) / 2
                val blue = (baseBlue + mRandom.nextInt(256)) / 2

                return Color.rgb(red, green, blue)
            }

        val colors: IntArray
            get() = intArrayOf(Color.parseColor("#1BFFFF"), Color.parseColor("#2E3192"), Color.parseColor("#ED1E79"), Color.parseColor("#009E00"), Color.parseColor("#FBB03B"), Color.parseColor("#D4145A"), Color.parseColor("#3AA17E"), Color.parseColor("#00537E"))

        val texts: Array<String>
            get() = arrayOf("Relax, its only ONES and ZEROS !", "Hardware: The parts of a computer system that can be kicked.", "Computer dating is fine, if you're a computer.", "Better to be a geek than an idiot.", "If you don't want to be replaced by a computer, don't act like one.", "I'm not anti-social; I'm just not user friendly", "Those who can't write programs, write help files.", "The more I C, the less I see.  ")

        fun getFileNameComic(url: String): String {
            var u = url
            u = u.replace(oldValue = "/", newValue = "")
            u = u.replace(oldValue = ".", newValue = "")
            u = u.replace(oldValue = ":", newValue = "")
            u = u.replace(oldValue = "-", newValue = "")
            return u + EXTENSION
        }

        fun createFileImage(i: Int): String {
            return "p$i$EXTENSION"
        }

        @JvmOverloads
        fun getFolderPath(context: Context, mfolderName: String = "Loitp"): String {
            var folderPath = ""
            if (isSdPresent) {
                try {
                    //val sdPath = File(Environment.getExternalStorageDirectory().absolutePath + "/" + mfolderName)
                    ///storage/emulated/0/Loitpzloitpbestcomic/

                    val sdPath = File(context.getExternalFilesDir(null)?.absolutePath + "/" + mfolderName)
                    ///storage/emulated/0/Android/data/loitp.basemaster/files/Loitpzloitpbestcomic/

                    //val sdPath = File(context.getExternalFilesDir(null)?.path + "/" + mfolderName)
                    ///storage/emulated/0/Android/data/loitp.basemaster/files/Loitpzloitpbestcomic/

                    if (!sdPath.exists()) {
                        sdPath.mkdirs()
                        folderPath = sdPath.absolutePath
                    } else if (sdPath.exists()) {
                        folderPath = sdPath.absolutePath
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                //folderPath = Environment.getExternalStorageDirectory().path + "/" + mfolderName + "/"
            } else {
                try {
                    val cacheDir = File(context.cacheDir, "$mfolderName/")
                    if (!cacheDir.exists()) {
                        cacheDir.mkdirs()
                        folderPath = cacheDir.absolutePath
                    } else if (cacheDir.exists()) {
                        folderPath = cacheDir.absolutePath
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            //LLog.d(TAG, "isSdPresent: $isSdPresent, getFolderPath: $folderPath")
            return folderPath
        }

        /*fun checkSDCardFreeSize(): Int {
            val stat = StatFs(Environment.getExternalStorageDirectory().path)
            val bytesAvailable = stat.blockSize.toLong() * stat.availableBlocks.toLong()
            val megAvailable = bytesAvailable / (1024 * 1024)
            return megAvailable.toInt()
        }*/

        /*
        save tring json to sdcard
        ex: writeToFile("module.json", strJson);
         */
        fun writeToFile(context: Context, folder: String?, fileName: String, body: String): Boolean {
            val fos: FileOutputStream?
            try {
                var path = getFolderPath(context)
                if (folder != null) {
                    path = "$path$folder/"
                }
                //LLog.d(TAG, "writeToFile path: $path")
                val dir = File(path)
                val dirExist = dir.exists()
                //LLog.d(TAG, "writeToFile dirExist: $dirExist")
                if (!dirExist) {
                    if (!dir.mkdirs()) {
                        //LLog.d(TAG, "writeToFile could not create the directories")
                        return false
                    }
                }
                val myFile = File(dir, fileName)
                val myFileExist = myFile.exists()
                //LLog.d(TAG, "writeToFile myFileExist: $myFileExist")
                if (!myFileExist) {
                    val isSuccess = myFile.createNewFile()
                    LLog.d(TAG, "writeToFile isSuccess: $isSuccess")
                }
                fos = FileOutputStream(myFile)
                fos.write(body.toByteArray())
                fos.close()
                return true
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }
        }

        fun writeToFile(activity: Activity, folder: String, fileName: String, body: String, callbackWriteFile: CallbackWriteFile?) {
            //TODO convert to coroutine
            object : AsyncTask<Void, Void, Void>() {
                var isSuccess: Boolean = false

                override fun doInBackground(vararg params: Void): Void? {
                    isSuccess = writeToFile(activity, folder, fileName, body)
                    return null
                }

                override fun onPostExecute(aVoid: Void) {
                    super.onPostExecute(aVoid)
                    //LLog.d(TAG, "writeToFile onPostExecute isSuccess: $isSuccess")
                    callbackWriteFile?.onFinish(isSuccess)
                }
            }.execute()
        }

        /*
         * read text file from folder
         */
        fun readTxtFromFolder(context: Context, folderName: String?, fileName: String): String {
            val path = getFolderPath(context) + (if (folderName == null) "/" else "$folderName/") + fileName
            //LLog.d(TAG, "path: $path")
            val txtFile = File(path)
            val text = StringBuilder()
            try {
                val reader = BufferedReader(FileReader(txtFile))
                /*var line: String
                while ((line = reader.readLine()) != null) {
                    text.append(line + '\n')
                }*/
                var line: String? = null
                while ({ line = reader.readLine(); line }() != null) {
                    text.append(line + '\n')
                }
                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return text.toString()
        }

        fun readTxtFromFolder(activity: Activity, folderName: String, fileName: String, callbackReadFile: CallbackReadFile) {
            //TODO convert to rx
            object : AsyncTask<Void, Void, Void>() {
                var result = ""

                override fun doInBackground(vararg params: Void): Void? {
                    result = readTxtFromFolder(activity, folderName, fileName)
                    return null
                }

                override fun onPostExecute(aVoid: Void) {
                    super.onPostExecute(aVoid)
                    callbackReadFile.onFinish(result)
                }
            }.execute()
        }

        /*
         * read text file from folder in background
         */
        fun readTxtFromFolder(activity: Activity, folderName: String?, fileName: String, eventReadFromFolder: EventReadFromFolder) {
            //TODO convert to rx
            object : AsyncTask<Void, Void, Void>() {
                private var text: StringBuilder? = null
                private var runTaskSuccess = true

                override fun onPreExecute() {
                    LLog.d(TAG, "readTxtFromFolder onPreExecute")
                    super.onPreExecute()
                }

                override fun doInBackground(vararg params: Void): Void? {
                    val path = getFolderPath(activity) + (if (folderName == null)
                        "/"
                    else
                        "$folderName/") + fileName
                    LLog.d(TAG, "path: $path")
                    val txtFile = File(path)
                    text = StringBuilder()
                    try {
                        val reader = BufferedReader(FileReader(txtFile))
                        var line: String? = null
                        /*while ((line = reader.readLine()) != null) {
                            text?.append(line + '\n')
                        }*/
                        while ({ line = reader.readLine(); line }() != null) {
                            text?.append(line + '\n')
                        }
                        reader.close()
                    } catch (e: IOException) {
                        runTaskSuccess = false
                        LLog.d(TAG, "readTxtFromFolder===$e")
                        e.printStackTrace()
                    }

                    return null
                }

                override fun onPostExecute(aVoid: Void) {
                    if (runTaskSuccess) {
                        eventReadFromFolder.onSuccess(text?.toString())
                    } else {
                        eventReadFromFolder.onError()
                    }
                    super.onPostExecute(aVoid)
                }
            }.execute()
        }

        /*
         * read text file in raw folder
         */
        fun readTxtFromRawFolder(context: Context, nameOfRawFile: Int): String {
            val inputStream = context.resources.openRawResource(nameOfRawFile)
            val byteArrayOutputStream = ByteArrayOutputStream()
            var i: Int
            try {
                i = inputStream.read()
                while (i != -1) {
                    byteArrayOutputStream.write(i)
                    i = inputStream.read()
                }
                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return byteArrayOutputStream.toString()
        }

        fun readTxtFromRawFolder(context: Context, nameOfRawFile: Int, callbackReadFromRaw: CallbackReadFromRaw) {
            //TODO convert to rx
            object : AsyncTask<Void, Void, Void>() {
                var result: String? = null

                override fun doInBackground(vararg params: Void): Void? {
                    result = readTxtFromRawFolder(context, nameOfRawFile)
                    return null
                }

                override fun onPostExecute(aVoid: Void) {
                    super.onPostExecute(aVoid)
                    callbackReadFromRaw.onFinish(result)
                }
            }.execute()
        }

        fun saveHTMLCodeFromURLToSDCard(context: Context, link: String, folderName: String, fileName: String): Boolean {
            var state = false
            val ins: InputStream?
            try {
                val url = URL(link)
                ins = url.openStream()
                val br = BufferedReader(InputStreamReader(ins!!))
                var line: String? = null
                val stringBuilder = StringBuilder()
                /*while ((line = br.readLine()) != null) {
                    stringBuilder.append(line)
                }*/
                while ({ line = br.readLine(); line }() != null) {
                    stringBuilder.append(line)
                }
                br.close()
                ins.close()
                //LLog.d(TAG, "writeToFile saveHTMLCodeFromURLToSDCard success: " + stringBuilder.toString())
                writeToFile(context, folderName, fileName, stringBuilder.toString())
                state = true
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return state
        }

        /*
         * get random quote
         */
        fun readTxtFromAsset(context: Context, assetFile: String): String {
            val ins: InputStream
            var str = ""
            try {
                ins = context.assets.open(assetFile)
                val buffer = ByteArray(ins.available())
                ins.read(buffer)
                ins.close()
                str = String(buffer)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return str
        }

        /*
         * get random number
         */
        fun getRandomNumber(length: Int): Int {
            val r = Random()
            return r.nextInt(length)
        }

        fun getPathOfFileNameMainComicsListHTMLCode(context: Context): String {
            return getFolderPath(context) + FOLDER_TRUYENTRANHTUAN + "/" + FILE_NAME_MAIN_COMICS_LIST_HTML_CODE
        }

        fun getFileFromAssets(context: Context?, fileName: String): File? {
            if (context == null) {
                return null
            }
            val file = File(context.cacheDir.toString() + "/" + fileName)
            if (!file.exists())
                try {
                    val ins = context.assets.open(fileName)
                    val size = ins.available()
                    val buffer = ByteArray(size)
                    ins.read(buffer)
                    ins.close()
                    val fos = FileOutputStream(file)
                    fos.write(buffer)
                    fos.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }

            return file
        }

        fun getListEpubFiles(parentDir: File): List<File> {
            val inFiles = ArrayList<File>()
            val files = parentDir.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isDirectory) {
                        inFiles.addAll(getListEpubFiles(file))
                    } else {
                        if (file.name.endsWith(".epub")) {
                            inFiles.add(file)
                        }
                    }
                }
            }
            return inFiles
        }

        fun getSettingFromGGDrive(context: Context?, linkGGDriveSetting: String?, ggSettingCallback: GGSettingCallback?) {
            if (context == null || linkGGDriveSetting == null || linkGGDriveSetting.isEmpty()) {
                return
            }
//            LLog.d(TAG, "getSettingFromGGDrive")
            val request = Request.Builder().url(linkGGDriveSetting).build()
            val okHttpClient = OkHttpClient()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    LLog.d(TAG, "getSettingFromGGDrive onFailure $e")
                    ggSettingCallback?.onGGFailure(call, e)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body() ?: return
                        val json = responseBody.string()
//                        LLog.d(TAG, "getSettingFromGGDrive onResponse isSuccessful $json")
                        val app = Gson().fromJson(json, App::class.java)
                        if (app == null) {
                            ggSettingCallback?.onGGResponse(app = null, isNeedToShowMsg = false)
                        } else {
                            val localMsg = LPrefUtil.getGGAppMsg(context)
                            val serverMsg = app.config?.msg
                            LPrefUtil.setGGAppMsg(context, serverMsg)
                            val isNeedToShowMsg: Boolean
                            if (serverMsg.isNullOrEmpty()) {
                                isNeedToShowMsg = false
                            } else {
                                isNeedToShowMsg = !localMsg?.trim { it <= ' ' }.equals(serverMsg, ignoreCase = true)
                            }
                            ggSettingCallback?.onGGResponse(app, isNeedToShowMsg)
                        }

                    } else {
//                        LLog.d(TAG, "getSettingFromGGDriveonResponse !isSuccessful: $response")
                        ggSettingCallback?.onGGResponse(app = null, isNeedToShowMsg = false)
                    }
                }
            })
        }

        fun getTextFromGGDrive(context: Context?, linkGGDrive: String?, ggCallback: GGCallback?) {
            if (context == null || linkGGDrive == null || linkGGDrive.isEmpty()) {
                return
            }
//            LLog.d(TAG, "getTextFromGGDrive")
            val request = Request.Builder().url(linkGGDrive).build()
            val okHttpClient = OkHttpClient()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    LLog.d(TAG, "getTextFromGGDrive onFailure $e")
                    ggCallback?.onGGFailure(call = call, e = e)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val json = responseBody?.string()
                        if (json.isNullOrEmpty()) {
                            ggCallback?.onGGFailure(call = call, e = NullPointerException("responseBody isNullOrEmpty"))
                        } else {
//                            LLog.d(TAG, "getTextFromGGDrive onResponse isSuccessful $json")
                            val g = Gson()
                            val listGG: ArrayList<GG> = g.fromJson(json, object : TypeToken<List<GG?>?>() {}.type)
//                            LLog.d(TAG, "getTextFromGGDrive listGG " + g.toJson(listGG))
                            ggCallback?.onGGResponse(listGG = listGG)
                        }
                    } else {
//                        LLog.d(TAG, "getTextFromGGDrive !isSuccessful: $response")
                        ggCallback?.onGGFailure(call = call, e = NullPointerException("responseBody !isSuccessful"))
                    }
                }
            })
        }
    }
}
