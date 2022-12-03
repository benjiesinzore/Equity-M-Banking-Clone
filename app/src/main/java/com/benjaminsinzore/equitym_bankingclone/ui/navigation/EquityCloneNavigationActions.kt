/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.benjaminsinzore.equitym_bankingclone.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Help
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.benjaminsinzore.equitym_bankingclone.R

object EquityCloneRoute {
    const val INBOX = "Inbox"
    const val ARTICLES = "Articles"
    const val DM = "DirectMessages"
    const val GROUPS = "Groups"
}

data class EquityCloneTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String,
    val iconTextId: Int
)

class EquityCloneNavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: EquityCloneTopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    EquityCloneTopLevelDestination(
        route = EquityCloneRoute.INBOX,
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Default.Home,
        iconText = R.string.ic_txt_home.toString(),
        iconTextId = R.string.tab_inbox
    ),
    EquityCloneTopLevelDestination(
        route = EquityCloneRoute.ARTICLES,
        selectedIcon = Icons.Default.Calculate,
        unselectedIcon = Icons.Default.Calculate,
        iconText = R.string.ic_txt_transaction.toString(),
        iconTextId = R.string.tab_article
    ),
    EquityCloneTopLevelDestination(
        route = EquityCloneRoute.DM,
        selectedIcon = Icons.Outlined.Help,
        unselectedIcon = Icons.Outlined.Help,
        iconText = R.string.ic_txt_loan.toString(),
        iconTextId = R.string.tab_inbox
    ),
    EquityCloneTopLevelDestination(
        route = EquityCloneRoute.GROUPS,
        selectedIcon = Icons.Default.MoreVert,
        unselectedIcon = Icons.Default.MoreVert,
        iconText = R.string.ic_txt_more.toString(),
        iconTextId = R.string.tab_article
    )

)
