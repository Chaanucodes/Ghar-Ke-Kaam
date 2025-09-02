package com.faultyplay.gharkekaam

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform