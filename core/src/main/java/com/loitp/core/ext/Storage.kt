package com.loitp.core.ext

import android.app.Activity
import android.app.ActivityManager
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import com.loitp.R
import com.loitp.core.base.BaseApplication
import com.loitp.func.epub.core.EpubReaderReadActivity
import com.loitp.func.epub.model.BookInfo
import com.loitp.func.epub.model.BookInfoData
import com.loitp.model.App
import com.loitp.model.Pkg
import okhttp3.*
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Created by Loitp on 08,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
val isSdPresent: Boolean
    get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

@JvmOverloads
fun Context.getFolderPath(
    folderName: String = "loitp"
): String {
    val logTag = "getFolderPath"
    var folderPath = ""
    if (isSdPresent) {
        try {
//                    C1
//                    val file = File(Environment.getExternalStorageDirectory().absolutePath + "/" + folderName)
// //                        ex: /storage/emulated/0/ZZZTestDownloader

//                    C2
//                    val file =
//                        File(this.getExternalFilesDir(null)?.absolutePath + "/" + folderName)
//                    ex: /storage/emulated/0/Android/data/loitp.basemaster/files/ZZZTestDownloader

//                    C3
            val path = this.getExternalFilesDir(null)?.parent?.split("/Android")
                ?.get(0)
                ?: ""
            val file = File("$path/$folderName")

            d(logTag, "file path ${file.path}")
            d(logTag, "file exists " + file.exists())

            folderPath = if (file.exists()) {
                file.absolutePath
            } else {
                file.mkdirs()
                file.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
            d(logTag, "err isSdPresent $e")
        }
    } else {
        try {
            val cacheDir = File(this.cacheDir, "$folderName/")
//                    /data/user/0/loitp.basemaster/cache/Pictures/ZZZTestDownloader
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
                folderPath = cacheDir.absolutePath
            } else if (cacheDir.exists()) {
                folderPath = cacheDir.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
            d(logTag, "err !isSdPresent $e")
        }
    }
    d(logTag, "return getFolderPath folderPath $folderPath")
    return folderPath
}

/*
        save string json to sdcard
         */
fun Context.writeToFile(
    folder: String?,
    fileName: String,
    body: String
): File? {
    val logTag = "writeToFile"
    val fos: FileOutputStream?
    try {
        var path = this.getFolderPath()
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
            d(logTag, "isSuccess $isSuccess")
        }
        fos = FileOutputStream(myFile)
        fos.write(body.toByteArray())
        fos.close()
        d(logTag, "<<<writeToFile myFile path: " + myFile.path)
        return myFile
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}

//read text file from folder
fun Context.readTxtFromFolder(
    folderName: String?,
    fileName: String
): String {
    val logTag = "readTxtFromFolder"
    val path =
        this.getFolderPath() + (if (folderName == null) "/" else "/$folderName/") + fileName
    val txtFile = File(path)
    d(logTag, "readTxtFromFolder txtFile ${txtFile.path}")
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
fun Context.readTxtFromRawFolder(
    nameOfRawFile: Int
): String {
    val inputStream = this.resources.openRawResource(nameOfRawFile)
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

fun Context.readTxtFromAsset(
    assetFile: String
): String {
    val ins: InputStream
    var str = ""
    try {
        ins = this.assets.open(assetFile)
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

fun Context.getFileFromAssets(
    fileName: String
): File? {

    val file = File(this.cacheDir.toString() + "/" + fileName)
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

fun Context.getAvailableSpaceInMb(): Int {
    val logTag = "getAvailableSpaceInMb"
    val freeBytesExternal =
        File(this.getExternalFilesDir(null).toString()).freeSpace
    val freeMb = (freeBytesExternal / (1024 * 1024)).toInt()
//            val totalSize = File(context.getExternalFilesDir(null).toString()).totalSpace
//            val totalMb = (totalSize / (1024 * 1024)).toInt()
    d(logTag, "freeMb $freeMb")
    return freeMb
}

fun Context.getAvailableRAM(): Long {
    val memoryInfo = ActivityManager.MemoryInfo()
    val activityManager =
        this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    activityManager.getMemoryInfo(memoryInfo)
    val availableMegs = memoryInfo.availMem / 1048576L
    val percentAvail = memoryInfo.availMem / memoryInfo.totalMem
    return availableMegs
}

// return destination file path
@Suppress("unused")
fun unzip(
    file: File
): String? {
    val logTag = "unzip"
    try {
        val filePath = file.path
        val destination = "${file.parent}/"
        d(logTag, ">>>unzip filePath $filePath")
        d(logTag, ">>>unzip destination $destination")
        val inputStream = FileInputStream(filePath)
        val zipStream = ZipInputStream(inputStream)
        var zipEntry: ZipEntry?
        while (zipStream.nextEntry.also {
                zipEntry = it
            } != null
        ) {
            d(logTag, "Unzipping " + zipEntry?.name + " at " + destination)
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
        d(logTag, "Unzipping complete, path :  $destination")
        return destination
    } catch (e: java.lang.Exception) {
        d(logTag, "Unzipping failed $e")
        e.printStackTrace()
        return null
    }
}

private fun handleDirectory(
    dir: String,
    destination: String
) {
    val logTag = "handleDirectory"
    val file = File(destination + dir)
    if (!file.isDirectory) {
        d(logTag, "handleDirectory !file.isDirectory")
        val isSuccess = file.mkdirs()
        d(logTag, "handleDirectory !file.isDirectory isSuccess $isSuccess")
    } else {
        d(logTag, "handleDirectory file.isDirectory")
    }
}

fun Context.getRealPathFromURI(
    uri: Uri?
): String? {
    if (uri == null) {
        return null
    }
    when {
        // DocumentProvider
        DocumentsContract.isDocumentUri(this, uri) -> {
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
                    val fileName = this.getFilePath(uri)
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
                    return this.getDataColumn(contentUri, null, null)
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
                    return this.getDataColumn(contentUri, selection, selectionArgs)
                }
            }
        }
        "content".equals(uri.scheme, ignoreCase = true) -> {
            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else this.getDataColumn(
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

private fun Context.getDataColumn(
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
        cursor = this.contentResolver.query(
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

private fun Context.getFilePath(
    uri: Uri?
): String? {
    var cursor: Cursor? = null
    val projection = arrayOf(
        MediaStore.MediaColumns.DISPLAY_NAME
    )
    try {
        if (uri == null) return null
        cursor = this.contentResolver.query(
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

val defaultCover: Int
    get() = R.drawable.l_df_cover_epub

fun decodeBitmapFromByteArray(
    coverImage: ByteArray,
    reqWidth: Int,
    reqHeight: Int
): Bitmap {
    // First decode with inJustDecodeBounds=true to check dimensions
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeByteArray(coverImage, 0, coverImage.size, options)

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeByteArray(coverImage, 0, coverImage.size, options)
}

private fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    // Raw height and width of image
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {

        val halfHeight = height / 2
        val halfWidth = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

fun Activity?.readEpub(
    bookInfo: BookInfo?
) {
    if (this == null || bookInfo == null) {
        throw NullPointerException("activity == null || bookInfo == null")
    }
    val intent = Intent(this, EpubReaderReadActivity::class.java)
    BookInfoData.instance.bookInfo = bookInfo
    this.startActivity(intent)
    this.tranIn()
}
