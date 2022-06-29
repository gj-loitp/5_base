package com.loitpcore.data

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
