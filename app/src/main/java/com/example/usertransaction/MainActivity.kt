package com.example.usertransaction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.usertransaction.compose.UserTransactionListDetail
import com.example.usertransaction.ui.theme.UserTransactionTheme
import com.google.accompanist.adaptive.calculateDisplayFeatures
import dagger.hilt.android.AndroidEntryPoint
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listViewModel: ListViewModel by  viewModels()

        setContent {
            UserTransactionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserTransactionListDetail(
                        windowSizeClass = calculateWindowSizeClass(this),
                        displayFeatures = calculateDisplayFeatures(this),listViewModel
                    )

                }
            }
            BackHandler {
                finishAffinity()
            }

        }
    }
}
