package com.service

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
enum class RequestStatus(val value: Int) {
    NO_INTERNET_CONNECTION(value = 470),
    ERROR_CLIENT(value = 472),
    NO_AUTHENTICATION(value = 401),
    BAD_GATEWAY(value = 502),
    INTERNAL_SERVER(value = 500)
}
