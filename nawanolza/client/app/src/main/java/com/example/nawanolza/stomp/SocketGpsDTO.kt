package com.example.nawanolza.stomp

class SocketGpsDTO(gameRoomId: String, senderId: Number, lat: Double, lng: Double) : SocketCommonDto(gameRoomId, senderId) {
    var lat = lat
    var lng = lng
}