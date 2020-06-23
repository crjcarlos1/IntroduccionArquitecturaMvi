package com.cralos.introductionmvi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cralos.introductionmvi.ui.main.state.MainStateEvent
import com.cralos.introductionmvi.ui.main.state.MainViewState
import com.cralos.introductionmvi.util.AbsentLiveData

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<MainViewState> = Transformations.switchMap(_stateEvent) { stateEvent ->

        stateEvent?.let { stateEvent ->
            handleStateEvent(stateEvent)
        }

    }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> {
        when (stateEvent) {
            is MainStateEvent.GetBlogPostsEvent -> {
                return AbsentLiveData.create()
            }
            is MainStateEvent.GetUserEvent -> {
                return AbsentLiveData.create()
            }
            is MainStateEvent.None -> {
                return AbsentLiveData.create()
            }
        }
    }

}