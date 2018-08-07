package nilezia.app.foodorder.ui.cart.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import nilezia.app.foodorder.R
import nilezia.app.foodorder.model.OrderItem


class CartAdapter(cartClickListener: CartViewHolder.CartClickListener) : RecyclerView.Adapter<CartViewHolder>(), CartAdapterContract.View {


    private val listener = cartClickListener
    private val mPresenter = CartAdapterPresenter(this)
    var orders: MutableList<OrderItem>?
        get() = mPresenter.getOrderItem()
        set(orders) {
            mPresenter.setOrderItem(orders)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_cart, parent, false)
        mPresenter.attachView(this)
        return CartViewHolder(view).apply {
            tvOrderName = view.findViewById(R.id.tvOrderName)
            tvOrderAmount = view.findViewById(R.id.tvAmount)
            tvOrderPrice = view.findViewById(R.id.tvOrderPrice)
            tvOrderQuantity = view.findViewById(R.id.tvOrderQuantity)
            btnMinus = view.findViewById(R.id.btnMinus)
            btnPlus = view.findViewById(R.id.btnPlus)
            btnDelete = view.findViewById(R.id.btnDelete)
        }
    }

    override fun getItemCount(): Int = mPresenter.getOrderItem()?.size!!

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        holder.bindView(orders?.get(position), position)
        holder.btnPlus?.setOnClickListener {
            mPresenter.increaseOrder(orders?.get(position)!!, position)
            listener.onClickIncreaseOrder(orders?.get(position)!!, position)
        }
        holder.btnMinus?.setOnClickListener {
            mPresenter.decreaseOrder(orders?.get(position)!!, position)
            listener.onClickDecreaseOrder(orders?.get(position)!!, position)
        }
        holder.btnDelete?.setOnClickListener {
            listener.onClickDeleteOrder(orders?.get(position)!!, position)
        }
    }

    fun removeOrder(order: OrderItem, position: Int) {
        Log.d("itemClick", "${order.name} Remove")
        mPresenter.deleteOrder(order, position)

    }

    fun getPriceTotal(): Double = mPresenter.getPriceTotal()

    fun hasItem(): Boolean = mPresenter.getOrderItem()?.size!! > 0


}