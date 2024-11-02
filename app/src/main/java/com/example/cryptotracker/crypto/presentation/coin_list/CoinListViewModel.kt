package com.example.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.core.domain.util.onError
import com.example.cryptotracker.core.domain.util.onSuccess
import com.example.cryptotracker.crypto.domain.CoinDataSource
import com.example.cryptotracker.crypto.presentation.models.CoinUi
import com.example.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class CoinListViewModel(private val coinDataSource: CoinDataSource) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()
        .onStart { loadCoins() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), CoinListState())

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    private suspend fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource.getCoins()
                .onSuccess { coins ->
                    _state.update { coinListState ->
                        coinListState.copy(
                            isLoading = false,
                            coins = coins.map { it.toCoinUi() })
                    }
                }.onError {
                    _state.update { it.copy(isLoading = false) }
                    _events.send(CoinListEvent.Error(it))
                }
        }
    }

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> selectCoin(action.coinUi)
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update { it.copy(selectedCoin = coinUi) }

        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUi.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            ).onSuccess {
                println(it)
            }.onError {
                _events.send(CoinListEvent.Error(it))
            }
        }
    }
}