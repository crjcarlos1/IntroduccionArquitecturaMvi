package com.cralos.introductionmvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.cralos.introductionmvi.api.MyRetrofitBuilder
import com.cralos.introductionmvi.ui.main.state.MainViewState
import com.cralos.introductionmvi.util.ApiEmptyResponse
import com.cralos.introductionmvi.util.ApiErrorResponse
import com.cralos.introductionmvi.util.ApiSuccessResponse

object Repository {

    fun getBlogPosts() : LiveData<MainViewState>{
        return Transformations.switchMap(MyRetrofitBuilder.apiService.getBlogs()){ apiResponse ->
            object : LiveData<MainViewState>(){
                override fun onActive() {
                    super.onActive()
                    when(apiResponse){
                        is ApiSuccessResponse ->{
                            value = MainViewState(blogPosts = apiResponse.body)
                        }
                        is ApiErrorResponse ->{
                            value = MainViewState() //handle error?
                        }
                        is ApiEmptyResponse ->{
                            value= MainViewState()//handle empty / error
                        }
                    }
                }
            }
        }
    }

    fun getUser(userId : String) : LiveData<MainViewState>{
        return Transformations.switchMap(MyRetrofitBuilder.apiService.getUser(userId)){ apiResponse ->
            object : LiveData<MainViewState>(){
                override fun onActive() {
                    super.onActive()
                    when(apiResponse){
                        is ApiSuccessResponse ->{
                            value = MainViewState(user = apiResponse.body)
                        }
                        is ApiErrorResponse ->{
                            value = MainViewState() //handle error?
                        }
                        is ApiEmptyResponse ->{
                            value= MainViewState()//handle empty / error
                        }
                    }
                }
            }
        }
    }

}