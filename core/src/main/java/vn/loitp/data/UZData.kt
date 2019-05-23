package vn.loitp.data

import vn.loitp.core.common.Constants
import vn.loitp.restapi.uiza.UZRestClient
import vn.loitp.restapi.uiza.UZRestClientGetLinkPlay

class UZData private constructor() {

    var appId: String? = null
        private set

    fun initWorkspace(domainApi: String, appId: String, token: String, urlLinkPlayEnvironment: String) {
        this.appId = appId
        UZRestClient.init(Constants.PREFIXS + domainApi, token)
        UZRestClientGetLinkPlay.init(urlLinkPlayEnvironment)
    }

    companion object {
        val instance = UZData()
    }
}
