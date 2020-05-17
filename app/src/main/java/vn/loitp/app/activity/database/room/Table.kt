package vn.loitp.app.activity.database.room

import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.Serializable
import java.math.BigDecimal

@Keep
data class Table(
        @Json(name = "id")
        val id: String? = null,

        @Json(name = "name")
        val name: String? = null,

        @Json(name = "vip")
        val vip: Boolean? = null,

        @Json(name = "positionX")
        val positionX: Int? = null,

        @Json(name = "positionY")
        val positionY: Int? = null,

        @Json(name = "outletId")
        val outletId: String? = null,

        @Json(name = "floorPlanId")
        val floorPlanId: String? = null,

        @Json(name = "tableTypeId")
        val tableTypeId: String? = null,

        @Json(name = "areaId")
        val areaId: String? = null,

        @Json(name = "tableType")
        val tableType: Int? = null,

        @Json(name = "capacity")
        val capacity: Int? = null,

        @Json(name = "status")
        var status: Int? = null,

        @Json(name = "tableId")
        val tableId: String? = null,

        @Json(name = "totalAmount")
        val totalAmount: BigDecimal? = BigDecimal.ZERO,

        @Json(name = "totalOrder")
        val totalOrder: Int? = null,

        @Json(name = "totalCover")
        val totalCover: Int? = null,

        @Json(name = "openTime")
        val openTime: String? = null,

        @Json(name = "closeTime")
        val closeTime: String? = null,

        @Json(name = "orders")
        val orders: List<Order>? = null,

        // more property
        var isCheck: Boolean = false,
        var isExpand: Boolean = false

) : Serializable
