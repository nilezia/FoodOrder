package nilezia.app.foodorder.ui.repository

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import nilezia.app.foodorder.model.OrderItem

class CartRepository(context: Context) : CartRepositoryContract {


    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.getReference("food-order")

    override fun updateCartOrderToFirebase(listener: () -> Unit, cartOrders: MutableList<OrderItem>?) {
        cartOrders?.forEach {
            it.isAdded = null
            it.quantity = it.quantity - it.amount
            it.amount = 1
            myRef?.child(it._id.toString())?.setValue(it)
            listener.invoke()
        }
    }
}