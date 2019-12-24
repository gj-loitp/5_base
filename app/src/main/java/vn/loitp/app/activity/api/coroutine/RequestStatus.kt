package vn.loitp.app.activity.api.coroutine

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
enum class RequestStatus(val value: Int) {
    NO_INTERNET_CONNECTION(470),
    ERROR_PARSE(471),
    ERROR_CLIENT(472),
    NO_AUTHENTICATION(401),
    BAD_GATEWAY(502),
    INTERNAL_SERVER(500),
    BATTERY_ERROR(2701),
    BATTERY_BLOCK(2702),
    BATTERY_NOT_STATION(2703),
    TICKET_NOT_EXIST(value = 1551)

}