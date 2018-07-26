package nilezia.app.foodorder.model

import java.util.*

data class Order(var _id: Int, var name: String?, var createDate: Date, var price: Double, var quantity: Int)