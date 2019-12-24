package vn.loitp.app.activity.api.coroutine

import loitp.basemaster.BuildConfig

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class ApiConfiguration {

    companion object {

        const val LIMIT_PAGE = 20

        // authentication
        const val BASE_AUTHEN_URL = BuildConfig.API_AUTHEN_ENDPOINT
        const val LOGIN = BuildConfig.PATH_LOGIN
        const val LOGOUT = BuildConfig.PATH_LOGOUT
        const val ME = BuildConfig.PATH_ME

        // base url
        const val BASE_URL = BuildConfig.API_ENDPOINT

        // product order
        const val PRODUCT_ORDER = BuildConfig.PATH_MAIN + BuildConfig.PATH_PRODUCT_ODER
        const val PRODUCT_ORDER_SEARCH = BuildConfig.PATH_MAIN + BuildConfig.PATH_PRODUCT_ODER_SEARCH

        // filter
        const val PLANT = BuildConfig.PATH_MAIN + BuildConfig.PATH_PLANT
        const val LOTCELL = BuildConfig.PATH_MAIN + BuildConfig.PATH_LOTCELL
        const val MATERIAL_DATA = BuildConfig.PATH_MAIN + BuildConfig.PATH_MATERIAL_DATA

        // material request
        const val MATERIAL_REQUEST = BuildConfig.PATH_MAIN + BuildConfig.PATH_MATERIAL_REQUEST
        const val MATERIAL_REQUEST_SEARCH = BuildConfig.PATH_MAIN + BuildConfig.PATH_MATERIAL_REQUEST_SEARCH

        // API Param
        const val AUTHORIZATION_HEADER = "Authorization"
        const val LANGUAGE_HEADER = "Accept-Language"
        const val TOKEN_TYPE = "bearer"
        const val TIME_OUT = 30L // In second
    }
}