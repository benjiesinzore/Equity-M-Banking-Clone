package com.benjaminsinzore.equitym_bankingclone.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.window.layout.DisplayFeature
import com.benjaminsinzore.equitym_bankingclone.ui.theme.utils.EquityCloneContentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquityCloneApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    equityCloneHomeUIState: EquityCloneHomeUIState,
    closeDetailScreen: () -> Unit = {},
    navigateToDetail: (Long, EquityCloneContentType) -> Unit = { _, _ -> }
) {


}