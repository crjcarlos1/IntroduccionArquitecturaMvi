package com.cralos.introductionmvi.util

data class DataState<T>(
    val message: String? = null,
    val loading: Boolean? = false,
    val data: T? = null
) {

    companion object {

        /**ERROR*/
        fun <T> error(message: String): DataState<T> {
            return DataState(message = message, loading = false, data = null)
        }

        /**LOADING*/
        fun <T> loading(isLoading: Boolean): DataState<T> {
            return DataState(message = null, loading = isLoading, data = null)
        }

        /**DATA*/
        fun <T> data(message: String? = null, data: T? = null): DataState<T> {
            return DataState(message = message, loading = false, data = data)
        }

    }

    override fun toString(): String {
        return "DataState(message=$message, loading=$loading, data=$data)"
    }


}