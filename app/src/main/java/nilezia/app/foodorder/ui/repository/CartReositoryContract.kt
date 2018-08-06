package nilezia.app.foodorder.ui.repository

import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.OrderItem

interface CartReositoryContract {

    fun updateCartOrderToFirebase(cartOrders: MutableList<OrderItem>?)
}