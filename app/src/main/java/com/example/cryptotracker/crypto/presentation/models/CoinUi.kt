package com.example.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.example.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.example.cryptotracker.crypto.domain.Coin
import java.text.NumberFormat

data class CoinUi(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val priceUsd: DisplayableNumber,
    val marketCapUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val resIcon: Int
)

data class DisplayableNumber(val value: Double, val formatted: String)

fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance().apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    return DisplayableNumber(value = this, formatted = formatter.format(this))
}

fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id,
        name = name,
        rank = rank,
        symbol = symbol,
        priceUsd = priceUsd.toDisplayableNumber(),
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        resIcon = getDrawableIdForCoin(symbol)
    )
}