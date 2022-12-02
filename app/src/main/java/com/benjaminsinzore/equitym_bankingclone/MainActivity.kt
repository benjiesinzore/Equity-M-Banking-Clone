package com.benjaminsinzore.equitym_bankingclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benjaminsinzore.equitym_bankingclone.data.local.LocalEmailsDataProvider
import com.benjaminsinzore.equitym_bankingclone.ui.EquityCloneApp
import com.benjaminsinzore.equitym_bankingclone.ui.EquityCloneHomeUIState
import com.benjaminsinzore.equitym_bankingclone.ui.EquityCloneHomeViewModel
import com.benjaminsinzore.equitym_bankingclone.ui.theme.EquityMBankingCloneTheme
import com.google.accompanist.adaptive.calculateDisplayFeatures


class MainActivity : ComponentActivity() {

    private val viewModel: EquityCloneHomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalLifecycleComposeApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EquityMBankingCloneTheme {


                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val windowSize = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this)


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    EquityCloneApp(
                        equityCloneHomeUIState = uiState,
                        windowSize = windowSize,
                        displayFeatures = displayFeatures,
                        closeDetailScreen = {
                            viewModel.closeDetailScreen()
                        },
                        navigateToDetail = { emailId, pane ->
                            viewModel.setSelectedEmail(emailId, pane)
                        }
                    )

                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun EquityCloneAppPreview() {
    EquityMBankingCloneTheme {
        EquityCloneApp(
            equityCloneHomeUIState = EquityCloneHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 700, heightDp = 500)
@Composable
fun EquityCloneAppPreviewTablet() {
    EquityMBankingCloneTheme {
        EquityCloneApp(
            equityCloneHomeUIState = EquityCloneHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowSizeClass.calculateFromSize(DpSize(700.dp, 500.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 500, heightDp = 700)
@Composable
fun EquityCloneAppPreviewTabletPortrait() {
    EquityMBankingCloneTheme {
        EquityCloneApp(
            equityCloneHomeUIState = EquityCloneHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowSizeClass.calculateFromSize(DpSize(500.dp, 700.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1100, heightDp = 600)
@Composable
fun EquityCloneAppPreviewDesktop() {
    EquityMBankingCloneTheme {
        EquityCloneApp(
            equityCloneHomeUIState = EquityCloneHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowSizeClass.calculateFromSize(DpSize(1100.dp, 600.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 600, heightDp = 1100)
@Composable
fun EquityCloneAppPreviewDesktopPortrait() {
    EquityMBankingCloneTheme {
        EquityCloneApp(
            equityCloneHomeUIState = EquityCloneHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowSizeClass.calculateFromSize(DpSize(600.dp, 1100.dp)),
            displayFeatures = emptyList(),
        )
    }
}


