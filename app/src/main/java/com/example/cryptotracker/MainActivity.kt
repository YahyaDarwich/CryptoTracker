package com.example.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.CryptoTrackerTheme
import com.example.cryptotracker.core.presentation.util.ObserveAsEvents
import com.example.cryptotracker.core.presentation.util.toString
import com.example.cryptotracker.crypto.presentation.coin_detail.CoinDetailScreen
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                val viewModel = koinViewModel<CoinListViewModel>()
                val state = viewModel.state.collectAsStateWithLifecycle()
                ObserveAsEvents(events = viewModel.events) { event ->
                    when (event) {
                        is CoinListEvent.Error -> {
                            Toast.makeText(
                                this,
                                event.error.toString(this), Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (state.value.selectedCoin != null) {
                        CoinDetailScreen(
                            state = state.value,
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        CoinListScreen(
                            state = state.value,
                            modifier = Modifier.padding(innerPadding),
                            onAction = viewModel::onAction
                        )
                    }
                }
            }
        }
    }
}