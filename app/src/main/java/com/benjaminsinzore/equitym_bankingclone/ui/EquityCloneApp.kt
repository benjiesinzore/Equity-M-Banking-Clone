package com.benjaminsinzore.equitym_bankingclone.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.benjaminsinzore.equitym_bankingclone.ui.navigation.*
import com.benjaminsinzore.equitym_bankingclone.ui.theme.EquityCloneHomeUIState
import com.benjaminsinzore.equitym_bankingclone.ui.theme.utils.*
import kotlinx.coroutines.launch


@Composable
fun EquityCloneApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    equityCloneHomeUIState: EquityCloneHomeUIState,
    closeDetailScreen: () -> Unit = {},
    navigateToDetail: (Long, EquityCloneContentType) -> Unit = { _, _ -> }
) {

    /**
     * This will help us select type of navigation and content type depending on window size and
     * fold state of the device.
     */
    val navigationType: EquityCloneNavigationType
    val contentType: EquityCloneContentType

    /**
     * We are using display's folding features to map the device postures a fold is in.
     * In the state of folding device If it's half fold in BookPosture we want to avoid content
     * at the crease/hinge
     */

    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }


    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = EquityCloneNavigationType.BOTTOM_NAVIGATION
            contentType = EquityCloneContentType.SINGLE_PANE
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = EquityCloneNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                EquityCloneContentType.DUAL_PANE
            } else {
                EquityCloneContentType.SINGLE_PANE
            }
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                EquityCloneNavigationType.NAVIGATION_RAIL
            } else {
                EquityCloneNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = EquityCloneContentType.DUAL_PANE
        }
        else -> {
            navigationType = EquityCloneNavigationType.BOTTOM_NAVIGATION
            contentType = EquityCloneContentType.SINGLE_PANE
        }
    }

    /**
     * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
     * ergonomics and reachability depending upon the height of the device.
     */

    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            EquityCloneNavigationContentPosition.TOP
        }
        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded -> {
            EquityCloneNavigationContentPosition.CENTER
        }
        else -> {
            EquityCloneNavigationContentPosition.TOP
        }
    }

    EquityNavigationWrapper(
        navigationType = navigationType,
        contentType = contentType,
        displayFeatures = displayFeatures,
        navigationContentPosition = navigationContentPosition,
        replyHomeUIState = equityCloneHomeUIState,
        closeDetailScreen = closeDetailScreen,
        navigateToDetail = navigateToDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquityNavigationWrapper(
    navigationType: EquityCloneNavigationType,
    contentType: EquityCloneContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: EquityCloneNavigationContentPosition,
    replyHomeUIState: EquityCloneHomeUIState,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, EquityCloneContentType) -> Unit) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        EquityCloneNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: EquityCloneRoute.INBOX


    if (navigationType == EquityCloneNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        // TODO check on custom width of PermanentNavigationDrawer: b/232495216
        PermanentNavigationDrawer(drawerContent = {
            PermanentNavigationDrawerContent(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigationActions::navigateTo,
            )
        }) {
            EquityCloneAppContent(
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                replyHomeUIState = replyHomeUIState,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail
            ) {
                scope.launch {
                    drawerState.open()
                }
            }
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalNavigationDrawerContent(
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToTopLevelDestination = navigationActions::navigateTo,
                    onDrawerClicked = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            },
            drawerState = drawerState
        ) {
            EquityCloneAppContent(
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                replyHomeUIState = replyHomeUIState,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail
            ) {
                scope.launch {
                    drawerState.open()
                }
            }
        }
    }

}

@Composable
fun EquityCloneAppContent(
    modifier: Modifier = Modifier,
    navigationType: EquityCloneNavigationType,
    contentType: EquityCloneContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: EquityCloneNavigationContentPosition,
    replyHomeUIState: EquityCloneHomeUIState,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (EquityCloneTopLevelDestination) -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, EquityCloneContentType) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {

    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == EquityCloneNavigationType.NAVIGATION_RAIL) {
            EquityCloneNavigationRail(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                onDrawerClicked = onDrawerClicked,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            EquityCloneNavHost(
                navController = navController,
                contentType = contentType,
                displayFeatures = displayFeatures,
                replyHomeUIState = replyHomeUIState,
                navigationType = navigationType,
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail,
                modifier = Modifier.weight(1f),
            )
            AnimatedVisibility(visible = navigationType == EquityCloneNavigationType.BOTTOM_NAVIGATION) {
                EquityCloneBottomNavigationBar(
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}


@Composable
private fun EquityCloneNavHost(
    navController: NavHostController,
    contentType: EquityCloneContentType,
    displayFeatures: List<DisplayFeature>,
    replyHomeUIState: EquityCloneHomeUIState,
    navigationType: EquityCloneNavigationType,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, EquityCloneContentType) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = EquityCloneRoute.INBOX,
    ) {
        composable(EquityCloneRoute.INBOX) {
            EquityCloneInboxScreen(
                contentType = contentType,
                replyHomeUIState = replyHomeUIState,
                navigationType = navigationType,
                displayFeatures = displayFeatures,
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail,
            )
        }
        composable(EquityCloneRoute.DM) {
            EmptyComingSoon()
        }
        composable(EquityCloneRoute.ARTICLES) {
            EmptyComingSoon()
        }
        composable(EquityCloneRoute.GROUPS) {
            EmptyComingSoon()
        }
    }
}
