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

package com.benjaminsinzore.equitym_bankingclone.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.benjaminsinzore.equitym_bankingclone.R
import com.benjaminsinzore.equitym_bankingclone.ui.theme.EquityMBankingCloneTheme
import com.benjaminsinzore.equitym_bankingclone.ui.utils.EquityCloneContentType
import com.benjaminsinzore.equitym_bankingclone.ui.utils.EquityCloneNavigationType

@Composable
fun EquityClonePeopleScreen(
    modifier: Modifier = Modifier,
    contentType: EquityCloneContentType,
    navigationType: EquityCloneNavigationType
) {


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.group_screen),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(id = R.string.empty_screen_subtitle),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )
    }


    if (contentType == EquityCloneContentType.DUAL_PANE) {

        //Contents
    } else {
        Box(modifier = modifier.fillMaxSize()) {

            // When we have bottom navigation we show FAB at the bottom end.
            if (navigationType == EquityCloneNavigationType.BOTTOM_NAVIGATION) {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EquityClonePeoplePreview() {

    EquityMBankingCloneTheme {
//        EquityClonePeopleScreen(
//            contentType = EquityCloneContentType,
//            navigationType = EquityCloneNavigationType
//        )
    }

}
