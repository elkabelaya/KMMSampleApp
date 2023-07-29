package com.elkabelaya.kmmsampleapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform