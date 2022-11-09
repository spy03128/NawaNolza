package com.example.nawanolza.retrofit.enterroom

data class GetRoomResponse(
    val entryCode: String,
    val tagger: Int,
    val runners: List<Int>,
    val startTime: String
)