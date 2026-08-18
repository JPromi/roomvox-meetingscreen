package com.jpromi.room_vox.meetingscreen.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

actual fun platformHttpClientEngine(): HttpClientEngineFactory<*> = Darwin
