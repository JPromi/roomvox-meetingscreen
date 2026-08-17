package com.jpromi.room_vox.meetingscreen.network

import com.jpromi.room_vox.meetingscreen.AppSettings
import com.jpromi.room_vox.meetingscreen.models.Room
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CancellationException

class RoomVoxService(
    private val appSettings: AppSettings = AppSettings(),
) {
    val baseUrl: String
        get() = appSettings.serverUrl.toHttpBaseUrl() + "/apps/roomvox/api/v1"

    suspend fun getRooms(): RoomsResult {
        if (appSettings.serverUrl.isBlank()) {
            return RoomsResult.InvalidConfiguration
        }

        val client = HttpClientFactory.create()
        return try {
            val response = client.get("$baseUrl/rooms") {
                if (appSettings.accessToken.isNotBlank()) {
                    bearerAuth(appSettings.accessToken.trim())
                }
            }

            RoomsResult.Success(response.body())
        } catch (error: ResponseException) {
            when (error.response.status) {
                HttpStatusCode.Unauthorized -> RoomsResult.Unauthorized
                HttpStatusCode.Forbidden -> RoomsResult.Forbidden
                HttpStatusCode.NotFound -> RoomsResult.NotFound
                else -> RoomsResult.HttpError(
                    statusCode = error.response.status.value,
                    message = error.message ?: error.response.status.description,
                )
            }
        } catch (error: CancellationException) {
            throw error
        } catch (error: Throwable) {
            RoomsResult.NetworkError(error)
        } finally {
            client.close()
        }
    }
}

sealed interface RoomsResult {
    data class Success(val rooms: List<Room>) : RoomsResult
    data object InvalidConfiguration : RoomsResult
    data object Unauthorized : RoomsResult
    data object Forbidden : RoomsResult
    data object NotFound : RoomsResult
    data class HttpError(
        val statusCode: Int,
        val message: String,
    ) : RoomsResult
    data class NetworkError(val cause: Throwable) : RoomsResult
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
