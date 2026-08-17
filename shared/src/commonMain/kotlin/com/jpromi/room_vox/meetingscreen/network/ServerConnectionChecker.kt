package com.jpromi.room_vox.meetingscreen.network

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get

class ServerConnectionChecker {
    suspend fun check(
        serverUrl: String,
        accessToken: String,
    ): ServerConnectionResult {
        val normalizedUrl = serverUrl.toHttpBaseUrl()

        if (normalizedUrl.isBlank()) {
            return ServerConnectionResult.Error("Bitte Server URL eingeben.")
        }

        return runCatching {
            val client = HttpClientFactory.create()
            try {
                val response = client.get(normalizedUrl) {
                    if (accessToken.isNotBlank()) {
                        bearerAuth(accessToken.trim())
                    }
                }

                ServerConnectionResult.Success(
                    message = "Server OK (${response.status.value})"
                )
            } finally {
                client.close()
            }
        }.getOrElse { error ->
            ServerConnectionResult.Error(error.toServerMessage())
        }
    }
}

sealed interface ServerConnectionResult {
    data class Success(val message: String) : ServerConnectionResult
    data class Error(val message: String) : ServerConnectionResult
}

private fun Throwable.toServerMessage(): String = when (this) {
    is RedirectResponseException -> "Weiterleitung erhalten: ${response.status.value}"
    is ClientRequestException -> "Server antwortet mit Fehler: ${response.status.value}"
    is ServerResponseException -> "Serverfehler: ${response.status.value}"
    else -> message ?: "Server nicht erreichbar."
}
