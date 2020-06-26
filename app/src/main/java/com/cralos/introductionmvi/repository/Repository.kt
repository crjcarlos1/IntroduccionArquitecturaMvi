package com.cralos.introductionmvi.repository

import androidx.lifecycle.LiveData
import com.cralos.introductionmvi.api.MyRetrofitBuilder
import com.cralos.introductionmvi.model.BlogPost
import com.cralos.introductionmvi.model.User
import com.cralos.introductionmvi.ui.main.state.MainViewState
import com.cralos.introductionmvi.util.ApiSuccessResponse
import com.cralos.introductionmvi.util.DataState
import com.cralos.introductionmvi.util.GenericApiResponse

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(data = MainViewState(blogPosts = response.body))
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return MyRetrofitBuilder.apiService.getBlogs()
            }

        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(data = MainViewState(null, user = response.body))
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return MyRetrofitBuilder.apiService.getUser(userId)
            }

        }.asLiveData()
    }

}