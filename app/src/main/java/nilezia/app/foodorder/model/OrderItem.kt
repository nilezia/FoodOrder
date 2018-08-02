package nilezia.app.foodorder.model

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
data class OrderItem(var _id: Int = 0, var name: String? = null, var price: Double = .0, var quantity: Int = 0, var amount: Int = 1, var image: String? = null, var description: String? = null, var isAdded: Boolean? = false) {

}

