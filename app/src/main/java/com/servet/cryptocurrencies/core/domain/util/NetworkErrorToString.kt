package com.servet.cryptocurrencies.core.domain.util

import android.content.Context
import com.servet.cryptocurrencies.R

fun NetworkError.toString(context: Context): String {
    val resId = when(this) {
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.SERVER_ERROR -> R.string.error_server_error
        NetworkError.SERIALIZATION_ERROR -> R.string.error_serialization
        NetworkError.UNKNOWN_ERROR -> R.string.error_unknown
    }

    return context.getString(resId)
}