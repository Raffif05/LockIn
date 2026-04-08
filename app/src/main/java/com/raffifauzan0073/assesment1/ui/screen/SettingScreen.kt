package com.raffifauzan0073.assesment1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.raffifauzan0073.assesment1.R
import com.raffifauzan0073.assesment1.ui.theme.Assesment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavHostController,
    onThemeChange: (Boolean) -> Unit,
    isDarkTheme: Boolean
) {
    val timerPickerState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 25,
        is24Hour = true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary)
                    }
                },
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_setting),
                style = MaterialTheme.typography.bodyLarge
                )

            Text(
                text = stringResource(R.string.set_time),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )

            TimeInput(state = timerPickerState)
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val minute = timerPickerState.minute

                    navController.navigate("mainScreen/$minute")
                }
            ) {
                Text(stringResource(R.string.simpan))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.theme))

                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { onThemeChange(it) }
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SettingScreenPreview() {
    Assesment1Theme() {
        SettingScreen(rememberNavController(), isDarkTheme = false, onThemeChange = {})
    }
}