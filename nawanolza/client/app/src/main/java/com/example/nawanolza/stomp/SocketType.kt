package com.example.sockettest

enum class SocketType(value: String) {
    GPS("gps"), MESSAGE("message"), EVENT("event");

    var value = value
}