package nilezia.app.foodorder.ui.repository

import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.FoodItem

interface OrderRepositoryContract {

    fun requestOrders(callbackHttp: CallbackHttp<MutableList<FoodItem>>)

    fun requestOrderFromServer(callbackHttp: CallbackHttp<MutableList<FoodItem>>)

    fun requestOrderFromFirebase(callbackHttp: CallbackHttp<MutableList<FoodItem>>)

    fun requestHistoryFromFirebase(callbackHttp: CallbackHttp<MutableList<FoodItem>>)

    fun requestOrderFromLocal(callbackHttp: CallbackHttp<MutableList<FoodItem>>)

    fun updateCartOrderToFirebase(listener: () -> Unit, cartOrders: MutableList<FoodItem>?)


}