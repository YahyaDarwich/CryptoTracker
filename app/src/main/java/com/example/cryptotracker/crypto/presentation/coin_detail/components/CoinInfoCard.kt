package com.example.cryptotracker.crypto.presentation.coin_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.CryptoTrackerTheme
import com.example.cryptotracker.R

@Composable
fun CoinInfoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    formattedTextPrice: String,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    val defaultFontStyle = LocalTextStyle.current.copy(
        color = contentColor,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center
    )

    Card(
        modifier = modifier
            .shadow(
                elevation = 15.dp,
                shape = RectangleShape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary
            )
            .padding(8.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = contentColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        AnimatedContent(
            targetState = icon,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            label = "IconAnimation"
        ) {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .size(70.dp),
                tint = contentColor
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        AnimatedContent(
            targetState = formattedTextPrice,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            label = "TextAnimation"
        ) {
            Text(
                text = it,
                style = defaultFontStyle,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 15.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 15.dp)
                .padding(bottom = 10.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun CoinInfoCardPreview() {
    CryptoTrackerTheme {
        CoinInfoCard(
            icon = ImageVector.vectorResource(id = R.drawable.maha),
            title = "test",
            formattedTextPrice = "6,12.122"
        )
    }
}