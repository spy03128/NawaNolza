package com.example.nawanolza.hideandseek

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
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
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.dialog_catch_check.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Timer
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
        var isTagger: Boolean = true
        var isHintOn: Boolean = false

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
        binding = ActivityMainHideSeekBinding.inflate(layoutInflater)
        setContentView(binding.root)
        entryCode = intent.getStringExtra("entryCode").toString()
        senderId = LoginUtil.getMember(this)?.id!!

        if (isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, permission_request)
        }//권한 확인

        if(Waiting.tagger != senderId)
            isTagger = false

        updateTime()
        setRecycleView()

        binding.memberDetail.setOnClickListener {
            val intent = Intent(this@MainHideSeek, MemberDetail::class.java )
            startActivity(intent)
        }

        if(!isTagger){
            binding.bulb.visibility = View.INVISIBLE
            binding.flag.visibility = View.INVISIBLE
        }

        binding.bulb.setOnClickListener {
            isHintOn = true
            Timer().schedule(3000) {
                isHintOn = false
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
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 4)
        adapter.setItemClickListener(object: HideSeekRvAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {

                val builder = AlertDialog.Builder(this@MainHideSeek, R.style.AppAlertDialogTheme)
                val dialogView = layoutInflater.inflate(R.layout.dialog_catch_check, null)

                val user = Waiting.memberHash[Waiting.runnerList[position]]
                dialogView.username.text = user?.name
                Glide.with(this@MainHideSeek).load(user?.image).circleCrop().into(dialogView.userImage)

                if(Waiting.tagger == LoginUtil.getMember(this@MainHideSeek)?.id){
                    builder.setView(dialogView)
                        .setPositiveButton("확인") {dialogInterface, i ->
                            val pubEventRequest = PubEventRequest(user!!.memberId, entryCode, "CATCH", Waiting.tagger, "EVENT")
                            WaitingStompClient.pubEvent(pubEventRequest)
                        }
                        .setNegativeButton("돌아가기") { dialogInterface, i ->
                        }
                    builder.show()
                }

            }
        })
    }

    private fun updateTime() {
        val startTime = WaitingStompClient.roomInfo.startTime.slice(0..22)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val start = LocalDateTime.parse(startTime, formatter)
        val plusTime = WaitingStompClient.roomInfo.playTime/60 + 1
        println("==========check")
        println(plusTime)
        println(WaitingStompClient.roomInfo.playTime)
        val end = start.plusMinutes(plusTime)

        val duration: Duration = Duration.between(LocalDateTime.now(), end)
        val seconds = duration.seconds

        /** 타이머 **/
        object : CountDownTimer(seconds*1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000).toInt() / 60
                val sec = (millisUntilFinished / 1000).toInt() % 60

                val textMin = if(minutes < 10) "0$minutes" else minutes.toString()
                val textSec = if(sec < 10) "0$seconds" else sec.toString()
                val textTime = "$textMin:$textSec"
                binding.tvTime.text = textTime
            }
            override fun onFinish() {
                /** 끝났을 때 **/
                finishGame()
            }
        }.start()
    }

    fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }//권한을 허락 받아야함

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


        setLocationOverlay() // overlay 설정
        setPolyline(LatLng(WaitingStompClient.roomInfo.lat, WaitingStompClient.roomInfo.lng))

        WaitingStompClient.subGPS(entryCode, naverMap, this, senderId, adapter)
        WaitingStompClient.subEvent(entryCode, adapter, this)
        WaitingStompClient.subFinish(entryCode, this)
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

                    sendMyLocation(location)
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

    fun setLastLocation(location: Location) {
        val myLocation = LatLng(location.latitude, location.longitude)
        val marker = Marker()
        //마커
        val cameraUpdate = CameraUpdate.scrollTo(myLocation)
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0

    }

    private fun setLocationOverlay(){
//        val myLocation = LatLng(location.latitude, location.longitude)
        val locationOverlay = naverMap.locationOverlay

        locationOverlay.position = LatLng(36.1071562, 128.4164185)
        locationOverlay.isVisible = true
    }

    private fun setPolyline(latLng: LatLng) {

        val circle = CircleOverlay()
        circle.center = latLng
        val color = Color.parseColor("#ef5350")
        circle.outlineColor = color
        circle.outlineWidth = 1
        circle.radius = 100.0
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
}
