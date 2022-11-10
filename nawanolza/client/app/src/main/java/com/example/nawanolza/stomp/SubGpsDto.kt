package com.example.nawanolza.stomp

data class SubGpsDto(
    val entryCode: String,
    val senderId: Int,
    val type: String,
    val lat: Double,
    val lng: Double,
    val outOfRange: Boolean
)