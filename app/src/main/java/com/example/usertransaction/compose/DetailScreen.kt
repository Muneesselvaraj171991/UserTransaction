package com.example.usertransaction.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.usertransaction.ListViewModel
import com.example.usertransaction.R


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel,
) {
    DetailScreenShow(modifier = modifier,viewModel)
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
 fun DetailScreenShow(
    modifier: Modifier = Modifier,viewModel: ListViewModel
) {
    val userTransaction = viewModel.userTransact

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.screen_detail))},
                modifier = Modifier.fillMaxWidth())
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (userTransaction != null) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    text = userTransaction.username,
                    style = MaterialTheme.typography.headlineMedium
                )
            } else {
                Text(
                    text = stringResource(R.string.placeholder)
                )
            }

        }

                   
    }
    
}



