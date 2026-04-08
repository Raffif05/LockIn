package com.raffifauzan0073.assesment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.raffifauzan0073.assesment1.navigation.SetupNavGraph
import com.raffifauzan0073.assesment1.ui.theme.Assesment1Theme
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false)  }

            Assesment1Theme(darkTheme = isDarkTheme) {
                SetupNavGraph(
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { isDarkTheme = it}
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assesment1Theme {
        SetupNavGraph(
            isDarkTheme = false,
            onThemeChange = {}
        )
    }
}