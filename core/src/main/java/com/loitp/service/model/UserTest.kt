package com.loitp.service.model

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
data class UserTest(
    @SerializedName("id")
    @Expose
    var id: Int?,

    @SerializedName("email")
    @Expose
    var email: String?,

    @SerializedName("first_name")
    @Expose
    var firstName: String?,

    @SerializedName("last_name")
    @Expose
    var lastName: String?,

    @SerializedName("avatar")
    @Expose
    var avatar: String?
) : Serializable
