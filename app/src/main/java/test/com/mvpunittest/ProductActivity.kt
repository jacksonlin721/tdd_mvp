package test.com.mvpunittest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import test.com.mvpunittest.api.ProductAPI
import test.com.mvpunittest.api.ProductResponse
import java.text.NumberFormat

class ProductActivity : AppCompatActivity(), ProductContract.IProductView {

    private val productId = "pixel3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productRepository = ProductRepository(ProductAPI())
        val productPresenter = ProductPresenter(this, productRepository)

        buy.setOnClickListener {
            val numbers = findViewById<EditText>(R.id.productItems).text.toString().toInt()
            productPresenter.buy(productId, numbers)
        }

        productPresenter.getProduct(productId)
    }

    override fun onGetResult(productResponse: ProductResponse) {
        productName.text = productResponse.name
        productDesc.text = productResponse.desc

        val currencyFormat = NumberFormat.getCurrencyInstance()
        currencyFormat.maximumFractionDigits = 0
        val price = currencyFormat.format(productResponse.price)
        productPrice.text = price
    }

    override fun onBuySuccess() {
        Toast.makeText(this, "購買成功", Toast.LENGTH_LONG).show();
    }

    override fun onBuyFail() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("購買失敗").setTitle("錯誤")
        builder.show()
    }
}