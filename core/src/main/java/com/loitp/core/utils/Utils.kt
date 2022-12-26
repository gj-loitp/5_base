package com.loitp.core.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class Utils {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        /**
         * 初始化工具类
         *
         * @param context 上下文
         */
        fun init(context: Context) {
            Companion.context = context.applicationContext
        }

        /**
         * 获取ApplicationContext
         *
         * @return ApplicationContext
         */
        @JvmStatic
        fun getContext(): Context? {
            if (context != null) {
                return context
            }
            throw NullPointerException("u should init first")
        }
    }
}
