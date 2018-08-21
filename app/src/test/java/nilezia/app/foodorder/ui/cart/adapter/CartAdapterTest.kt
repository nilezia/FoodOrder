package nilezia.app.foodorder.ui.cart.adapter

import nilezia.app.foodorder.model.FoodItem
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock

class CartAdapterTest {

    @Mock
    private val mPresenter = CartAdapterPresenter(CartAdapter())
    private var mView: CartAdapterContract.View = mock(CartAdapterContract.View::class.java)
    val list: MutableList<FoodItem> = mutableListOf()
    @Before
    fun setup() {
        mPresenter.attachView(mView)
        list.add(FoodItem(1, 1, "", "", false, "", .0, 5))

        mPresenter.setOrderItem(list)

    }

    @Test
    fun increaseOrder() {

        mPresenter.increaseOrder(list[0], 0)
        Assert.assertEquals(2, mPresenter.getOrderItem()?.get(0)?.amount)
    }

    @Test
    fun decreaseOrder() {
        mPresenter.decreaseOrder(list[0], 0)
        Assert.assertEquals(1, mPresenter.getOrderItem()?.get(0)?.amount)
    }

    @Test
    fun removeOrder() {

        mPresenter.deleteOrder(list[0], 0)
        Assert.assertEquals(0, mPresenter.getOrderItem()?.size)
    }
}