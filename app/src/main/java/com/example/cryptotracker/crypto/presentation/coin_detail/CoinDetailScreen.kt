@file:OptIn(ExperimentalLayoutApi::class)

package com.example.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.CryptoTrackerTheme
import com.example.cryptotracker.R
import com.example.cryptotracker.crypto.presentation.coin_detail.components.CoinInfoCard
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.example.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptotracker.crypto.presentation.models.toDisplayableNumber

@Composable
fun CoinDetailScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectedCoin != null) {
        val coin = state.selectedCoin
        val contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.resIcon),
                contentDescription = coin.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = coin.name,
                color = contentColor,
                fontWeight = FontWeight.Black,
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = coin.symbol,
                color = contentColor,
                fontWeight = FontWeight.Light,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CoinInfoCard(
                    icon = ImageVector.vectorResource(id = R.drawable.stock),
                    title = stringResource(
                        id = R.string.market_cap
                    ),
                    formattedTextPrice = "$ ${coin.marketCapUsd.formatted}"
                )

                CoinInfoCard(
                    icon = ImageVector.vectorResource(id = R.drawable.dollar),
                    title = stringResource(
                        id = R.string.price
                    ),
                    formattedTextPrice = "$ ${coin.priceUsd.formatted}"
                )

                val changePrice =
                    (coin.priceUsd.value * (coin.changePercent24Hr.value / 100)).toDisplayableNumber()
                val isPositive = coin.changePercent24Hr.value > 0.0
                val contentColor = if (isPositive) Color.Green else MaterialTheme.colorScheme.error

                CoinInfoCard(
                    icon = if (isPositive) ImageVector.vectorResource(id = R.drawable.trending)
                    else ImageVector.vectorResource(id = R.drawable.trending_down),
                    title = stringResource(
                        id = R.string.change_last_24h
                    ),
                    formattedTextPrice = changePrice.formatted,
                    contentColor = contentColor
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    CryptoTrackerTheme {
        CoinDetailScreen(state = CoinListState(selectedCoin = previewCoin))
    }
}