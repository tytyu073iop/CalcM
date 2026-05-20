package com.example.calcm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform