package com.example.sockettest

class SocketGpsDTO(gameRoomId: Number, senderId: Number, lat: Double, lng: Double) : SocketCommonDto(gameRoomId, senderId) {
    var lat = lat
    var lng = lng
}