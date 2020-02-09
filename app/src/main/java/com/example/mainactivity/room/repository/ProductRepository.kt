package com.example.mainactivity.room.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mainactivity.util.retrofit.ApiService
import com.example.mainactivity.room.dao.ProductDAO
import com.example.mainactivity.room.entity.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductRepository(private val productDAO: ProductDAO) {
    val allProducts: LiveData<List<Product>> = productDAO.getAllProducts()
    val favProducts: LiveData<List<Product>> = productDAO.getFavProducts()


    fun getStoreProducts(storeId: Long): List<Product> {
        return productDAO.getStoreProducts(storeId)
    }

    @WorkerThread
    suspend fun getProduct(productId: Long): Product {
        return productDAO.getProduct(productId)
    }

    @WorkerThread
    suspend fun insertProduct(product: Product) {
        productDAO.insertProduct(product)
    }

    @WorkerThread
    suspend fun updateProduct(product: Product) {
        productDAO.updateProduct(product)
    }

    @WorkerThread
    suspend fun deleteProduct(product: Product) {
        productDAO.deleteProduct(product)
    }

    @WorkerThread
    suspend fun deleteStoreProducts(storeId: Long) {
        productDAO.getStoreProducts(storeId)
    }

    suspend fun ins(storeId: Long, store: String) {
        val apiService = ApiService()
        GlobalScope.launch(Dispatchers.Main) {
            val currentElements = apiService.getStoreProducts(store).await()
            for (cElement in currentElements) {
                insertProduct(Product(null, storeId, cElement.title, cElement.description, cElement.icon, cElement.price, cElement.discount, cElement.discountPrice, false))
            }
        }
    }

}