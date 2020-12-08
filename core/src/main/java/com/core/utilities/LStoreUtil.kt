package com.core.utilities

import alirezat775.lib.downloader.Downloader
import alirezat775.lib.downloader.core.OnDownloadListener
import android.app.ActivityManager
import android.content.Context
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Environment
import com.core.base.BaseApplication
import com.google.gson.reflect.TypeToken
import com.model.App
import com.model.GG
import okhttp3.*
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.collections.ArrayList

//https://gist.github.com/lopspower/76421751b21594c69eb2
//https://github.com/lopspower/BestAndroidGists

class LStoreUtil {
    companion object {
        internal var logTag = LStoreUtil::class.java.simpleName

        const val FOLDER_TRANSLATE = ".Loitp"
        const val FILE_TRANSLATE_FAV_SENTENCE = "Loitp.txt"
        private const val EXTENSION = ".txt"
        const val FOLDER_TRUYENTRANHTUAN = "zloitpbestcomic"
        const val FILE_NAME_MAIN_COMICS_LIST_HTML_CODE = "filenamemaincomicslisthtmlcode$EXTENSION"
        const val FILE_NAME_MAIN_COMICS_LIST = "filenamemaincomicslist$EXTENSION"
        const val FILE_NAME_MAIN_COMICS_LIST_FAVOURITE = "filenamemaincomicslistfavourite$EXTENSION"
        const val FILE_NAME_TRUYENTRANHTUAN_DOWNLOADED_COMIC = "filenamedownloadedcomic$EXTENSION"

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

        fun getFileNameComic(
                url: String
        ): String {
            var u = url
            u = u.replace(oldValue = "/", newValue = "")
            u = u.replace(oldValue = ".", newValue = "")
            u = u.replace(oldValue = ":", newValue = "")
            u = u.replace(oldValue = "-", newValue = "")
            return u + EXTENSION
        }

        fun createFileImage(
                i: Int
        ): String {
            return "p$i$EXTENSION"
        }

        //dung de bao hieu cho gallery load lai photo vi co anh moi
        fun sendBroadcastMediaScan(
                file: File? = null
        ) {
            file?.let {
//                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
//                val contentUri = Uri.fromFile(file)
//                mediaScanIntent.data = contentUri
//                LAppResource.application.sendBroadcast(mediaScanIntent)

                MediaScannerConnection.scanFile(LAppResource.application,
                        arrayOf(file.toString()),
                        arrayOf(it.name),
                        null)
            }
        }

        @JvmOverloads
        fun getFolderPath(
                folderName: String = "z1000"
        ): String {
            var folderPath = ""
            if (isSdPresent) {
                try {
//                    C1
//                    val file = File(Environment.getExternalStorageDirectory().absolutePath + "/" + folderName)
//                        ex: /storage/emulated/0/ZZZTestDownloader

//                    C2
//                    val file = File(LAppResource.application.getExternalFilesDir(null)?.absolutePath + "/" + folderName)
//                    ex: /storage/emulated/0/Android/data/loitp.basemaster/files/ZZZTestDownloader

//                    C3
                    val path = LAppResource.application.getExternalFilesDir(null)?.parent?.split("/Andro")?.get(0)
                            ?: ""
                    val file = File("$path/$folderName")
                    LLog.d(logTag, "file ${file.path}")
                    LLog.d(logTag, "file exists " + file.exists())

                    folderPath = if (file.exists()) {
                        file.absolutePath
                    } else {
                        file.mkdirs()
                        file.absolutePath
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    LLog.e(logTag, "err isSdPresent $e")
                }
            } else {
                try {
                    val cacheDir = File(LAppResource.application.cacheDir, "$folderName/")
                    if (!cacheDir.exists()) {
                        cacheDir.mkdirs()
                        folderPath = cacheDir.absolutePath
                    } else if (cacheDir.exists()) {
                        folderPath = cacheDir.absolutePath
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    LLog.e(logTag, "err !isSdPresent $e")
                }

            }
            LLog.d(logTag, "return getFolderPath folderPath $folderPath")
            return folderPath
        }

        /*
        save string json to sdcard
         */
        fun writeToFile(
                folder: String?,
                fileName: String,
                body: String
        ): File? {
            val fos: FileOutputStream?
            try {
                var path = getFolderPath()
                if (folder != null) {
                    path = "$path/$folder/"
                }
                val dir = File(path)
                val dirExist = dir.exists()
                if (!dirExist) {
                    if (!dir.mkdirs()) {
                        return null
                    }
                }
                val myFile = File(dir, fileName)
                val myFileExist = myFile.exists()
                if (!myFileExist) {
                    val isSuccess = myFile.createNewFile()
                }
                fos = FileOutputStream(myFile)
                fos.write(body.toByteArray())
                fos.close()
                LLog.d(logTag, "<<<writeToFile myFile path: " + myFile.path)
                return myFile
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }

        /*
         * read text file from folder
         */
        fun readTxtFromFolder(
                folderName: String?,
                fileName: String
        ): String {
            val path = getFolderPath() + (if (folderName == null) "/" else "/$folderName/") + fileName
            val txtFile = File(path)
            LLog.d(logTag, "readTxtFromFolder txtFile ${txtFile.path}")
            val text = StringBuilder()
            try {
                val reader = BufferedReader(FileReader(txtFile))
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

        /*
         * read text file in raw folder
         */
        fun readTxtFromRawFolder(
                nameOfRawFile: Int
        ): String {
            val inputStream = LAppResource.application.resources.openRawResource(nameOfRawFile)
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

        fun readTxtFromAsset(
                assetFile: String
        ): String {
            val ins: InputStream
            var str = ""
            try {
                ins = LAppResource.application.assets.open(assetFile)
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
        fun getRandomNumber(
                length: Int
        ): Int {
            val r = Random()
            return r.nextInt(length)
        }

        fun getPathOfFileNameMainComicsListHTMLCode(): String {
            return getFolderPath() + FOLDER_TRUYENTRANHTUAN + "/" + FILE_NAME_MAIN_COMICS_LIST_HTML_CODE
        }

        fun getFileFromAssets(
                fileName: String
        ): File? {

            val file = File(LAppResource.application.cacheDir.toString() + "/" + fileName)
            if (!file.exists())
                try {
                    val ins = LAppResource.application.assets.open(fileName)
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

        fun getListEpubFiles(
                parentDir: File
        ): ArrayList<File> {
            val listFile = ArrayList<File>()
            val files = parentDir.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isDirectory) {
                        listFile.addAll(getListEpubFiles(file))
                    } else {
                        if (file.name.endsWith(".epub")) {
                            listFile.add(file)
                        }
                    }
                }
            }
            return listFile
        }

        fun getSettingFromGGDrive(
                linkGGDriveSetting: String? = null,
                onGGFailure: ((call: Call, e: IOException) -> Unit)? = null,
                onGGResponse: ((app: App?, isNeedToShowMsg: Boolean) -> Unit)? = null
        ) {
            if (linkGGDriveSetting == null || linkGGDriveSetting.isEmpty()) {
                return
            }
            val request = Request.Builder().url(linkGGDriveSetting).build()
            val okHttpClient = OkHttpClient()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onGGFailure?.invoke(call, e)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body ?: return
                        val json = responseBody.string()
                        val app = BaseApplication.gson.fromJson(json, App::class.java)
                        if (app == null) {
                            onGGResponse?.invoke(null, false)
                        } else {
                            val localMsg = LPrefUtil.getGGAppMsg()
                            val serverMsg = app.config?.msg
                            LPrefUtil.setGGAppMsg(serverMsg)
                            val isNeedToShowMsg: Boolean
                            isNeedToShowMsg = if (serverMsg.isNullOrEmpty()) {
                                false
                            } else {
                                !localMsg?.trim { it <= ' ' }.equals(serverMsg, ignoreCase = true)
                            }
                            onGGResponse?.invoke(app, isNeedToShowMsg)
                        }

                    } else {
                        onGGResponse?.invoke(null, false)
                    }
                }
            })
        }

        fun getTextFromGGDrive(
                linkGGDrive: String? = null,
                onGGFailure: ((call: Call, e: Exception) -> Unit)? = null,
                onGGResponse: ((listGG: ArrayList<GG>) -> Unit)? = null
        ) {
            if (linkGGDrive.isNullOrEmpty()) {
                return
            }
            val request = Request.Builder().url(linkGGDrive).build()
            val okHttpClient = OkHttpClient()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onGGFailure?.invoke(call, e)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body
                        val json = responseBody?.string()
                        if (json.isNullOrEmpty()) {
                            onGGFailure?.invoke(call, NullPointerException("responseBody isNullOrEmpty"))
                        } else {
                            val listGG: ArrayList<GG> = BaseApplication.gson.fromJson(json, object : TypeToken<List<GG?>?>() {}.type)
                            onGGResponse?.invoke(listGG)
                        }
                    } else {
                        onGGFailure?.invoke(call, NullPointerException("responseBody !isSuccessful"))
                    }
                }
            })
        }

        @Suppress("INTEGER_OVERFLOW")
        fun getSize(
                size: Int
        ): String {
            var s = ""
            val kb = (size / 1024).toDouble()
            val mb = kb / 1024
            val gb = kb / 1024
            val tb = kb / 1024
            if (size < 1024) {
                s = "$size Bytes"
            } else if (size >= 1024 && size < 1024 * 1024) {
                s = String.format("%.2f", kb) + " KB"
            } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
                s = String.format("%.2f", mb) + " MB"
            } else if (size >= 1024 * 1024 * 1024 && size < 1024 * 1024 * 1024 * 1024) {
                s = String.format("%.2f", gb) + " GB"
            } else if (size >= 1024 * 1024 * 1024 * 1024) {
                s = String.format("%.2f", tb) + " TB"
            }
            return s
        }

        fun getAvailableSpaceInMb(): Int {
            val freeBytesExternal = File(LAppResource.application.getExternalFilesDir(null).toString()).freeSpace
            val freeMb = (freeBytesExternal / (1024 * 1024)).toInt()
//            val totalSize = File(context.getExternalFilesDir(null).toString()).totalSpace
//            val totalMb = (totalSize / (1024 * 1024)).toInt()
            return freeMb
        }

        fun getAvailableRAM(): Long {
            val memoryInfo = ActivityManager.MemoryInfo()
            val activityManager = LAppResource.application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.getMemoryInfo(memoryInfo)
            val availableMegs = memoryInfo.availMem / 1048576L
            val percentAvail = memoryInfo.availMem / memoryInfo.totalMem
//            Log.d(logTag, "percentAvail $percentAvail")
            return availableMegs
        }

        fun getDownloader(
                folderName: String? = null,
                token: String? = null,
                url: String,
                onDownloadListener: OnDownloadListener
        ): Downloader {
            if (folderName.isNullOrEmpty()) {
                val map = HashMap<String, String>()
                token?.let {
                    map["Authorization"] = it
                }
                return Downloader.Builder(mContext = LAppResource.application, mUrl = url)
                        .header(map)
                        .downloadListener(onDownloadListener)
                        .build()
            } else {
                val path = getFolderPath(folderName = folderName)
                val map = HashMap<String, String>()
                token?.let {
                    map["Authorization"] = it
                }
                return Downloader.Builder(mContext = LAppResource.application, mUrl = url)
                        .downloadDirectory(path)
                        .header(map)
                        .downloadListener(onDownloadListener)
                        .build()
            }
        }

        //return destination file path
        fun unzip(
                file: File
        ): String? {
            try {
                val filePath = file.path
                val destination = "${file.parent}/"
//                LLog.d(logTag, ">>>unzip filePath $filePath")
//                LLog.d(logTag, ">>>unzip destination $destination")
                val inputStream = FileInputStream(filePath)
                val zipStream = ZipInputStream(inputStream)
                var zipEntry: ZipEntry?
                while (zipStream.nextEntry.also {
                            zipEntry = it
                        } != null) {
//                    LLog.d(logTag, "Unzipping " + zipEntry?.name + " at " + destination)
                    zipEntry?.let { ze ->
                        if (ze.isDirectory) {
                            handleDirectory(dir = ze.name, destination = destination)
                        } else {
                            val fileOutputStream = FileOutputStream(destination + "/" + ze.name)
                            val bufferedOutputStream = BufferedOutputStream(fileOutputStream)
                            val buffer = ByteArray(1024)
                            var read: Int
                            while (zipStream.read(buffer).also { read = it } != -1) {
                                bufferedOutputStream.write(buffer, 0, read)
                            }
                            zipStream.closeEntry()
                            bufferedOutputStream.close()
                            fileOutputStream.close()
                        }
                    }
                }
                zipStream.close()
//                LLog.d(logTag, "Unzipping complete, path :  $destination")
                return destination
            } catch (e: java.lang.Exception) {
//                LLog.e(logTag, "Unzipping failed $e")
                e.printStackTrace()
                return null
            }
        }

        private fun handleDirectory(
                dir: String,
                destination: String
        ) {
            val file = File(destination + dir)
//            LLog.d(logTag, "handleDirectory file ${file.path}")
            if (!file.isDirectory) {
//                LLog.d(logTag, "handleDirectory !file.isDirectory")
                val isSuccess = file.mkdirs()
//                LLog.d(logTag, "handleDirectory !file.isDirectory isSuccess $isSuccess")
            } else {
//                LLog.d(logTag, "handleDirectory file.isDirectory")
            }
        }
    }
}
