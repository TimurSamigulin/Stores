package com.example.mainactivity.room.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.mainactivity.retrofit.ApiService
import com.example.mainactivity.room.dao.StoreDAO
import com.example.mainactivity.room.database.ProductDatabase
import com.example.mainactivity.room.entity.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StoreRepository(private val storeDAO: StoreDAO) {
    val allStores: LiveData<List<Store>> = storeDAO.getAllStores()
    val favStores: LiveData<List<Store>> = storeDAO.getFavStores()
    val count: LiveData<Int> = storeDAO.getCount()

    init {

            val apiService = ApiService()
            GlobalScope.launch(Dispatchers.Main) {
                val currentElements = apiService.getStores().await()
                for (cElement in currentElements) {
                    insertStore(
                        Store(
                            null,
                            cElement.title,
                            cElement.description,
                            cElement.icon,
                            false,
                            cElement.x,
                            cElement.y,
                            cElement.apiHref
                        )

                    )
                }
            }

    }

    @WorkerThread
    suspend fun insertStore(store: Store) {
        storeDAO.insert(store)
    }

    @WorkerThread
    suspend fun updateStore(store: Store) {
        storeDAO.updateStore(store)
    }
}