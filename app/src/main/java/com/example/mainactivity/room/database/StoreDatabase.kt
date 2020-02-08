package com.example.mainactivity.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mainactivity.room.dao.ProductDAO
import com.example.mainactivity.room.dao.StoreDAO
import com.example.mainactivity.room.entity.Product
import com.example.mainactivity.room.entity.Store
import kotlinx.coroutines.CoroutineScope

@Database(entities = [(Store::class), (Product::class)], version = 1)
abstract class StoreDatabase: RoomDatabase() {
    abstract fun storeDAO(): StoreDAO
    abstract fun productDAO(): ProductDAO

    companion object {
        @Volatile
        private var INSTANCE: StoreDatabase ?= null

        fun getInstance(context: Context, scope: CoroutineScope): StoreDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoreDatabase::class.java,
                    "stores.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}