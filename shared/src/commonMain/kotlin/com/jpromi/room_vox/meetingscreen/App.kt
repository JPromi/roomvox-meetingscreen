package com.jpromi.room_vox.meetingscreen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.jpromi.room_vox.meetingscreen.screens.ConfigurationScreen
import com.jpromi.room_vox.meetingscreen.screens.HomeScreen
import com.jpromi.room_vox.meetingscreen.screens.RoomScreen

private enum class Screen {
    Home,
    Configuration,
    Room
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf(Screen.Home) }

        when (currentScreen) {
            Screen.Home -> HomeScreen(
                onOpenConfiguration = { currentScreen = Screen.Configuration }
            )
            Screen.Configuration -> ConfigurationScreen(
                onGoBack = { currentScreen = Screen.Home }
            )
            Screen.Room -> RoomScreen(
                onOpenConfiguration = { currentScreen = Screen.Configuration }
            )
        }
    }
}