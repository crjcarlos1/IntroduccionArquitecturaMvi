package com.cralos.introductionmvi.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofitBuilder {

    const val BASE_URL: String = "https://open-api.xyz/"

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
    }

    val service: ApiService by lazy {
        retrofitBuilder.build().create(ApiService::class.java)
    }

}