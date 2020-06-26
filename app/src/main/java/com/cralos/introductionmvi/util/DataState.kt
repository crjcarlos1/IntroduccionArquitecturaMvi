package com.cralos.introductionmvi.util

data class DataState<T>(
    val message: Event<String>? = null,
    val loading: Boolean? = false,
    val data: Event<T>? = null
) {

    companion object {

        /**ERROR*/
        fun <T> error(message: String): DataState<T> {
            return DataState(message = Event(message), loading = false, data = null)
        }

        /**LOADING*/
        fun <T> loading(isLoading: Boolean): DataState<T> {
            return DataState(message = null, loading = isLoading, data = null)
        }

        /**DATA*/
        fun <T> data(message: String? = null, data: T? = null): DataState<T> {
            return DataState(
                message = Event.messageEvent(message),
                loading = false,
                data = Event.dataEvent(data)
            )
        }

    }

    override fun toString(): String {
        return "DataState(message=$message, loading=$loading, data=$data)"
    }


}