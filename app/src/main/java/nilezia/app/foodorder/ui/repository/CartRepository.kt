package nilezia.app.foodorder.ui.repository

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import nilezia.app.foodorder.model.OrderItem

class CartRepository(context: Context) : CartReositoryContract {


    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.getReference("food-order")

    override fun updateCartOrderToFirebase(cartOrders: MutableList<OrderItem>?) {

        cartOrders?.forEach {
            it.isAdded = null
            val spent = it.quantity - it.amount
            it.quantity = spent
            myRef?.child(it._id.toString())?.setValue(it)

        }


    }


}