package com.example.nawanolza.stomp

enum class SocketType(value: String) {
    GPS("gps"), MESSAGE("message"), EVENT("event"), CHAT("chat"), PARTICIPATE("participate");

    var value = value
}