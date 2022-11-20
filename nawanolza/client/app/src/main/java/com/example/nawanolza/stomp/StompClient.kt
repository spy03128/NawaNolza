package com.example.nawanolza.stomp

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent

class StompClient {
    val url = "wss://k7d103.p.ssafy.io/api/ws"
    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    val objectMapper = ObjectMapper()

    // 소켓 방생성후 입장하면 쓰세요.
    fun connect() {
        println("소켓 연결")
        stompClient.connect()

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i("OPEND", "!!")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.i("CLOSED", "!!")

                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "!!")
                    Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
                }
                else ->{
                    Log.i("ELSE", lifecycleEvent.message)
                }
            }
        }
    }

    // 소켓 연결해제 ex) 게임 종료나 방파기시 쓰세요!
    fun disconnect() {
        println("소켓 연결해제")
        stompClient.disconnect()
    }

    // 여기서 데이터 받아서 처리하는 코드쓰면 됩니다.
    fun subscribe(type: SocketType, roomNumber: Number) {
        println("소켓 구독")
        println("방번호 : " + roomNumber)
        println("/sub/" + type.value + "/" + roomNumber)
        stompClient.topic("/sub/" + type.value + "/" + roomNumber).subscribe { topicMessage ->
            Log.i("message Recieve", topicMessage.payload)
        }
    }

    // 여기서 데이터 전송하는 코드쓰면 됩니다.
    fun send(type: SocketType, dto: SocketCommonDto) {
        println("소켓 메시지 전송")
        println("방번호 : " + dto.entryCode)
        println("dto : " + objectMapper.writeValueAsString(dto))
        val data = objectMapper.writeValueAsString(dto)
        stompClient.send("/pub" + type.value, data).subscribe()
    }
}