package nilezia.app.foodorder.ui.cart.adapter

import nilezia.app.foodorder.model.OrderItem

class CartAdapterPresenter(cartAdapter: CartAdapter) : CartAdapterPresenterImp(), CartAdapterContract.Presenter {

    private var items: MutableList<OrderItem>? = null
    private val mCartAdapter = cartAdapter

    override fun increaseOrder(order: OrderItem, position: Int) {
        val amount = items?.get(position)?.amount
        val quantity = items?.get(position)?.quantity
        if (amount!! < quantity!!) {
            items?.get(position)?.amount = amount + 1
        }
        mCartAdapter.notifyDataSetChanged()
    }

    override fun decreaseOrder(order: OrderItem, position: Int) {
        val amount = items?.get(position)?.amount
        if (amount!! > 1) {
            items?.get(position)?.amount = amount - 1
        }
        mCartAdapter.notifyDataSetChanged()
    }

    override fun deleteOrder(order: OrderItem, position: Int) {
        items?.remove(items?.get(position)!!)
        mCartAdapter.notifyItemRemoved(position)
        mCartAdapter.notifyItemRangeChanged(position, items?.size!!)
    }

    override fun getOrderItem(): MutableList<OrderItem>? = items

    override fun setOrderItem(orderItems: MutableList<OrderItem>?) {
        this.items = orderItems

    }

    override fun getPriceTotal(): Double = items?.sumByDouble {
        it.price * it.amount
    }!!

}