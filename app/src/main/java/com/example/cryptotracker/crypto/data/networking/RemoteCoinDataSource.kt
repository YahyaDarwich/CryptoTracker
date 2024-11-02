package com.example.cryptotracker.crypto.data.networking

import com.example.cryptotracker.core.data.networking.constructUrl
import com.example.cryptotracker.core.data.networking.safeCall
import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.core.domain.util.Result
import com.example.cryptotracker.core.domain.util.map
import com.example.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import com.example.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.example.cryptotracker.crypto.domain.Coin
import com.example.cryptotracker.crypto.domain.CoinDataSource
import com.example.cryptotracker.crypto.domain.CoinPrice
import com.example.cryptotracker.crypto.mappers.toCoin
import com.example.cryptotracker.crypto.mappers.toCoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCoinDataSource(val httpClient: HttpClient) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> { httpClient.get(urlString = constructUrl("/assets")) }.map { coinList ->
            coinList.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant().toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant().toEpochMilli()

        return safeCall<CoinHistoryDto> {
            httpClient.get(urlString = constructUrl("/assets/$coinId/history")) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { coinHistory ->
            coinHistory.data.map { it.toCoinPrice() }
        }
    }
}