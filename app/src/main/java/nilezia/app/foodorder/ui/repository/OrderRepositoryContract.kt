package nilezia.app.foodorder.ui.repository

import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.OrderItem

interface OrderRepositoryContract {

    fun requestOrders(callbackHttp: CallbackHttp<MutableList<OrderItem>>)

    fun requestOrderFromServer(callbackHttp: CallbackHttp<MutableList<OrderItem>>)

    fun requestOrderFromFirebase(callbackHttp: CallbackHttp<MutableList<OrderItem>>)

    fun requestOrderFromLocal(callbackHttp: CallbackHttp<MutableList<OrderItem>>)


}