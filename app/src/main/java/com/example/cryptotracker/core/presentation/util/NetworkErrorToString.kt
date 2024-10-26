package com.example.cryptotracker.core.presentation.util

import android.content.Context
import com.example.cryptotracker.R
import com.example.cryptotracker.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.NO_INTERNET -> R.string.error_no_internet_connection
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.UNKNOWN -> R.string.error_unknown
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_to_many_requets
    }

    return context.getString(resId)
}