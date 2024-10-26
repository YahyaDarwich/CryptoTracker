package com.example.cryptotracker.core.domain.util

enum class NetworkError : Error {
    NO_INTERNET,
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN
}