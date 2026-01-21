package com.davi.dev.firstappkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform