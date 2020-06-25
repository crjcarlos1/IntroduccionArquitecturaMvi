package com.cralos.introductionmvi.api

import com.cralos.introductionmvi.model.BlogPost
import com.cralos.introductionmvi.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(@Path("userId") userId: String): User

    @GET("placeholder/blogs")
    fun getBlogs(): List<BlogPost>

}