package test.com.mvpunittest

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import test.com.mvpunittest.api.ProductResponse

class ProductPresenterTest {
    lateinit var presenter: ProductContract.IProductPresenter
    var productResponse = ProductResponse()
    var emptyProductResponse = ProductResponse()

    @Mock
    lateinit var repository: IProductRepository
    @Mock
    lateinit var productView: ProductContract.IProductView

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        presenter = ProductPresenter(productView, repository)

        productResponse.id = "pixel3"
        productResponse.name = "Google Pixel 3"
        productResponse.price = 27000
        productResponse.desc = "Desc"
    }

    @Test
    fun test_getProduct() {
        val productID = "pixel3"
        val loadProductCallback =
            argumentCaptor<IProductRepository.LoadProductCallback>()

        presenter.getProduct(productID)
        verify(repository).getProduct(eq(productID), capture(loadProductCallback))
        loadProductCallback.value.onProductResult(emptyProductResponse)
        verify(productView).onGetResult(emptyProductResponse)
    }

    @Test
    fun test_buySuccess() {
        val productID = "pixel3"
        val amount = 1
        val buyProductCallback =
            argumentCaptor<IProductRepository.BuyProductCallback>()

        presenter.buy(productID, amount)
        verify(repository).buy(eq(productID), eq(amount), capture(buyProductCallback))
        buyProductCallback.value.onBuyResult(true)
        verify(productView).onBuySuccess()
    }

    @Test
    fun test_bugFail() {
        val productID = "pixel3"
        val amount = 11
        val buyProductCallback =
            argumentCaptor<IProductRepository.BuyProductCallback>()

        presenter.buy(productID, amount)
        verify(repository).buy(eq(productID), eq(amount), capture(buyProductCallback))
        buyProductCallback.value.onBuyResult(false)
        verify(productView).onBuyFail()
    }
}