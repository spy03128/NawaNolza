package com.example.nawanolza.stomp.waitingstomp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.nawanolza.createGame.Waiting
import com.example.nawanolza.createGame.WaitingRvAdapter
import com.example.nawanolza.hideandseek.PubEventRequest
import com.example.nawanolza.hideandseek.PubGpsRequest
import com.example.nawanolza.hideandseek.RoleCheckActivity
import com.example.nawanolza.retrofit.createroom.MemberList
import com.example.nawanolza.retrofit.enterroom.GetRoomResponse
import com.example.nawanolza.stomp.CatchResponse
import com.example.nawanolza.stomp.SocketType
import com.example.nawanolza.stomp.SubGpsDto
import com.google.gson.GsonBuilder
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import kotlin.concurrent.timer

class WaitingStompClient {
    companion object {
        val url = "wss://k7d103.p.ssafy.io/api/ws"
        val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        var stopFlag = true
        lateinit var roomInfo: GetRoomResponse
        val markerMap: HashMap<Int, Marker> = HashMap()

        // 마커 업데이트
//        fun updateMarker(naverMap: NaverMap, subGpsDto: SubGpsDto) {
//            if(markerMap.containsKey(subGpsDto.senderId))
//                markerMap.get(subGpsDto.senderId).marker = null
//        }

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

        /** 받는 메서드 **/

        // 참가자 정보 받기
        fun subParticipate(entryCode: String, adapter: WaitingRvAdapter, activity: Activity){
            stompClient.topic("/sub/participate/$entryCode").subscribe { topicMessage ->
                Log.i("message Receive", topicMessage.payload)
                Waiting.memberList = GsonBuilder().create().fromJson(topicMessage.payload, MemberList::class.java).participants

                activity.runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }
        }

        // 게임 시작
        fun subGameStart(entryCode: String, context: Context) {
            stompClient.topic("/sub/game/start/$entryCode").subscribe{ topicMessage ->
                Log.i("message Receive", topicMessage.payload)
                for(member in Waiting.memberList)
                    Waiting.memberHash.put(member.memberId, member)

                roomInfo = GsonBuilder().create().fromJson(topicMessage.payload, GetRoomResponse::class.java)
                Waiting.taggerList = roomInfo.tagger
                Waiting.runnerList = roomInfo.runners

                val intent = Intent(context, RoleCheckActivity::class.java)
                intent.putExtra("entryCode", entryCode)
                context.startActivity(intent)
            }
        }

        // 참여자 위치 받기 (구독)
        fun subGPS(entryCode: String, naverMap: NaverMap, activity: Activity, senderId: Int){
            println("구독했나요?")
            stompClient.topic("/sub/gps/$entryCode").subscribe{ topicMessage ->
                println("메세지 오나요?")
                Log.i("subGPSMessage", topicMessage.payload)

                var subDto: SubGpsDto = GsonBuilder().create().fromJson(topicMessage.payload, SubGpsDto::class.java)
                println("================= start ===================")
                println(subDto)
                println("================= end ===================")

                if(senderId != subDto.senderId) {
                    activity.runOnUiThread {
                        if(markerMap.containsKey(subDto.senderId)) {
                            markerMap.get(subDto.senderId)?.map = null
                        }

                        val myLocation = LatLng(subDto.lat, subDto.lng)
                        val marker = Marker()
                        //마커

                        marker.position= myLocation
//                    marker.width  = 250
//                    marker.height = 250
////                    marker.icon = OverlayImage.fromResource(MarkerImageUtil.getImage(current.characterId) as Int)

                        markerMap.put(subDto.senderId, marker)
                        marker.map = naverMap
//                    var timeFlag: Boolean = false

//                    timer(initialDelay = 0L, period = 1000L) {
//                        activity.runOnUiThread{
////                            marker.map = null
//                            timeFlag = true
//                        }
//                        if (timeFlag) cancel()
//                    }

                    }
                }
            }
        }

        // 잡힌 참여자 받기 구독
        fun subEvent(entryCode: String){
            stompClient.topic("/sub/event/$entryCode").subscribe{ topicMessage ->
                Log.i("message Receive", topicMessage.payload)
                val data = GsonBuilder().create().fromJson(topicMessage.payload, CatchResponse::class.java)
                for(member in Waiting.memberList){
                    if(data.catchMemberId == member.memberId){
                        member.status = false
                    }
                }
            }
        }

        /** 보내는 메서드 **/

        // 내 위치 전송
        fun pubGPS(pubGpsRequest: PubGpsRequest) {
            val data = GsonBuilder().create().toJson(pubGpsRequest)
            stompClient.send("/pub/gps", data).subscribe()
        }

        //참가자 잡기
        fun pubEvent(pubEventRequest: PubEventRequest){
            val data = GsonBuilder().create().toJson(pubEventRequest)
            stompClient.send("/pub/"+ pubEventRequest.type, data).subscribe()
        }

    }


}


