package com.loitp.core.ext

import android.content.Intent
import android.os.Build
import android.os.Bundle
import java.io.Serializable

/**
 * Created by Loitp on 05,March,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun <T : Serializable?> Bundle.getSerializableCompat(key: String, clazz: Class<T>): T {
    @Suppress("DEPRECATION")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getSerializable(
        key,
        clazz
    )!! else (getSerializable(key) as T)
}
