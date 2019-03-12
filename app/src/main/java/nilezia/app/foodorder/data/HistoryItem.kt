package nilezia.app.foodorder.data

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
data class HistoryItem(
        var receiptNo: String? = null,
        var date: String? = null,
        var time: String? = null,
        var totalAmount: Int = 0,
        var totalPrice: Double = .0,
        var orders: MutableList<FoodItem>? = null,
        var userName: String? = null
)