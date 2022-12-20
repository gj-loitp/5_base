package com.loitp.service

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
enum class RequestStatus(val value: Int) {
    NO_INTERNET_CONNECTION(value = 470),
    ERROR_CLIENT(value = 472),
    NO_AUTHENTICATION(value = 401),
    BAD_GATEWAY(value = 502),
    INTERNAL_SERVER(value = 500)
}
