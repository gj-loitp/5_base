package com.loitp.core.utilities

import android.app.ActivityManager
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import com.loitp.core.base.BaseApplication
import com.loitpcore.model.App
import com.loitpcore.model.data.Pkg
import okhttp3.*
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
// https://gist.github.com/lopspower/76421751b21594c69eb2
// https://github.com/lopspower/BestAndroidGists
class LStoreUtil {
    companion object {
        internal var logTag = LStoreUtil::class.java.simpleName

        private fun log(msg: String) {
            LLog.d(logTag, msg)
        }

//        const val FOLDER_TRANSLATE = ".Loitp"
//        const val FILE_TRANSLATE_FAV_SENTENCE = "Loitp.txt"
//        private const val EXTENSION = ".txt"
//        const val FOLDER_TRUYENTRANHTUAN = "zloitpbestcomic"
//        const val FILE_NAME_MAIN_COMICS_LIST_HTML_CODE = "filenamemaincomicslisthtmlcode$EXTENSION"
//        const val FILE_NAME_MAIN_COMICS_LIST = "filenamemaincomicslist$EXTENSION"
//        const val FILE_NAME_MAIN_COMICS_LIST_FAVOURITE = "filenamemaincomicslistfavourite$EXTENSION"
//        const val FILE_NAME_TRUYENTRANHTUAN_DOWNLOADED_COMIC = "filenamedownloadedcomic$EXTENSION"

        val isSdPresent: Boolean
            get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        val randomColor: Int
            get() {
                val rnd = Random()
                return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            }

        // https://stackoverflow.com/questions/30703652/how-to-generate-light-and-pastel-colors-randomly-in-android
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
            get() = intArrayOf(
                Color.parseColor("#1BFFFF"),
                Color.parseColor("#2E3192"),
                Color.parseColor("#ED1E79"),
                Color.parseColor("#009E00"),
                Color.parseColor("#FBB03B"),
                Color.parseColor("#D4145A"),
                Color.parseColor("#3AA17E"),
                Color.parseColor("#00537E")
            )

        val texts: Array<String>
            get() = arrayOf(
                "Relax, its only ONES and ZEROS !",
                "Hardware: The parts of a computer system that can be kicked.",
                "Computer dating is fine, if you're a computer.",
                "Better to be a geek than an idiot.",
                "If you don't want to be replaced by a computer, don't act like one.",
                "I'm not anti-social; I'm just not user friendly",
                "Those who can't write programs, write help files.",
                "The more I C, the less I see.  "
            )

        // dung de bao hieu cho gallery load lai photo vi co anh moi
//        fun sendBroadcastMediaScan(
//            file: File? = null
//        ) {
//            file?.let {
////                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
////                val contentUri = Uri.fromFile(file)
////                mediaScanIntent.data = contentUri
////                LAppResource.application.sendBroadcast(mediaScanIntent)
//
//                MediaScannerConnection.scanFile(
//                    LAppResource.application,
//                    arrayOf(file.toString()),
//                    arrayOf(it.name),
//                    null
//                )
//            }
//        }

        @JvmOverloads
        fun getFolderPath(
            folderName: String = "loitp"
        ): String {
            var folderPath = ""
            if (isSdPresent) {
                try {
//                    C1
//                    val file = File(Environment.getExternalStorageDirectory().absolutePath + "/" + folderName)
// //                        ex: /storage/emulated/0/ZZZTestDownloader

//                    C2
//                    val file =
//                        File(LAppResource.application.getExternalFilesDir(null)?.absolutePath + "/" + folderName)
//                    ex: /storage/emulated/0/Android/data/loitp.basemaster/files/ZZZTestDownloader

//                    C3
                    val path =
                        LAppResource.application.getExternalFilesDir(null)?.parent?.split("/Android")
                            ?.get(0)
                            ?: ""
                    val file = File("$path/$folderName")

                    log("file path ${file.path}")
                    log("file exists " + file.exists())

                    folderPath = if (file.exists()) {
                        file.absolutePath
                    } else {
                        file.mkdirs()
                        file.absolutePath
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    log("err isSdPresent $e")
                }
            } else {
                try {
                    val cacheDir = File(LAppResource.application.cacheDir, "$folderName/")
//                    /data/user/0/loitp.basemaster/cache/Pictures/ZZZTestDownloader
                    if (!cacheDir.exists()) {
                        cacheDir.mkdirs()
                        folderPath = cacheDir.absolutePath
                    } else if (cacheDir.exists()) {
                        folderPath = cacheDir.absolutePath
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    log("err !isSdPresent $e")
                }
            }
            log("return getFolderPath folderPath $folderPath")
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
                    log("isSuccess $isSuccess")
                }
                fos = FileOutputStream(myFile)
                fos.write(body.toByteArray())
                fos.close()
                log("<<<writeToFile myFile path: " + myFile.path)
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
            val path =
                getFolderPath() + (if (folderName == null) "/" else "/$folderName/") + fileName
            val txtFile = File(path)
            log("readTxtFromFolder txtFile ${txtFile.path}")
            val text = StringBuilder()
            try {
                val reader = BufferedReader(FileReader(txtFile))
                var line: String?
                while (run {
                        line = reader.readLine()
                        line
                    } != null) {
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
            onGGResponse: ((app: App?) -> Unit)? = null
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
                        response.body?.let { responseBody ->
                            val json = responseBody.string()
                            val app = BaseApplication.gson.fromJson(json, App::class.java)
                            onGGResponse?.invoke(app)
                        }
                    } else {
                        onGGResponse?.invoke(null)
                    }
                }
            })
        }

        fun getPkgFromGGDrive(
            linkGGDriveSetting: String? = null,
            onGGFailure: ((call: Call, e: IOException) -> Unit)? = null,
            onGGResponse: ((pkg: Pkg?) -> Unit)? = null
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
                        response.body?.let { responseBody ->
                            val json = responseBody.string()
                            val pkg = BaseApplication.gson.fromJson(json, Pkg::class.java)
                            onGGResponse?.invoke(pkg)
                        }
                    } else {
                        onGGResponse?.invoke(null)
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
            val freeBytesExternal =
                File(LAppResource.application.getExternalFilesDir(null).toString()).freeSpace
            val freeMb = (freeBytesExternal / (1024 * 1024)).toInt()
//            val totalSize = File(context.getExternalFilesDir(null).toString()).totalSpace
//            val totalMb = (totalSize / (1024 * 1024)).toInt()
            log("freeMb $freeMb")
            return freeMb
        }

        fun getAvailableRAM(): Long {
            val memoryInfo = ActivityManager.MemoryInfo()
            val activityManager =
                LAppResource.application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.getMemoryInfo(memoryInfo)
            val availableMegs = memoryInfo.availMem / 1048576L
            val percentAvail = memoryInfo.availMem / memoryInfo.totalMem
            log("percentAvail $percentAvail")
            return availableMegs
        }

        // return destination file path
        @Suppress("unused")
        fun unzip(
            file: File
        ): String? {
            try {
                val filePath = file.path
                val destination = "${file.parent}/"
                log(">>>unzip filePath $filePath")
                log(">>>unzip destination $destination")
                val inputStream = FileInputStream(filePath)
                val zipStream = ZipInputStream(inputStream)
                var zipEntry: ZipEntry?
                while (zipStream.nextEntry.also {
                        zipEntry = it
                    } != null
                ) {
                    log("Unzipping " + zipEntry?.name + " at " + destination)
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
                log("Unzipping complete, path :  $destination")
                return destination
            } catch (e: java.lang.Exception) {
                log("Unzipping failed $e")
                e.printStackTrace()
                return null
            }
        }

        private fun handleDirectory(
            dir: String,
            destination: String
        ) {
            val file = File(destination + dir)
            log("handleDirectory file ${file.path}")
            if (!file.isDirectory) {
                log("handleDirectory !file.isDirectory")
                val isSuccess = file.mkdirs()
                log("handleDirectory !file.isDirectory isSuccess $isSuccess")
            } else {
                log("handleDirectory file.isDirectory")
            }
        }

        fun getRealPathFromURI(
            context: Context?,
            uri: Uri?
        ): String? {
            if (context == null || uri == null) {
                return null
            }
            when {
                // DocumentProvider
                DocumentsContract.isDocumentUri(context, uri) -> {
                    when {
                        // ExternalStorageProvider
                        isExternalStorageDocument(uri) -> {
                            val docId = DocumentsContract.getDocumentId(uri)
                            val split = docId.split(":").toTypedArray()
                            val type = split[0]
                            // This is for checking Main Memory
                            return if ("primary".equals(type, ignoreCase = true)) {
                                if (split.size > 1) {
                                    Environment.getExternalStorageDirectory()
                                        .toString() + "/" + split[1]
                                } else {
                                    Environment.getExternalStorageDirectory().toString() + "/"
                                }
                                // This is for checking SD Card
                            } else {
                                "storage" + "/" + docId.replace(":", "/")
                            }
                        }
                        isDownloadsDocument(uri) -> {
                            val fileName = getFilePath(context, uri)
                            if (fileName != null) {
                                return Environment.getExternalStorageDirectory()
                                    .toString() + "/Download/" + fileName
                            }
                            var id = DocumentsContract.getDocumentId(uri)
                            if (id.startsWith("raw:")) {
                                id = id.replaceFirst("raw:".toRegex(), "")
                                val file = File(id)
                                if (file.exists()) return id
                            }
                            val contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"),
                                java.lang.Long.valueOf(id)
                            )
                            return getDataColumn(context, contentUri, null, null)
                        }
                        isMediaDocument(uri) -> {
                            val docId = DocumentsContract.getDocumentId(uri)
                            val split = docId.split(":").toTypedArray()
                            val type = split[0]
                            var contentUri: Uri? = null
                            when (type) {
                                "image" -> {
                                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                }
                                "video" -> {
                                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                                }
                                "audio" -> {
                                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                                }
                            }
                            val selection = "_id=?"
                            val selectionArgs = arrayOf(split[1])
                            return getDataColumn(context, contentUri, selection, selectionArgs)
                        }
                    }
                }
                "content".equals(uri.scheme, ignoreCase = true) -> {
                    // Return the remote address
                    return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                        context,
                        uri,
                        null,
                        null
                    )
                }
                "file".equals(uri.scheme, ignoreCase = true) -> {
                    return uri.path
                }
            }
            return null
        }

        private fun getDataColumn(
            context: Context,
            uri: Uri?,
            selection: String?,
            selectionArgs: Array<String>?
        ): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(
                column
            )
            try {
                if (uri == null) return null
                cursor = context.contentResolver.query(
                    uri, projection, selection, selectionArgs,
                    null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        private fun getFilePath(
            context: Context,
            uri: Uri?
        ): String? {
            var cursor: Cursor? = null
            val projection = arrayOf(
                MediaStore.MediaColumns.DISPLAY_NAME
            )
            try {
                if (uri == null) return null
                cursor = context.contentResolver.query(
                    uri, projection, null, null,
                    null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                    return cursor.getString(index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        private fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri.authority
        }
    }
}
