package nilezia.app.foodorder.ui.cart.adapter

import nilezia.app.foodorder.model.FoodItem

class CartAdapterPresenter : CartAdapterPresenterImp(), CartAdapterContract.Presenter {

    private lateinit var items: MutableList<FoodItem>
    override fun increaseOrder(order: FoodItem, position: Int) {

        order.apply {
            if (amount < quantity) {
                amount += 1
            }
        }
    }

    override fun decreaseOrder(order: FoodItem, position: Int) {

        order.apply {
            if (amount > 1) {
                amount -= 1
            }
        }

    }

    override fun deleteOrder(order: FoodItem, position: Int) {
        items.apply {
            this.remove(order)
        }
    }

    override fun getOrderItem(): MutableList<FoodItem> = items

    override fun setOrderItem(orderItems: MutableList<FoodItem>) {
        this.items = orderItems

    }

    override fun getPriceTotal(): Double = items.sumByDouble {
        it.price * it.amount
    }
}