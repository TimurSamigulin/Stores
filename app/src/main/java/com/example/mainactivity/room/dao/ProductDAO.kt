package com.example.mainactivity.room.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.mainactivity.room.entity.Product

@Dao
interface ProductDAO {
    @Insert
    suspend fun insertProduct(product: Product)

    @Insert
    suspend fun insertProducts(products: List<Product>)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM product WHERE store_id = :storeId")
    suspend fun deleteStoreProducts(storeId: Long)

    @Query("SELECT * FROM product")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE id = :product_id")
    fun getProduct(product_id: Long): Product

    @Query("SELECT * FROM product WHERE favourite = 1")
    fun getFavProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE store_id = :storeId")
    fun getStoreProducts(storeId: Long): List<Product>
}