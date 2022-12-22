package com.loitp.sv.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
data class ErrorJson(
    @SerializedName("errors")
    @Expose
    val errors: List<ErrorResponse>? = null
) : Serializable
