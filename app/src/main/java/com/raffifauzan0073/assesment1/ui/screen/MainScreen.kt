package com.raffifauzan0073.assesment1.ui.screen

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
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
import com.raffifauzan0073.assesment1.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
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
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {

    val timerPickerState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 25,
        is24Hour = true
    )

    var timeLeft by remember { mutableLongStateOf(0L) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = String.format(
        Locale.getDefault(),
        "%02d:%02d",
        minutes,
        seconds
    )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.app_intro),
                style = MaterialTheme.typography.bodyLarge
            )

            TimeInput(state = timerPickerState)

            Text(
                text = formattedTime,
                style = MaterialTheme.typography.displayLarge
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val hour = timerPickerState.hour
                    val minute = timerPickerState.minute

                    val totalTimeMillis = (hour * 60 + minute) * 60 * 1000L

                    timer?.cancel()

                    timer = object : CountDownTimer(totalTimeMillis, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            timeLeft = millisUntilFinished / 1000
                        }

                        override fun onFinish() {
                            timeLeft = 0
                        }
                    }.start()
                }
            ) {
                Text("Start")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    timer?.cancel()
                    timeLeft = 0
                }
            ) {
                Text("Reset")
            }
        }
    }
