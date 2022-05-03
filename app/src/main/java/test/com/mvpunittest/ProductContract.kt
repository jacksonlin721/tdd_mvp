package test.com.mvpunittest

import test.com.mvpunittest.api.ProductResponse

class ProductContract {

    interface IProductPresenter {
        fun getProduct(productId: String)
        fun buy(productId: String, numbers: Int)
    }

    interface IProductView {
        fun onGetResult(productResponse: ProductResponse)
        fun onBuySuccess()
        fun onBuyFail()

    }
}
