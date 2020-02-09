package com.example.mainactivity.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.R
import com.example.mainactivity.adapter.ProductAdapter
import com.example.mainactivity.room.entity.Product
import com.example.mainactivity.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.toolbar.*

class ProductActivity: AppCompatActivity(), ProductAdapter.OnBtnClickListener {
    override fun onFuvClickListener(product: Product) {
        product.favourite = !product.favourite
        model.updateProduct(product)
    }

    override fun onViewClickListener(product: Product) {
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXTRA_ID", product.id)
        intent.putExtra("EXTRA_TITLE", product.title)
        intent.putExtra("EXTRA_DESCRIPTION", product.description)
        intent.putExtra("EXTRA_PRICE", product.price)
        intent.putExtra("EXTRA_DIS_PRICE", product.discountPrice)
        intent.putExtra("EXTRA_DISCOUNT", product.discount)
        intent.putExtra("EXTRA_ICON", product.icon)
        intent.putExtra("EXTRA_FAV", product.favourite)
        startActivity(intent)
    }

    private lateinit var model: ProductViewModel
    lateinit var storeTitle: String
    var x: Double = 0.0
    var y: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        setSupportActionBar(toolbar_actionbar)

        val intent = intent
        storeTitle = intent.getStringExtra("EXTRA_TITLE")
        x = intent.getDoubleExtra("EXTRA_X", 0.0)
        y = intent.getDoubleExtra("EXTRA_Y", 0.0)
        setTitle(storeTitle)

        val productAdapter: ProductAdapter = ProductAdapter(this)

        val recyclerView: RecyclerView = findViewById(R.id.product_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter

        model = ViewModelProvider(this).get(ProductViewModel::class.java)


        model.getProducts(intent.getLongExtra("EXTRA_ID", 0), intent.getStringExtra("EXTRA_API"))
        model.storeProducts.observe(this, Observer {
            products -> products?.let { productAdapter.setProducts(it) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_sort, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sort_name -> {
                item.setChecked(true)
                model.sortProducts("Name")
                return true
            }
            R.id.sort_price -> {
                item.setChecked(true)
                model.sortProducts("Price")
                return true
            }
            R.id.sort_discount -> {
                item.setChecked(true)
                model.sortProducts("Discount")
                return true
            }
            R.id.open_map -> {
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("EXTRA_X", x)
                intent.putExtra("EXTRA_Y", y)
                intent.putExtra("EXTRA_TITLE", storeTitle)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}