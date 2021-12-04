package com.data

/**
 * Created by www.muathu@gmail.com on 12/26/2017.
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
