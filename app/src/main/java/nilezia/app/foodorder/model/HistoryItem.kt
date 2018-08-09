package nilezia.app.foodorder.model

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
class HistoryItem {

    var date: String? = null
    var time: String? = null
    var totalAmount: Int = 0
    var totalPrice: Double = .0
    var orders: MutableList<FoodItem>? = null

    constructor() {

    }

    constructor(date: String? = null,
                time: String? = null,
                totalAmount: Int = 0,
                totalPrice: Double = .0,
                orders: MutableList<FoodItem>? = null) {
        this.date = date
        this.time = time
        this.totalAmount = totalAmount
        this.totalPrice = totalPrice
        this.orders = orders

    }
}