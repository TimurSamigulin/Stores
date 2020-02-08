package com.example.mainactivity.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mainactivity.room.entity.Store

@Dao
interface StoreDAO {
    @Insert
    suspend fun insert(store: Store)

    @Insert
    suspend fun insertStores(stores: List<Store>)

    @Update
    suspend fun updateStore(store: Store)

    @Delete
    suspend fun deleteStore(store: Store)

    @Query("SELECT * FROM store")
    fun getAllStores(): LiveData<List<Store>>

    @Query("SELECT * FROM store WHERE favourite = 1")
    fun getFavStores(): LiveData<List<Store>>

    @Query("SELECT COUNT(*) FROM Store")
    fun getCount(): LiveData<Int>
}