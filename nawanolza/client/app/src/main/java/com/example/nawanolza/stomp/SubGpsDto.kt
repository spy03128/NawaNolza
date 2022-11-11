package com.example.nawanolza.stomp

import com.naver.maps.map.overlay.Marker

data class SubGpsDto(
    val entryCode: String,
    val senderId: Int,
    val type: String,
    val lat: Double,
    val lng: Double,
    val outOfRange: Boolean,
//    val marker: Marker
)