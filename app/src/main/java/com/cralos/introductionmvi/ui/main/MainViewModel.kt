package com.cralos.introductionmvi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cralos.introductionmvi.model.BlogPost
import com.cralos.introductionmvi.model.User
import com.cralos.introductionmvi.repository.Repository
import com.cralos.introductionmvi.ui.main.state.MainStateEvent
import com.cralos.introductionmvi.ui.main.state.MainViewState
import com.cralos.introductionmvi.util.AbsentLiveData
import com.cralos.introductionmvi.util.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> =
        Transformations.switchMap(_stateEvent) { stateEvent ->

            stateEvent?.let { stateEvent ->
                handleStateEvent(stateEvent)
            }

        }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        when (stateEvent) {

            is MainStateEvent.GetBlogPostsEvent -> {
                return Repository.getBlogPosts()
            }

            is MainStateEvent.GetUserEvent -> {
                return Repository.getUser(stateEvent.userId)
            }

            is MainStateEvent.None -> {
                return AbsentLiveData.create()
            }

        }
    }

    fun setBlogListData(blogList: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogList
        _viewState.value = update
    }

    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value?.let {
            it
        } ?: MainViewState()

        return value
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

}