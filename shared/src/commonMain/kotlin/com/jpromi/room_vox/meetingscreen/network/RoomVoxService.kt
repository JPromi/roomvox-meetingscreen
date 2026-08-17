package com.jpromi.room_vox.meetingscreen.network

import com.jpromi.room_vox.meetingscreen.AppSettings
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get

class RoomVoxService(
    private val appSettings: AppSettings = AppSettings(),
) {
    val baseUrl: String
        get() = appSettings.serverUrl.toHttpBaseUrl() + "/apps/roomvox/api/v1"

    suspend fun getRooms(): List<String> {
        val client = HttpClientFactory.create()
        try {
            val response = client.get("$baseUrl/rooms") {
                if (appSettings.accessToken.isNotBlank()) {
                    bearerAuth(appSettings.accessToken.trim())
                }
            }
            return response.body()
        } finally {
            client.close()
        }
    }
}

fun String.toHttpBaseUrl(): String {
    val trimmedUrl = trim().trimEnd('/')

    if (trimmedUrl.isBlank()) {
        return ""
    }

    if (trimmedUrl.startsWith("http://") || trimmedUrl.startsWith("https://")) {
        return trimmedUrl
    }

    return "http://$trimmedUrl"
}
