package com.raffifauzan0073.assesment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raffifauzan0073.assesment1.ui.screen.MainScreen
import com.raffifauzan0073.assesment1.ui.screen.SettingScreen
import com.raffifauzan0073.assesment1.ui.screen.AboutScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }

        composable(route = Screen.Setting.route) {
            SettingScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange)
        }

        composable("mainScreen/{minute}") { backStackEntry ->
            val minute = backStackEntry.arguments?.getString("minute") ?: "25"
            MainScreen(navController, minute.toInt())
        }

        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
    }
}