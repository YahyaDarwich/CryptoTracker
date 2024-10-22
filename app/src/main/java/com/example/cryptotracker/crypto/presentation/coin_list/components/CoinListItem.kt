package com.example.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.CryptoTrackerTheme
import com.example.cryptotracker.crypto.domain.Coin
import com.example.cryptotracker.crypto.presentation.models.CoinUi
import com.example.cryptotracker.crypto.presentation.models.toCoinUi

@Composable
fun CoinListItem(modifier: Modifier = Modifier, coinUi: CoinUi, onClick: () -> Unit) {
    val color = if (isSystemInDarkTheme()) Color.White else Color.Black
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = coinUi.resIcon),
            contentDescription = coinUi.name,
            modifier = Modifier.size(85.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = coinUi.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(text = coinUi.name, fontSize = 14.sp, fontWeight = FontWeight.Light, color = color)
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "$ ${coinUi.priceUsd.formatted}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )

            ChangePrice(change = coinUi.marketCapUsd)
        }
    }
}

internal val previewCoin = Coin(
    id = "bitcoin",
    name = "Bitcoin",
    symbol = "BTC",
    rank = 1,
    priceUsd = 2335.61523,
    marketCapUsd = 1.22,
    changePercent24Hr = 1.55
).toCoinUi()

@PreviewLightDark
@Composable
private fun CoinListItemPreview() {
    CryptoTrackerTheme {
        CoinListItem(
            coinUi = previewCoin,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}