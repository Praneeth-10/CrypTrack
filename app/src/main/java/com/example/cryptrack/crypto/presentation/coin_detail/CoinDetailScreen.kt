package com.example.cryptrack.crypto.presentation.coin_detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.cryptrack.crypto.presentation.coin_list.CoinListState
import com.example.cryptrack.crypto.presentation.models.CoinUi

@Composable
fun CoinDetailScreen(
    coinListState :  CoinListState,
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
        is CoinListState.Error -> TODO()
        is CoinListState.SelectedCoin -> {
            val coin: CoinUi = coinListState.selectedCoin!!
        }
    }
}