package com.cralos.introductionmvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.cralos.introductionmvi.api.MyRetrofitBuilder
import com.cralos.introductionmvi.ui.main.state.MainViewState
import com.cralos.introductionmvi.util.ApiEmptyResponse
import com.cralos.introductionmvi.util.ApiErrorResponse
import com.cralos.introductionmvi.util.ApiSuccessResponse
import com.cralos.introductionmvi.util.DataState

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return Transformations.switchMap(MyRetrofitBuilder.apiService.getBlogs()) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = DataState.data(
                                message = null,
                                data = MainViewState(blogPosts = apiResponse.body)
                            )
                        }
                        is ApiErrorResponse -> {
                            value = DataState.error(message = apiResponse.errorMessage)
                        }
                        is ApiEmptyResponse -> {
                            value = DataState.error(message = "HTTP 204. Returned nothing!")
                        }
                    }
                }
            }
        }
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return Transformations.switchMap(MyRetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = DataState.data(
                                message = null,
                                data = MainViewState(user = apiResponse.body)
                            )
                        }
                        is ApiErrorResponse -> {
                            value = DataState.error(message = apiResponse.errorMessage)
                        }
                        is ApiEmptyResponse -> {
                            value = DataState.error(message = "HTTP 204. Returned nothing!")
                        }
                    }
                }
            }
        }
    }

}