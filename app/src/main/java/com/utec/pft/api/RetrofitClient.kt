package com.utec.pft.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    //Esta URL hay que cambiarla si el servidor no está corriendo local
    private const val BASE_URL =
        "https://f407-2800-a4-26b7-3f00-c060-6cde-224e-f572.ngrok-free.app/PFT_Web/rest/"

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}