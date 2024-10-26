package com.example.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.CryptoTrackerTheme
import com.example.cryptotracker.crypto.presentation.models.DisplayableNumber

@Composable
fun ChangePrice(modifier: Modifier = Modifier, change: DisplayableNumber) {
    val isMin = change.value < 0.0
    val backgroundColor =
        if (isMin) MaterialTheme.colorScheme.errorContainer else Color(0xFF028B02)
    val contentColor =
        if (isMin) MaterialTheme.colorScheme.onErrorContainer else Color.Green

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .padding(2.dp)
    ) {
        Image(
            imageVector = if (isMin) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            colorFilter = ColorFilter.tint(color = contentColor)
        )

        Text(
            text = "${change.formatted} %",
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = contentColor
        )
    }
}

@PreviewLightDark
@Composable
private fun ChangePricePreview() {
    CryptoTrackerTheme {
        ChangePrice(change = DisplayableNumber(value = 2.33, formatted = "2.33"))
    }
}