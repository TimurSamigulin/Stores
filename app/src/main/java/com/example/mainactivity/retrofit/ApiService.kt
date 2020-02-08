package com.example.mainactivity.retrofit

import com.example.mainactivity.BuildConfig
import com.example.mainactivity.retrofit.models.ProductApi
import com.example.mainactivity.retrofit.models.StoreApi
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("store.json")
    fun getStores(): Deferred<List<StoreApi>>

    @GET("{store}")
    fun getStoreProducts(
        @Path("store") store: String
    ): Deferred<List<ProductApi>>

    companion object {
        operator fun invoke(): ApiService {
            val gson = GsonBuilder()
                .create()

            return Retrofit.Builder()
                // .client(okHttpClient)
                .baseUrl("https://timursamigulin.github.io/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}