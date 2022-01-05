package com.utils.util

import java.io.File

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

        fun cleanCustomCache(dirPath: String?): Boolean {
            return FileUtils.deleteFilesInDir(dirPath)
        }

        fun cleanCustomCache(dir: File?): Boolean {
            return FileUtils.deleteFilesInDir(dir)
        }
    }
}
