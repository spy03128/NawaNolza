package com.example.nawanolza.stomp.waitingstomp

import android.util.Log
import com.example.nawanolza.createGame.Waiting
import com.example.nawanolza.hideandseek.PubGpsRequest
import com.example.nawanolza.hideandseek.PubEventRequest
import com.example.nawanolza.retrofit.createroom.MemberList
import com.example.nawanolza.stomp.SocketCommonDto
import com.example.nawanolza.stomp.SocketType
import com.google.gson.GsonBuilder
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import kotlin.concurrent.timer

class WaitingStompClient {
    companion object {
        val url = "wss://k7d103.p.ssafy.io/api/ws"
        val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        var stopFlag = true

        // 소켓 연결
        fun connect() {
            stompClient.connect()
            stopFlag = false

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
            timer(initialDelay = 0L, period = 1000 * 50L) {
                Log.i("타이머 실행", "타이머 실행")
                stompClient.send("", "").subscribe()
                if (stopFlag)   cancel()
            }
        }


        // 연결 해제
        fun disconnect() {
            stompClient.disconnect()
            stopFlag = true
        }

        // 구독 후 메세지 받음
        fun receive(type: SocketType, entryCode: String){
            stompClient.topic("/sub/" + type.value + "/" + entryCode).subscribe { topicMessage ->
                Log.i("message Receive", topicMessage.payload)
                Waiting.memberList = GsonBuilder().create().fromJson(topicMessage.payload, MemberList::class.java).participants
            }
        }

        /** 받는 메서드 **/

        // 참여자 위치 받아서 실시간으로 업데이트
        fun subGPS(entryCode: String){
            stompClient.topic("/sub/gps/$entryCode").subscribe{ topicMessage ->
                Log.i("message Receive", topicMessage.payload)
            }
        }

        // 잡힌 참여자 받아서 실시간으로 업데이트
        fun subEvent(entryCode: String){
            stompClient.topic("/sub/event/$entryCode").subscribe{ topicMessage ->
                Log.i("message Receive", topicMessage.payload)
            }
        }


        /** 보내는 메서드 **/

        // 내 위치 전송
        fun pubGPS(pubGpsRequest: PubGpsRequest) {
            val data = GsonBuilder().create().toJson(pubGpsRequest)
            stompClient.send("/pub/"+ pubGpsRequest.type, data).subscribe()
        }

        //참가자 잡기
        fun pubEvent(pubEventRequest: PubEventRequest){
            val data = GsonBuilder().create().toJson(pubEventRequest)
            stompClient.send("/pub/"+ pubEventRequest.type, data).subscribe()
        }

    }


}


