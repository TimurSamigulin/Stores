package com.example.mainactivity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.R
import com.example.mainactivity.adapter.StoreAdapter
import com.example.mainactivity.retrofit.ApiService
import com.example.mainactivity.room.entity.Store
import com.example.mainactivity.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), StoreAdapter.OnBtnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var model: MainViewModel

    override fun onFuvClickListener(store: Store) {
        Toast.makeText(this, "Store Star Click", Toast.LENGTH_LONG).show()
        store.favourite = !store.favourite
        model.updateStore(store)
    }

    override fun onViewClickListener(store: Store) {
        Toast.makeText(this, store.id.toString(), Toast.LENGTH_LONG).show()
        val intent: Intent = Intent(this, ProductActivity::class.java)

        intent.putExtra("EXTRA_ID", store.id)
        intent.putExtra("EXTRA_TITLE", store.title)
        intent.putExtra("EXTRA_X", store.x)
        intent.putExtra("EXTRA_Y", store.y)
        intent.putExtra("EXTRA_API", store.apiHref)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_actionbar)

        setTitle("Магазины")



        val storeAdapter: StoreAdapter = StoreAdapter(this)

        val recyclerView: RecyclerView = findViewById(R.id.store_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = storeAdapter

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar_actionbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        model = ViewModelProvider(this).get(MainViewModel::class.java)

        if (intent.getBooleanExtra("EXTRA_CAT", true))
            model.currentCategory = "All"
        else
            model.currentCategory = "Fav"

        // model.insert(Store(null, "Vtxnf", "sss", "https://ярче.рф/static/yarche/v2/images/logo.png", false, "lenta.json"))

        model.Stores.observe(this, Observer {
            stores -> stores?.let {storeAdapter.setItems(it)}
        })


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_all_store -> {
                model.changeStores("All")
                setTitle("Магазины")
            }
            R.id.nav_fav_store -> {
                model.changeStores("Fav")
                setTitle("Избранные магазины")
            }
            R.id.nav_fav_products -> {
                val intent: Intent = Intent(this, BagActivity::class.java)
                startActivity(intent)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
