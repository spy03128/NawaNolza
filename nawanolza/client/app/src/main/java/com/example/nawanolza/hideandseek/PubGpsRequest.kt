package com.example.nawanolza.hideandseek

data class PubGpsRequest(
    val entryCode: String,
    val lat: Double,
    val lng: Double,
    val senderId: Int,
    val type: String
)