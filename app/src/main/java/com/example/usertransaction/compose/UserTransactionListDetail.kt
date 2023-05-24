package com.example.usertransaction.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.example.usertransaction.ListViewModel
import com.example.usertransaction.ListViewUiState
import com.example.usertransaction.R
import com.example.usertransaction.UserTransactionUiState
import com.example.usertransaction.data.model.UserTransaction
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    isDetailOpen: (Boolean) -> Unit,
    listViewModel: ListViewModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState: ListViewUiState by listViewModel.uiState.collectAsState()
    val errorMessage = stringResource(id = R.string.error_text)
    val okText = stringResource(id = R.string.ok_button_text)

    if (uiState.isError) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = okText
            )
            listViewModel.onErrorConsumed()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.screen_list))},
                modifier = Modifier.fillMaxWidth())
        }

    ) { padding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isRefreshing),
            onRefresh = { listViewModel.onRefresh() },
            modifier = Modifier
                .padding(padding)
        ) {
            UserTransactionList(uiState.userTransactionUiState,listViewModel,isDetailOpen)

        }
    }
}



@Composable
fun UserTransactionListDetail(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    listViewModel: ListViewModel

) {
    val widthSizeClass by rememberUpdatedState(windowSizeClass.widthSizeClass)
    var isDetailOpen by rememberSaveable { mutableStateOf(false) }

    ListDetail(
        isDetailOpen = isDetailOpen,
        setIsDetailOpen = { isDetailOpen = it },
        showListAndDetail =
        when (widthSizeClass) {
            WindowWidthSizeClass.Compact -> false
            WindowWidthSizeClass.Medium -> true
            WindowWidthSizeClass.Expanded -> true
            else -> true
        },
        list = { isDetailVisible ->
            ListScreen(
                isDetailOpen = { isDetailOpen = it },
                listViewModel=  listViewModel)
        },
        detail = { isListVisible ->
            DetailScreen(
                modifier = if (isListVisible) {
                    Modifier.padding(start = 8.dp)
                } else {
                    Modifier
                },listViewModel
            )
        },
        twoPaneStrategy = HorizontalTwoPaneStrategy(
            splitFraction = 1f / 3f,
        ),
        displayFeatures = displayFeatures,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}


@Composable
fun UserTransactionList(
    uiState: UserTransactionUiState, listViewModel: ListViewModel,isDetailOpen: (Boolean) -> Unit
) {

    LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when (uiState) {
                UserTransactionUiState.Error -> {
                    usertranscationErrorText(R.string.error_text)
                }

                UserTransactionUiState.Loading -> {
                    item {
                        LoadingIndicator()
                    }
                }

                is UserTransactionUiState.Success -> {

                    items(uiState.userTransactions) { userTransaction ->
                        DisplayList(userTransaction = userTransaction, onItemClick = {
                            listViewModel.addUserTransact(userTransaction = userTransaction)
                            isDetailOpen(true)
                        })

                    }
                }
            }
        }
    }



fun LazyListScope.usertranscationErrorText(
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    item {
        ErrorText(
            title = title,
            modifier = modifier
        )
    }
}



@Composable
fun ErrorText(
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = title),
        modifier = modifier.padding(vertical = 24.dp)
    )
}


@Composable
fun DisplayList(userTransaction: UserTransaction, onItemClick:(UserTransaction) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth().padding(5.dp)
        .clickable {
            onItemClick(userTransaction)
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ), elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

    ) {
        Text(
            textAlign = TextAlign.Center,
            text = userTransaction.username,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
    }

}



@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(color = Color.LightGray)
    }
}






