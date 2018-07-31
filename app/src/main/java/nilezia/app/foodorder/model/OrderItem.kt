package nilezia.app.foodorder.model

data class OrderItem(var _id: Int, var name: String?,
                     var price: Double, var amount: Int,
                     var image: String? = null, var description: String?, var isAdded: Boolean? = false)