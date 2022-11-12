package com.example.nawanolza.retrofit.enterroom

data class GetRoomResponse(
    val entryCode: String,
    val tagger: List<Int>,
    val runners: List<Int>,
    val startTime: String,
    val playTime: Long,
    val lat: Double,
    val lng: Double,
    val range: Int
)