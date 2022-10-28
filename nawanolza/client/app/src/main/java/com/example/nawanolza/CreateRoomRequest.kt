package com.example.nawanolza

data class CreateRoomRequest(
    val count: Int?,
    val latitude: Float?,
    val longitude: Float?,
    val playTime: Int?,
    val hideTime: Int?,
    val memberId: Int?
)