package com.example.nawanolza.hideandseek

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nawanolza.HomeActivity
import com.example.nawanolza.LoginUtil
import com.example.nawanolza.R
import com.example.nawanolza.createGame.Waiting
import com.example.nawanolza.databinding.ActivityMainHideSeekBinding
import com.example.nawanolza.retrofit.RetrofitConnection
import com.example.nawanolza.retrofit.finishroom.FinishRoomService
import com.example.nawanolza.stomp.FinishResponse
import com.example.nawanolza.stomp.waitingstomp.WaitingStompClient
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.dialog_catch_check.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.*

class MainHideSeek : OnMapReadyCallback, AppCompatActivity() {

    // 네이버 맵 권한 요청
    private val permission_request = 99
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    var senderId = 0

    var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)

    lateinit var binding: ActivityMainHideSeekBinding
    lateinit var adapter: HideSeekRvAdapter
    lateinit var entryCode: String

    companion object {
        var _MainHiddSeek_Activity: Activity? = null

        var isTagger: Boolean = true
        lateinit var taggerLocation: LatLng

        var caughtMember = 0
        fun finishGame() {
            val retrofitAPI = RetrofitConnection.getInstance().create(FinishRoomService::class.java)
            retrofitAPI.finishRoom(Waiting.entryCode).enqueue(object: Callback<FinishResponse>{
                override fun onResponse(
                    call: Call<FinishResponse>,
                    response: Response<FinishResponse>
                ) {
                    println("====retrofit====")
                    println(response)
                }
                override fun onFailure(call: Call<FinishResponse>, t: Throwable) {
                    println("=====error=====")
                    println(call)
                    println(t)
                }
            })
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _MainHiddSeek_Activity = this@MainHideSeek

        binding = ActivityMainHideSeekBinding.inflate(layoutInflater)
        setContentView(binding.root)
        entryCode = intent.getStringExtra("entryCode").toString()
        senderId = LoginUtil.getMember(this)?.id!!

        var hintCount = 3;

        //권한 확인
        if (isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, permission_request)
        }

        if(Waiting.tagger != senderId)
            isTagger = false

        updateTime()
        setRecycleView()

        binding.memberDetailBtn.setOnClickListener {
            val intent = Intent(this@MainHideSeek, MemberDetail::class.java )
            startActivity(intent)
        }

        if(Waiting.tagger != senderId){
            binding.bulb.visibility = View.INVISIBLE
            binding.flag.visibility = View.INVISIBLE
            binding.title.text = "숨는 팀"
        }

        binding.bulb.setOnClickListener {
            if(hintCount > 0) {
                hintCount--

                val markerMapCopy = HashMap(WaitingStompClient.markerMap)

                for(marker in markerMapCopy)
                    marker.value.map = naverMap

                Timer().schedule(2000) {
                    runOnUiThread {
                        for(marker in markerMapCopy)
                            marker.value.map = null
                    }
                }
            }
        }

        binding.flag.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainHideSeek, R.style.AppAlertDialogTheme)
            val dialogView = layoutInflater.inflate(R.layout.dialog_surrender, null)

            builder.setView(dialogView)
                .setPositiveButton("확인") {dialogInterface, i ->
                    Log.i("surrender", "항복 확인")
                    finishGame()
                }
                .setNegativeButton("돌아가기") { dialogInterface, i ->
                    Log.i("surrender", "항복 취소")
                }
            builder.show()
        }

        binding.talk.setOnClickListener {
            val intent = Intent(this@MainHideSeek, ChattingActivity::class.java)
            intent.putExtra("entryCode", entryCode)
            startActivity(intent)
        }
    }

    private fun setRecycleView() {
        adapter = HideSeekRvAdapter(this)
        binding.mRecyclerView.adapter = adapter
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        adapter.setItemClickListener(object: HideSeekRvAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                if(isTagger && WaitingStompClient.markerMap.size >= 1){
                    val builder = AlertDialog.Builder(this@MainHideSeek, R.style.AppAlertDialogTheme)
                    val dialogView = layoutInflater.inflate(R.layout.dialog_catch_check, null)

                    val user = Waiting.memberHash[Waiting.runnerList[position]]

                    if(!user!!.status) {
                        dialogView.username.text = user?.name
                        Glide.with(this@MainHideSeek).load(user?.image).circleCrop().into(dialogView.userImage)
                        println("=======markerMap======")
                        println(WaitingStompClient.markerMap)

                        val userLat = WaitingStompClient.markerMap[Waiting.runnerList[position]]!!.position.latitude
                        val userLng = WaitingStompClient.markerMap[Waiting.runnerList[position]]!!.position.longitude
                        val close: Boolean = DistanceManager.getDistance(userLat, userLng, taggerLocation.latitude, taggerLocation.longitude) <= 10

                        if(!close){
                            Toast.makeText(this@MainHideSeek, "거리가 너무 멀어요", Toast.LENGTH_SHORT).show()
                        } else{
                            builder.setView(dialogView)
                                .setPositiveButton("확인") {dialogInterface, i ->
                                    val pubEventRequest = PubEventRequest(user!!.memberId, entryCode, "CATCH", Waiting.tagger, "EVENT")
                                    WaitingStompClient.pubEvent(pubEventRequest, this@MainHideSeek)
                                }
                                .setNegativeButton("돌아가기") { dialogInterface, i ->
                                }
                            builder.show()
                        }
                    }
                } else {
                    println("숨는 팀입니다! back")
                }


            }
        })


    }

    private fun updateTime() {
        val startTime = WaitingStompClient.roomInfo.startTime.slice(0..22)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val start = LocalDateTime.parse(startTime, formatter)
        val plusTime = WaitingStompClient.roomInfo.playTime + RoleCheckActivity.countdown/1000
        println("==========check")
        println(plusTime)
        println(WaitingStompClient.roomInfo.playTime)
        val end = start.plusSeconds(plusTime)

        val duration: Duration = Duration.between(LocalDateTime.now(), end)
        println(LocalDateTime.now())
        val seconds = duration.seconds

        val participantCount = Waiting.runnerList.size
        MessageSenderService.sendMessageToWearable("/message_path", "s/$end/$participantCount", this)

        /** 타이머 **/
        object : CountDownTimer(seconds*1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000).toInt() / 60
                val sec = (millisUntilFinished / 1000).toInt() % 60

                val textMin = if(minutes < 10) "0$minutes" else minutes.toString()
                val textSec = if(sec < 10) "0$sec" else sec.toString()

                val textTime = "$textMin:$textSec"
                binding.tvTime.text = textTime
            }
            override fun onFinish() {
                finishGame()
            }
        }.start()
    }

    //권한 허락
    fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun startProcess(){
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            } //권한
        mapFragment.getMapAsync(this)

        locationSource = FusedLocationSource(this, 1000) // 좌표 눌렀을때 현재 위치로 이동
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap){
        this.naverMap = naverMap

        val cameraPosition = CameraPosition(
            LatLng(36.1071562, 128.4164185),  // 위치 지정
            16.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition

        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true //위치 버튼 활성화

        naverMap.locationSource = locationSource // 좌표 눌렀을때 현재 위치로 이동

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this) //gps 자동으로 받아오기
        setUpdateLocationListener() //내위치를 가져오는 코드


        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        setPolyline(LatLng(WaitingStompClient.roomInfo.lat, WaitingStompClient.roomInfo.lng))

        WaitingStompClient.subGPS(entryCode, naverMap, this, senderId, adapter)
        WaitingStompClient.subEvent(entryCode, adapter, this)
        WaitingStompClient.subFinish(entryCode, this)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this@MainHideSeek, R.style.AppAlertDialogTheme)
        val dialogView = layoutInflater.inflate(R.layout.dialog_main_back_finish, null)

        builder.setView(dialogView)
            .setPositiveButton("나가기") {dialogInterface, i ->
                WaitingStompClient.disconnect()
                val intent = Intent(this@MainHideSeek, HomeActivity::class.java)
                startActivity(intent)

            }
            .setNegativeButton("돌아가기") { dialogInterface, i ->

            }
        builder.show()
    }

    //내 위치를 가져오는 코드
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient //자동으로 gps값을 받아온다.
    lateinit var locationCallback: LocationCallback //gps응답 값을 가져온다.

    @SuppressLint("MissingPermission")
    fun setUpdateLocationListener() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도
            interval = 1000 //1초에 한번씩 GPS 요청
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for ((i, location) in locationResult.locations.withIndex()) {
                    Log.d("location: ", "${location.latitude}, ${location.longitude}")
//                    setLastLocation(location)
                    if(isTagger){
                        taggerLocation = LatLng(location.latitude, location.longitude)
                    }
                    sendMyLocation(location)
//                    setLastLocation(location)
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )

    }


    // 내 위치 보내기
    fun sendMyLocation(location: Location) {
        val pubGpsRequest = PubGpsRequest(entryCode, location.latitude, location.longitude, senderId, "GPS")
        WaitingStompClient.pubGPS(pubGpsRequest)
    }

//    // 위치 카메라 갱신
//    fun setLastLocation(location: Location) {
//        val myLocation = LatLng(location.latitude, location.longitude)
//        val cameraUpdate = CameraUpdate.scrollTo(myLocation)
//        naverMap.moveCamera(cameraUpdate)
//        naverMap.maxZoom = 18.0
//        naverMap.minZoom = 16.0
//    }

    // 반경 표시
    private fun setPolyline(latLng: LatLng) {
        val circle = CircleOverlay()
        circle.center = latLng
        val color = Color.parseColor("#80ce93d8")
        val outlineColor = Color.parseColor("#8e24aa")
        circle.color = color
        circle.outlineColor = outlineColor
        circle.outlineWidth = 2
        circle.radius = WaitingStompClient.roomInfo.range.toDouble()
        circle.map = naverMap
    }

    // 거리 구하기
    object DistanceManager {

        private const val R = 6372.8 * 1000

        fun getDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Int {
            val dLat = Math.toRadians(lat2 - lat1)
            val dLng = Math.toRadians(lng2 - lng1)
            val a = sin(dLat / 2).pow(2.0) + sin(dLng / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
            val c = 2 * asin(sqrt(a))
            return (R * c).toInt()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        isTagger = true
        caughtMember = 0

        ChattingActivity._Chatting_Activity?.finish()

        Waiting._Waiting_Activity?.finish()
    }
}
