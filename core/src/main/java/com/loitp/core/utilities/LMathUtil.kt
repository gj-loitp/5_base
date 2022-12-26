package com.loitp.core.utilities

import java.math.BigDecimal

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LMathUtil {
    companion object {
        fun roundBigDecimal(
            value: BigDecimal,
            newScale: Int = 0
        ): BigDecimal {
            return value.setScale(newScale, BigDecimal.ROUND_HALF_UP)
        }

        fun roundDouble(
            value: Double,
            newScale: Int = 0
        ): Double {
            return value.toBigDecimal().setScale(newScale, BigDecimal.ROUND_HALF_UP).toDouble()
        }

        /**
         * Tìm ước số chung lớn nhất (USCLN)
         *
         * @param a: số nguyên dương
         * @param b: số nguyên dương
         * @return USCLN của a và b
         */
        fun getUSCLN(
            a: Int,
            b: Int
        ): Int {
            if (b == 0) return a
            return getUSCLN(a = b, b = a % b)
        }

        /**
         * Tìm bội số chung nhỏ nhất (BSCNN)
         *
         * @param a: số nguyên dương
         * @param b: số nguyên dương
         * @return BSCNN của a và b
         */
        fun getBSCNN(
            a: Int,
            b: Int
        ): Int {
            return (a * b) / getUSCLN(a, b)
        }
    }
}
