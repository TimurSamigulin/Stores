package com.example.mainactivity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.util.retrofit.ApiService
import com.example.mainactivity.room.dao.StoreDAO
import com.example.mainactivity.room.database.StoreDatabase
import com.example.mainactivity.room.entity.Store
import com.example.mainactivity.room.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: StoreRepository
    val allStores: LiveData<List<Store>>
    val favStores: LiveData<List<Store>>
    val Stores = MediatorLiveData<List<Store>>()
    var currentCategory = "All"

    init {
        val storeDAO: StoreDAO = StoreDatabase.getInstance(application, viewModelScope).storeDAO()
        repository = StoreRepository(storeDAO)





        allStores = repository.allStores
        favStores = repository.favStores

        Stores.addSource(allStores) {
            if (currentCategory == "All") {it?.let { Stores.value = it }}
        }

        Stores.addSource(favStores) {
            if (currentCategory == "Fav") {it?.let { Stores.value = it }}
        }


    }

    fun changeStores(type: String) = when(type) {
        "All" -> allStores.value?.let { Stores.value = it }
        "Fav" -> favStores.value?.let { Stores.value = it }
        else -> allStores.value?.let { Stores.value = it }
    }.also { currentCategory = type }

    fun insert(store: Store) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertStore(store)
    }

    fun updateStore(store: Store) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateStore(store)
    }
}