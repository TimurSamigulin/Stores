package com.example.mainactivity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.room.dao.ProductDAO
import com.example.mainactivity.room.database.StoreDatabase
import com.example.mainactivity.room.entity.Product
import com.example.mainactivity.room.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val repository: ProductRepository

    init {
        val productDAO: ProductDAO = StoreDatabase.getInstance(application, viewModelScope).productDAO()
        repository = ProductRepository(productDAO)

    }

    fun updateProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateProduct(product)
    }

    fun switchProductFav(productId: Long): Boolean  = runBlocking(Dispatchers.Default) {
        val product = repository.getProduct(productId)
        product.favourite = !product.favourite
        updateProduct(product)
        return@runBlocking product.favourite
    }
}