package com.loitpcore.utils.util

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/16
 * desc  : 字符串相关工具类
</pre> *
 */
class StringUtils {
    companion object {

        @JvmStatic
        fun isEmpty(s: CharSequence?): Boolean {
            return s == null || s.isEmpty()
        }

        fun isTrimEmpty(s: String?): Boolean {
            return s == null || s.trim { it <= ' ' }.isEmpty()
        }

        fun isSpace(s: String?): Boolean {
            if (s == null) return true
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }

        fun equals(a: CharSequence?, b: CharSequence?): Boolean {
            if (a === b) return true
            var length = 0
            return if (a != null && b != null && a.length.also { length = it } == b.length) {
                if (a is String && b is String) {
                    a == b
                } else {
                    for (i in 0 until length) {
                        if (a[i] != b[i]) return false
                    }
                    true
                }
            } else false
        }

        fun equalsIgnoreCase(a: String?, b: String?): Boolean {
            return a?.equals(b, ignoreCase = true) ?: (b == null)
        }

        fun null2Length0(s: String?): String {
            return s ?: ""
        }

        fun length(s: CharSequence?): Int {
            return s?.length ?: 0
        }

        fun reverse(s: String): String {
            val len = length(s)
            if (len <= 1) return s
            val mid = len shr 1
            val chars = s.toCharArray()
            var c: Char
            for (i in 0 until mid) {
                c = chars[i]
                chars[i] = chars[len - i - 1]
                chars[len - i - 1] = c
            }
            return String(chars)
        }
    }
}
