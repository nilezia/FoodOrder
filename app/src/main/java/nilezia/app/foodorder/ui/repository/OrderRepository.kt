package nilezia.app.foodorder.ui.repository

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.OrderItem


class OrderRepository(context: Context) : OrderRepositoryContract {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.getReference("food-order")


    override fun requestOrderFromServer(callbackHttp: CallbackHttp<MutableList<OrderItem>>) {


    }

    override fun requestOrders(callbackHttp: CallbackHttp<MutableList<OrderItem>>) {

        requestOrderFromFirebase(callbackHttp)

    }

    override fun requestOrderFromFirebase(callbackHttp: CallbackHttp<MutableList<OrderItem>>) {

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val orders = mutableListOf<OrderItem>()

                dataSnapshot.children.forEach {

                    val value = it.getValue(OrderItem::class.java)
                    value?.amount = 1
                    Log.d("FirebaseRead", "Value is: ${value?.name}")
                    orders.add(value!!)

                }

                callbackHttp.onSuccess(orders)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("FirebaseRead", "Failed to read value.", error.toException())
            }
        })


    }

    override fun requestOrderFromLocal(callbackHttp: CallbackHttp<MutableList<OrderItem>>) {

        val orders = mutableListOf<OrderItem>()
        for (i in 0..6) {
            val order: OrderItem? = when (i) {
                0 -> OrderItem()
                1 -> OrderItem()
                2 -> OrderItem()
                3 -> OrderItem()
                4 -> OrderItem()
                5 -> OrderItem()
                else -> {
                    OrderItem()
                }

            }
            orders.add(order!!)
        }
        callbackHttp.onSuccess(orders)

    }

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