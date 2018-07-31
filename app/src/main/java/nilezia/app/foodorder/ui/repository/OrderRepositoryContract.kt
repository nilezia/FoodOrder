package nilezia.app.foodorder.ui.repository

import nilezia.app.foodorder.model.OrderItem

interface OrderRepositoryContract {

    fun getOrders(): MutableList<OrderItem>?

}