package com.example.cryptrack.crypto.presentation.coin_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptrack.R
import com.example.cryptrack.crypto.presentation.coin_detail.components.InfoCard
import com.example.cryptrack.crypto.presentation.coin_list.CoinListState
import com.example.cryptrack.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptrack.crypto.presentation.models.CoinUi
import com.example.cryptrack.crypto.presentation.models.toDisplayableNumber
import com.example.cryptrack.ui.theme.CrypTrackTheme
import com.example.cryptrack.ui.theme.greenBackground

@Composable
fun CoinDetailScreen(
    coinListState: CoinListState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    when (coinListState) {
        is CoinListState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is CoinListState.CoinList -> TODO()
        is CoinListState.SelectedCoin -> {
            val coin: CoinUi = coinListState.selectedCoin!!
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = coin.iconRes
                    ),
                    contentDescription = coin.name,
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.primary
                )


                Text(
                    text = coin.name,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Black,
                    color = contentColor
                )


                Text(
                    text = coin.symbol,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    color = contentColor
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InfoCard(
                        title = stringResource(id = R.string.market_cap),
                        formattedText = " $ ${coin.marketCapUsd.formatted}",
                        icon = ImageVector.vectorResource(R.drawable.stock)
                    )

                    InfoCard(
                        title = stringResource(id = R.string.price),
                        formattedText = " $ ${coin.priceUsd.formatted}",
                        icon = ImageVector.vectorResource(R.drawable.dollar)
                    )

                    val absoluteChangeFormatted =
                        (coin.priceUsd.value * (coin.changePercent24Hr.value / 100))
                            .toDisplayableNumber()

                    val isPositive = coin.changePercent24Hr.value > 0.0

                    val contentColor = if (isPositive) {
                        if (isSystemInDarkTheme())
                            Color.Green
                        else
                            greenBackground
                    } else {
                        MaterialTheme.colorScheme.error
                    }

                    InfoCard(
                        title = stringResource(id = R.string.change_last_24h),
                        formattedText = absoluteChangeFormatted.formatted,
                        icon = if (isPositive) {
                            ImageVector.vectorResource(R.drawable.trending)
                        } else {
                            ImageVector.vectorResource(R.drawable.trending_down)
                        },
                        contentColor = contentColor
                    )
                }

            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    CrypTrackTheme {
        CoinDetailScreen(
            coinListState = CoinListState.SelectedCoin(selectedCoin = previewCoin),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}