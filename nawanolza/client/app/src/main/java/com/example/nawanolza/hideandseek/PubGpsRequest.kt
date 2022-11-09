package com.example.nawanolza.hideandseek

data class PubGpsRequest(
    val entryCode: String,
    val lat: String,
    val lng: String,
    val senderId: String,
    val type: String
)