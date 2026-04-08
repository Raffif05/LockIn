package com.raffifauzan0073.assesment1.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object Setting: Screen("settingScreen")
}