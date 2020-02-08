package com.example.mainactivity.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.room.dao.ProductDAO
import com.example.mainactivity.room.database.ProductDatabase
import com.example.mainactivity.room.database.StoreDatabase
import com.example.mainactivity.room.entity.Product
import com.example.mainactivity.room.entity.Store
import com.example.mainactivity.room.repository.ProductRepository
import kotlinx.coroutines.*

class ProductViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ProductRepository
    val allProducts: LiveData<List<Product>>
    val favProducts: LiveData<List<Product>>
    var storeProducts: MutableLiveData<List<Product>> = MutableLiveData()

    init {
        val productDAO: ProductDAO = StoreDatabase.getInstance(application, viewModelScope).productDAO()
        repository = ProductRepository(productDAO)

        allProducts = repository.allProducts
        favProducts = repository.favProducts
    }

    fun insertProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertProduct(product)
    }

    fun updateProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateProduct(product)
    }

    private fun getStoreProducts(storeId: Long, storeApi: String):List<Product> = runBlocking(Dispatchers.Default) {
        repository.ins(storeId, storeApi)
        val result = repository.getStoreProducts(storeId)
        return@runBlocking result as List<Product>
    }

    fun getProducts(storeId: Long, storeApi: String) {
        storeProducts.value = getStoreProducts(storeId, storeApi)
    }

    fun sortProducts(sortType: String) = when(sortType) {
        "Name" -> storeProducts.value?.let { storeProducts.value = it.sortedBy { it.title } }
        "Price" -> storeProducts.value?.let { storeProducts.value = it.sortedBy { it.discountPrice } }
        "Discount" -> storeProducts.value?.let { storeProducts.value = it.sortedBy { it.discount } }
        else -> storeProducts.value?.let { storeProducts.value = it.sortedBy { it.discountPrice } }
    }

}