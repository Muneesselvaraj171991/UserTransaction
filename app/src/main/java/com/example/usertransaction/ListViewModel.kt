package com.example.usertransaction

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usertransaction.WhileUiSubscribed
import com.example.usertransaction.core.Result
import com.example.usertransaction.core.asResult
import com.example.usertransaction.data.model.UserTransaction
import com.example.usertransaction.data.repo.UserTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListViewUiState(
  val userTransactionUiState: UserTransactionUiState,
  val isRefreshing: Boolean,
  val isError: Boolean
)

@Immutable
sealed interface UserTransactionUiState {
  data class Success(val userTransactions: List<UserTransaction>) : UserTransactionUiState
  object Error : UserTransactionUiState
  object Loading : UserTransactionUiState
}


@HiltViewModel
class ListViewModel @Inject constructor(
  private val userTransactionRepository: UserTransactionRepository
) : ViewModel() {

  val exceptionHandler = CoroutineExceptionHandler { context, exception ->
    viewModelScope.launch {
      isError.emit(true)
    }
  }
  var userTransact by
    mutableStateOf<UserTransaction?>(null)
    private set

  private val userTransactions: Flow<Result<List<UserTransaction>>> =
    userTransactionRepository.getUserTransactions().asResult()



  fun addUserTransact(userTransaction: UserTransaction) {
  userTransact = userTransaction
  }


  private val isRefreshing = MutableStateFlow(false)

  private val isError = MutableStateFlow(false)

  val uiState: StateFlow<ListViewUiState> = combine(
    userTransactions,
    isRefreshing,
    isError
  ) { userTransactionResult, refreshing, errorOccurred ->

    val topRated: UserTransactionUiState = when (userTransactionResult) {
      is Result.Success -> UserTransactionUiState.Success(userTransactionResult.data)
      is Result.Loading -> UserTransactionUiState.Loading
      is Result.Error -> UserTransactionUiState.Error
    }


    ListViewUiState(
      topRated,
      refreshing,
      errorOccurred
    )
  }
    .stateIn(
      scope = viewModelScope,
      started = WhileUiSubscribed,
      initialValue = ListViewUiState(
        UserTransactionUiState.Loading,
        isRefreshing = false,
        isError = false
      )
    )

  fun  onRefresh() {
    viewModelScope.launch(exceptionHandler) {
      with(userTransactionRepository) {
        val refreshUserTransactionDeferred = async { refreshUserTransaction() }
        isRefreshing.emit(true)
        try {
          awaitAll(
            refreshUserTransactionDeferred,
          )
        } finally {
          isRefreshing.emit(false)
        }
      }
    }
  }

  fun onErrorConsumed() {
    viewModelScope.launch {
      isError.emit(false)
    }
  }
}