package com.example.nawanolza.stomp

data class FinishResponse(
    val entryCode: String,
    val winTagger: Boolean,
    val winnerList: List<Winner>
)