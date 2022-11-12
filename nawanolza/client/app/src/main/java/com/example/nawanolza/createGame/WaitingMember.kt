package com.example.nawanolza.createGame

data class WaitingMember(
    val memberId: Int,
    val name: String,
    val image: String,
    var status: Boolean = true
)
