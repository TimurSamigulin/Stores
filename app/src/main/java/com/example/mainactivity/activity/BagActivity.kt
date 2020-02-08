package com.example.mainactivity.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.R
import com.example.mainactivity.adapter.ProductAdapter
import com.example.mainactivity.room.entity.Product
import com.example.mainactivity.viewmodel.ProductViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_bag.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.toolbar.*

class BagActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ProductAdapter.OnBtnClickListener {

    private lateinit var model: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bag)
        setSupportActionBar(toolbar_actionbar)
        setTitle("Корзина")

        val productAdapter: ProductAdapter = ProductAdapter(this)

        model = ViewModelProvider(this).get(ProductViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.bag_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter

        val toggle = ActionBarDrawerToggle(this, bag_drawer_layout, toolbar_actionbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        bag_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        model.favProducts.observe(this, Observer {
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
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_all_store -> {
                val intent: Intent = Intent(this, MainActivity::class.java)
                intent.putExtra("EXTRA_CAT", true)
                startActivity(intent)
            }
            R.id.nav_fav_store -> {
                val intent: Intent = Intent(this, MainActivity::class.java)
                intent.putExtra("EXTRA_CAT", false)
                startActivity(intent)
            }
        }
        // drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

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
}