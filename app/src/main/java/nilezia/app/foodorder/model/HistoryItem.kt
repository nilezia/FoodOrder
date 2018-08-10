package nilezia.app.foodorder.model

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
data class HistoryItem(var date: String? = null,
                  var time: String? = null,
                  var totalAmount: Int = 0,
                  var totalPrice: Double = .0,
                  var orders: MutableList<FoodItem>? = null)  {

}