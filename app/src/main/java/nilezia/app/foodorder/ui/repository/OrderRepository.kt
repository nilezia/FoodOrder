package nilezia.app.foodorder.ui.repository

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.HistoryItem
import nilezia.app.foodorder.model.FoodItem
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class OrderRepository(context: Context) : OrderRepositoryContract {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.getReference("food-order")
    private var hisRef = database.getReference("order-history")

    override fun requestOrderFromServer(callbackHttp: CallbackHttp<MutableList<FoodItem>>) {


    }

    override fun requestOrders(callbackHttp: CallbackHttp<MutableList<FoodItem>>) {

        requestOrderFromFirebase(callbackHttp)
        // requestHistoryFromFirebase(callbackHttp)
    }

    override fun requestOrderFromFirebase(callbackHttp: CallbackHttp<MutableList<FoodItem>>) {

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val orders = mutableListOf<FoodItem>()

                dataSnapshot.children.forEach {

                    val value = it.getValue(FoodItem::class.java)
                    value?.amount = 1
                    Log.d("FirebaseRead", "Value is: ${value?.name}")
                    orders.add(value!!)

                }

                callbackHttp.onSuccess(orders)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("FirebaseRead", "Failed to read value.", error.toException()!!)
            }
        })


    }

    override fun requestHistoryFromFirebase(callbackHttp: CallbackHttp<MutableList<HistoryItem>>) {
        hisRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w("FirebaseRead", "Failed to read value.", p0.toException())

            }

            override fun onDataChange(p0: DataSnapshot) {
                val history = mutableListOf<HistoryItem>()
                p0.children.forEach {

                    val value = it.getValue(HistoryItem::class.java)
                    history.add(value!!)

                }
                callbackHttp.onSuccess(history)
            }
        })
    }

    override fun requestOrderFromLocal(callbackHttp: CallbackHttp<MutableList<FoodItem>>) {

//        val orders = mutableListOf<FoodItem>()
//        for (i in 0..6) {
//            val order: FoodItem? = when (i) {
//                0 -> FoodItem()
//                1 -> FoodItem()
//                2 -> FoodItem()
//                3 -> FoodItem()
//                4 -> FoodItem()
//                5 -> FoodItem()
//                else -> {
//                    FoodItem()
//                }
//
//            }
//            orders.add(order!!)
//        }
//        callbackHttp.onSuccess(orders)

    }

    override fun updateCartOrderToFirebase(listener: () -> Unit, cartOrders: MutableList<FoodItem>?) {
        createHistory(cartOrders)
        cartOrders?.forEach {
            it.isAdded = null
            it.quantity = it.quantity - it.amount
            myRef.child(it._id.toString()).setValue(it)
        }
        listener.invoke()
    }

    private fun createHistory(cartOrders: MutableList<FoodItem>?) {

        val hisModel = HistoryItem(Date().toString(), "", cartOrders?.sumBy { it.amount }!!, cartOrders.sumByDouble { it.price * it.amount }, cartOrders)
        hisRef.push().setValue(hisModel)

    }
}