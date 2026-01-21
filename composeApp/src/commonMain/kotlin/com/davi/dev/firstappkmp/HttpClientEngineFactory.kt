package com.davi.dev.firstappkmp

import io.ktor.client.engine.HttpClientEngine

expect class HttpClientEngineFactory() {
    fun create(): HttpClientEngine
}