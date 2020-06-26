package com.cralos.introductionmvi.ui

import com.cralos.introductionmvi.util.DataState

interface DataStateListener{
    fun onDataStateChange(dataState : DataState<*>?)
}