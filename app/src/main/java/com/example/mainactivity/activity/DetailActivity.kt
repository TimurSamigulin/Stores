package com.example.mainactivity.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.mainactivity.R
import com.example.mainactivity.room.entity.Product
import com.example.mainactivity.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.toolbar.*

class DetailActivity: AppCompatActivity() {
    private lateinit var model: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar_actionbar)
        setTitle("Детали товара")


        model = ViewModelProvider(this).get(DetailViewModel::class.java)
        val intent: Intent = intent


        val txtTitle: TextView = findViewById(R.id.detail_title)
        val txtDescription: TextView = findViewById(R.id.detail_description)
        val txtPrice: TextView = findViewById(R.id.detail_price)
        val txtDiscount: TextView = findViewById(R.id.detail_discount)
        val txtOldPrice: TextView = findViewById(R.id.detail_old_price)
        val imgIcon: ImageView = findViewById(R.id.detail_image)

        txtTitle.text = intent.getStringExtra("EXTRA_TITLE")
        txtDescription.text = intent.getStringExtra("EXTRA_DESCRIPTION")
        txtOldPrice.text = intent.getIntExtra("EXTRA_PRICE", 0).toString()
        txtPrice.text = intent.getIntExtra("EXTRA_DIS_PRICE", 0).toString()
        txtDiscount.text = intent.getIntExtra("EXTRA_DISCOUNT", 0).toString() + "%"
        Glide.with(this)
            .load(intent.getStringExtra("EXTRA_ICON"))
            .error(R.drawable.no_image)
            .into(imgIcon)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_fav, menu)
        val menuItem: MenuItem = menu!!.findItem(R.id.action_fav)
        if (intent.getBooleanExtra("EXTRA_FAV", false)) {
            menuItem.setIcon(R.drawable.ic_star_check_24dp)
        } else {
            menuItem.setIcon(R.drawable.ic_star_not_check_24dp)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_fav) {
            val fav = model.switchProductFav(intent.getLongExtra("EXTRA_ID", 0))
            if (fav) {
                item.setIcon(R.drawable.ic_star_check_24dp)
            } else {
                item.setIcon(R.drawable.ic_star_not_check_24dp)
            }
        }
        return true
    }

}