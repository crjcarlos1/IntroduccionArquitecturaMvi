package com.cralos.introductionmvi.api

import androidx.lifecycle.LiveData
import com.cralos.introductionmvi.model.BlogPost
import com.cralos.introductionmvi.model.User
import com.cralos.introductionmvi.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(@Path("userId") userId: String): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogs(): LiveData<GenericApiResponse<List<BlogPost>>>

}