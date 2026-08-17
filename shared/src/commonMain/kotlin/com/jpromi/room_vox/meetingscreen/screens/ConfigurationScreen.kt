package com.jpromi.room_vox.meetingscreen.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jpromi.room_vox.meetingscreen.AppSettings
import com.jpromi.room_vox.meetingscreen.network.ServerConnectionChecker
import com.jpromi.room_vox.meetingscreen.network.ServerConnectionResult
import kotlinx.coroutines.launch

@Composable
fun ConfigurationScreen(
    onGoBack: () -> Unit,
    appSettings: AppSettings = remember { AppSettings() },
    serverConnectionChecker: ServerConnectionChecker = remember { ServerConnectionChecker() },
) {
    var serverUrl by remember { mutableStateOf(appSettings.serverUrl) }
    var accessToken by remember { mutableStateOf(appSettings.accessToken) }
    var serverCheckResult by remember { mutableStateOf<ServerConnectionResult?>(null) }
    var isCheckingServer by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Konfiguration")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = serverUrl,
            onValueChange = {
                serverUrl = it
                appSettings.serverUrl = it
                serverCheckResult = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Server URL") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = accessToken,
            onValueChange = {
                accessToken = it
                appSettings.accessToken = it
                serverCheckResult = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Access Token") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    isCheckingServer = true
                    serverCheckResult = null
                    serverCheckResult = serverConnectionChecker.check(
                        serverUrl = serverUrl,
                        accessToken = accessToken,
                    )
                    isCheckingServer = false
                }
            },
            enabled = !isCheckingServer,
        ) {
            Text(if (isCheckingServer) "Pruefe..." else "Server pruefen")
        }

        serverCheckResult?.let { result ->
            Spacer(modifier = Modifier.height(8.dp))

            val message = when (result) {
                is ServerConnectionResult.Success -> result.message
                is ServerConnectionResult.Error -> result.message
            }

            Text(message)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onGoBack) {
            Text("Zurueck")
        }
    }
}
