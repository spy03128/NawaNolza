package com.example.nawanolza.stomp

class SocketGpsDTO(gameRoomId: Number, senderId: Number, lat: Double, lng: Double) : SocketCommonDto(gameRoomId, senderId) {
    var lat = lat
    var lng = lng
}