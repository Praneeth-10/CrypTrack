package com.example.cryptrack.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.cryptrack.crypto.presentation.coin_list.components.CoinListItem
import com.example.cryptrack.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptrack.ui.theme.CrypTrackTheme

@Composable
fun CoinListScreen(
    coinListState: CoinListState,
    modifier: Modifier = Modifier
) {
    when(coinListState){
        is CoinListState.Loading -> {
            Box (
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is CoinListState.CoinList -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = coinListState.coinList){ coinUi ->
                    CoinListItem(
                        coinUi = coinUi,
                        onClick = { TODO() },
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
            coinListState.coinList
        }
        is CoinListState.Error -> TODO()

        is CoinListState.SelectedCoin -> TODO()
    }

}

@PreviewLightDark
@Composable
private fun CoinListScreenPreview () {
    CrypTrackTheme {
        CoinListScreen(
            coinListState = CoinListState.CoinList(
                coinList = (1..10).map{
                    previewCoin.copy(id = it.toString())
                }),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
            )
    }

}