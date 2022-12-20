package com.loitp.core.utils

import com.loitpcore.utils.util.SDCardUtils
import com.loitpcore.utils.util.Utils
import java.io.File

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class CleanUtils private constructor() {
    companion object {
        fun cleanInternalCache(): Boolean {
            return FileUtils.deleteFilesInDir(Utils.getContext()?.cacheDir)
        }

        fun cleanInternalFiles(): Boolean {
            return FileUtils.deleteFilesInDir(Utils.getContext()?.filesDir)
        }

        fun cleanInternalDbs(): Boolean {
            return FileUtils.deleteFilesInDir(Utils.getContext()?.filesDir?.parent + File.separator + "databases")
        }

        @Suppress("unused")
        fun cleanInternalDbByName(
            dbName: String?
        ): Boolean? {
            return Utils.getContext()?.deleteDatabase(dbName)
        }

        fun cleanInternalSP(): Boolean {
            return FileUtils.deleteFilesInDir(Utils.getContext()?.filesDir?.parent + File.separator + "shared_prefs")
        }

        fun cleanExternalCache(): Boolean {
            return SDCardUtils.isSDCardEnable() && FileUtils.deleteFilesInDir(Utils.getContext()?.externalCacheDir)
        }

        @Suppress("unused")
        fun cleanCustomCache(dirPath: String?): Boolean {
            return FileUtils.deleteFilesInDir(dirPath)
        }

        fun cleanCustomCache(dir: File?): Boolean {
            return FileUtils.deleteFilesInDir(dir)
        }
    }
}
