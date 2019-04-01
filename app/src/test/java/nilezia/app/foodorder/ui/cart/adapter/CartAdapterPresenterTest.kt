package nilezia.app.foodorder.ui.cart.adapter

import nilezia.app.foodorder.data.FoodItem
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock

class CartAdapterPresenterTest {

    @Mock
    private val mPresenter = CartAdapterPresenter()
    private var mView: CartAdapterContract.View = mock(CartAdapterContract.View::class.java)
    private val foodList: MutableList<FoodItem> = mutableListOf()
    @Before
    fun setup() {
        mPresenter.attachView(mView)
        foodList.add(FoodItem(1, 1, "", "", false, "", 2.0, 5))
        foodList.add(FoodItem(1, 1, "", "", false, "", 2.0, 5))

        mPresenter.setOrderItem(foodList)

    }

    @Test
    fun increaseOrder() {

        mPresenter.increaseOrder(foodList[0], 0)
        Assert.assertEquals(2, mPresenter.getOrderItem()?.get(0)?.amount)
    }

    @Test
    fun decreaseOrder() {
        mPresenter.decreaseOrder(foodList[0], 0)
        Assert.assertEquals(1, mPresenter.getOrderItem()?.get(0)?.amount)
    }

    @Test
    fun removeOrder() {

        mPresenter.deleteOrder(foodList[0], 0)
        mPresenter.deleteOrder(foodList[0], 0)
        Assert.assertEquals(0, mPresenter.getOrderItem()?.size)
    }

    @Test
    fun totalPrice() {
        foodList.add(FoodItem(2, 1, "", "", false, "", 3.0, 5))

        val totalPrice = mPresenter.getPriceTotal()
        Assert.assertEquals(5.0, totalPrice,0.0)
    }
}