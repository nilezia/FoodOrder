package nilezia.app.foodorder.data

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
data class FoodItem(var _id: Int,
                    var amount: Int,
                    var description: String?,
                    var image: String?,
                    var isAdded: Boolean = false,
                    var name: String?,
                    var price: Double,
                    var quantity: Int) {
    constructor() : this(0, 0, "", "", false, "", .0, 0)
}

