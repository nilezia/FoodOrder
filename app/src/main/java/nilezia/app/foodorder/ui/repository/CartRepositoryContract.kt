package nilezia.app.foodorder.ui.repository

import nilezia.app.foodorder.model.OrderItem

interface CartRepositoryContract {

    fun updateCartOrderToFirebase(listener: () -> Unit, cartOrders: MutableList<OrderItem>?)
}