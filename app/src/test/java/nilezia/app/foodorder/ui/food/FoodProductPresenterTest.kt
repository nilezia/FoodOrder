package nilezia.app.foodorder.ui.food

import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.ui.repository.OrderRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)

class FoodProductPresenterTest {

    @Mock
    private var mockView: FoodProductContract.View = mock(FoodProductContract.View::class.java)
    private var presenter: FoodProductPresenter = FoodProductPresenter()
//    private  var mRepository: OrderRepository = OrderRepository()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter.attachView(mockView)
    //    presenter.registerRepository(mRepository)
    }

    @Test
    fun requestOrders() {
       // presenter.requestOrders()

    }
    @Test
    fun addOrderItemToCart() {
        presenter.addOrderItemToCart(FoodItem(), 0)

    }

    @Test
    fun removeOrderFromCart() {
        presenter.removeOrderFromCart(FoodItem(), 0)
    }
}