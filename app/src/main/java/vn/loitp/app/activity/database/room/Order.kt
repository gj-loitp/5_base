package vn.loitp.app.activity.database.room

import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.Serializable
import java.math.BigDecimal

@Keep
data class Order(
        @Json(name = "id")
        var id: String? = null,

        @Json(name = "orderName")
        var orderName: String? = null,

        @Json(name = "isSplit")
        var isSplit: Int? = null,

        @Json(name = "code")
        var code: String? = null,

        @Json(name = "refInfo")
        var refInfo: String? = null,

        @Json(name = "discountId")
        var discountId: String? = null,

        @Json(name = "reasonId")
        var reasonId: String? = null,

        @Json(name = "discountOrderType")
        var discountOrderType: Int? = null,

        @Json(name = "discountName")
        var discountName: String? = null,

        @Json(name = "discountNote")
        var discountNote: String? = null,

        @Json(name = "note")
        var note: String? = null,

        @Json(name = "discountValue")
        var discountValue: BigDecimal = BigDecimal.ZERO,

        @Json(name = "discountIn")
        var discountIn: Int? = null,

        @Json(name = "amountPercent")
        var amountPercent: BigDecimal = BigDecimal.ZERO,

        @Json(name = "discount")
        var discount: BigDecimal? = BigDecimal.ZERO,

        @Json(name = "outletId")
        var outletId: String? = null,

        @Json(name = "outletName")
        var outletName: String? = null,

        @Json(name = "orderTakerName")
        var orderTakerName: String? = null,

        @Json(name = "outletPhone")
        var outletPhone: String? = null,

        @Json(name = "outletAddress")
        var outletAddress: String? = null,

        @Json(name = "floorPlanId")
        var floorPlanId: String? = null,

        @Json(name = "staffId")
        var staffId: String? = null,

        @Json(name = "orderStatus")
        var orderStatus: Int? = null,

        @Json(name = "cover")
        var cover: Int? = null,

        @Json(name = "nationality")
        var nationality: String? = null,

        @Json(name = "specialRequest")
        var specialRequest: String? = null,

        @Json(name = "cancelReason")
        var cancelReason: String? = null,

        @Json(name = "typeOfMeal")
        var typeOfMeal: Int? = null,

        @Json(name = "typeOfService")
        var typeOfService: Int? = null,

        @Json(name = "orderSource")
        var orderSource: Int? = null,

        @Json(name = "staffName")
        var staffName: String? = null,

        @Json(name = "tableName")
        var tableName: String? = null,

        @Json(name = "tables")
        var tables: List<Table>? = null,

        @Json(name = "orderType")
        var orderType: Int? = null,

        @Json(name = "svc")
        var svc: BigDecimal = BigDecimal.ZERO,

        @Json(name = "tax")
        var tax: BigDecimal = BigDecimal.ZERO,

        @Json(name = "sct")
        var sct: BigDecimal = BigDecimal.ZERO,

        @Json(name = "total")
        var total: BigDecimal = BigDecimal.ZERO,

        @Json(name = "totalNotRound")
        var totalNotRound: BigDecimal = BigDecimal.ZERO,

        @Json(name = "subTotal")
        var subTotal: BigDecimal = BigDecimal.ZERO,

        @Json(name = "comment")
        var comment: String? = null,

        @Json(name = "cookingTime")
        var cookingTime: String? = null,

        @Json(name = "guestQuantity")
        var guestQuantity: Int? = null,

        @Json(name = "guestType")
        var guestType: Int? = null,

        @Json(name = "roomId")
        var roomId: String? = null,

        @Json(name = "reservationId")
        var reservationId: String? = null,

        @Json(name = "confirmationNumber")
        var confirmationNumber: String? = null,

        @Json(name = "roomNumber")
        var roomNumber: String? = null,

        @Json(name = "roomCharge")
        var roomCharge: Boolean? = null,

        @Json(name = "guestName")
        var guestName: String? = null,

        @Json(name = "propertyId")
        var propertyId: String? = null,

        @Json(name = "orderStart")
        var orderStart: String? = null,

        @Json(name = "orderEnd")
        var orderEnd: String? = null,

        @Json(name = "floorId")
        var floorId: String? = null,

        @Json(name = "parentId")
        var parentId: String? = null,

        @Json(name = "rootId")
        var rootId: String? = null,

        @Json(name = "createdDate")
        var createdDate: String? = null,

        @Json(name = "createdBy")
        var createdBy: String? = null,

        @Json(name = "printCount")
        var printCount: Int? = null,

        // property for Return
        @Json(name = "isReturn")
        var isReturn: Boolean? = false,

        // property for Payment
        @Json(name = "rounding")
        var rounding: BigDecimal? = BigDecimal.ZERO,

        @Json(name = "changeMoney")
        var changeMoney: BigDecimal = BigDecimal.ZERO,

        @Json(name = "totalDiscountNetPrice")
        var totalDiscountNetPrice: BigDecimal? = BigDecimal.ZERO,

        @Json(name = "subTotalAfterDiscount")
        var subTotalAfterDiscount: BigDecimal? = BigDecimal.ZERO,

        @Json(name = "taxAfterDiscount")
        var taxAfterDiscount: BigDecimal? = BigDecimal.ZERO,

        @Json(name = "svcAfterDiscount")
        var svcAfterDiscount: BigDecimal? = BigDecimal.ZERO,

        @Json(name = "sctAfterDiscount")
        var sctAfterDiscount: BigDecimal? = BigDecimal.ZERO,

        // more property
        var isCheck: Boolean = false,
        var isPaymentSuccess: Boolean = false

) : Serializable
