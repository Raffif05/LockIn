package com.raffifauzan0073.assesment1.ui.screen

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.raffifauzan0073.assesment1.R
import com.raffifauzan0073.assesment1.navigation.Screen
import java.util.Locale
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import com.raffifauzan0073.assesment1.model.Kegiatan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, initialMinute: Int = 25) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Setting.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = stringResource(R.string.about),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding), initialMinute = initialMinute)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier = Modifier, initialMinute: Int) {

    var timeLeft by rememberSaveable { mutableLongStateOf(0L) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    val kegiatanList = listOf(
        Kegiatan(stringResource(R.string.belajar), R.drawable.reading),
        Kegiatan(stringResource(R.string.istirahat), R.drawable.coffee)
    )
    var isStarted by rememberSaveable { mutableStateOf(false) }
    var selectedKegiatan by remember { mutableStateOf(kegiatanList[0]) }
    var expanded by remember { mutableStateOf(false) }
    var isRunning by rememberSaveable { mutableStateOf(false) }

    val hours = timeLeft / 3600
    val minutes = (timeLeft % 3600) / 60
    val seconds = timeLeft % 60
    val formattedTime = String.format(
        Locale.getDefault(),
        "%02d:%02d:%02d",
        hours,
        minutes,
        seconds
    )

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.app_intro),
                style = MaterialTheme.typography.bodyLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = stringResource(R.string.mode_set),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.width(12.dp))

                Box {
                    TextButton(onClick = { expanded = true }) {
                        Text(selectedKegiatan.nama)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        kegiatanList.forEach { kegiatan ->
                            DropdownMenuItem(
                                text = { Text(kegiatan.nama) },
                                onClick = {
                                    selectedKegiatan = kegiatan
                                    expanded = false

                                    timer?.cancel()
                                    timeLeft = 0
                                    isRunning = false
                                    isStarted = false
                                }
                            )
                        }
                    }
                }
            }

            if (isStarted) {
                Image(
                    painter = painterResource(id = selectedKegiatan.imageResId),
                    contentDescription = selectedKegiatan.nama,
                    modifier = Modifier.size(200.dp)
                )
            }


            Text(
                text = formattedTime,
                style = MaterialTheme.typography.displayLarge
            )



            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (isRunning) {
                        timer?.cancel()
                        isRunning = false
                    } else {
                        val totalTimeMillis = if (timeLeft > 0)
                            timeLeft * 1000
                        else
                            initialMinute * 60 * 1000L

                        timer?.cancel()

                        timer = object : CountDownTimer(totalTimeMillis, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                timeLeft = millisUntilFinished / 1000
                            }

                            override fun onFinish() {
                                timeLeft = 0
                                isRunning = false
                                isStarted = false
                            }
                        }.start()

                        isRunning = true
                        isStarted = true
                    }
                }
            ) {
                Text(if (isRunning) stringResource(R.string.pause) else stringResource(R.string.start))
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    timer?.cancel()
                    timeLeft = 0
                    isRunning = false
                    isStarted = false
                }
            ) {
                Text(stringResource(R.string.reset))
            }
        }
    }
