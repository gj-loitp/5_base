package com.loitpcore.data

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AdmobData private constructor() {

    companion object {
        val instance = AdmobData()
    }

    var idAdmobFull: String? = ""
        get() {
            if (field.isNullOrEmpty()) {
//                Log.e(javaClass.simpleName, "idAdmobFull == null || idAdmobFull.isEmpty()")
                return ""
            }
            return field
        }
}
