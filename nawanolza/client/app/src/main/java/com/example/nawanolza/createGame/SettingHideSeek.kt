package com.example.nawanolza.createGame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Looper
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.nawanolza.*
import com.example.nawanolza.R
import com.example.nawanolza.databinding.ActivitySettingHideSeekBinding
import com.example.nawanolza.hideandseek.MessageSenderService
import com.example.nawanolza.retrofit.RetrofitConnection
import com.example.nawanolza.retrofit.createroom.CreateRoomHideResponse
import com.example.nawanolza.retrofit.createroom.CreateRoomHideService
import com.example.nawanolza.retrofit.createroom.CreateRoomRequest
import com.google.android.gms.location.*
import com.google.gson.GsonBuilder
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_setting_hide_seek.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingHideSeek : OnMapReadyCallback, AppCompatActivity() {
    val permission_request = 99
    var check: Boolean = false
    var circle = CircleOverlay()
    var lat: Double? = null
    var lng: Double? = null

    private lateinit var naverMap: NaverMap
    private lateinit var createRoomRequest: CreateRoomRequest
    private lateinit var locationSource: FusedLocationSource

    var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivitySettingHideSeekBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var time = 60
        var gameTime = 300

        gamePlus.setOnClickListener {
            gameTime += 30
            var gameMinute = if(gameTime%60==0) "00" else gameTime%60
            binding.gameTime.text = "${gameTime/60}:${gameMinute}"
        }
        gameMinus.setOnClickListener {
            if(gameTime > 30){
                gameTime -= 30
                var gameMinute = if(gameTime%60==0) "00" else gameTime%60
                binding.gameTime.text = "${gameTime/60}:${gameMinute}"
            }
            else{
                binding.gameTime.text = "0:30"
            }
        }

        var range = 100
        rangeMinus.setOnClickListener {
            if(range <= 50) {
            }else{
                range -= 50
                binding.rangeText.text = range.toString()
            }
        }
        rangePlus.setOnClickListener {
            if(range <= 150.0){
                range += 50
                binding.rangeText.text = range.toString()
            }
        }

        val retrofitAPI = RetrofitConnection.getInstance().create(
            CreateRoomHideService::class.java
        )

        btnCreateRoom.setOnClickListener {
            if(check){
                val hostId = LoginUtil.getMember(this@SettingHideSeek)!!.id
                createRoomRequest = CreateRoomRequest(lat, lng, gameTime, time, 100, hostId)
                println("=====게임만들기======")
                println(createRoomRequest)
                retrofitAPI.postCreateRoomHide(createRoomRequest).enqueue(object:Callback<CreateRoomHideResponse> {
                    override fun onResponse(
                        call: Call<CreateRoomHideResponse>,
                        response: Response<CreateRoomHideResponse>
                    ) {
                        val intent = Intent(this@SettingHideSeek, Waiting::class.java)
                        intent.putExtra("entryCode", "${response.body()?.entryCode}" )
                        intent.putExtra("data", GsonBuilder().create().toJson(response.body()))

                        when(response.code()){
                            200 -> {
                                startActivity(intent)
                                MessageSenderService.sendMessageToWearable("/message_path", "w", this@SettingHideSeek)
                            }
                            else -> Toast.makeText(this@SettingHideSeek, "잘못된 정보입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<CreateRoomHideResponse>, t: Throwable) {
                        println(call)
                        println(t)
                    }
                } )
            }else{
                Toast.makeText(this,"영역을 설정해주세요", Toast.LENGTH_LONG).show()
            }
        }

        if (isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, permission_request)
        }//권한 확인
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

        naverMap.setOnMapClickListener { point, coord ->
            val latLng = LatLng(coord.latitude, coord.longitude)
            lat = coord.latitude
            lng = coord.longitude
            setPolyline(latLng)
        }
    }
    //내 위치를 가져오는 코드
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient //자동으로 gps값을 받아온다.
    lateinit var locationCallback: LocationCallback //gps응답 값을 가져온다.
    //lateinit: 나중에 초기화 해주겠다는 의미

    @SuppressLint("MissingPermission")
    fun setUpdateLocationListener() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도
//            interval = 1000 //1초에 한번씩 GPS 요청
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for ((i, location) in locationResult.locations.withIndex()) {
//                    Log.d("location: ", "${location.latitude}, ${location.longitude}")
                    setLastLocation(location)
                }
            }
        }
        //location 요청 함수 호출 (locationRequest, locationCallback)

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }//좌표계를 주기적으로 갱신

    fun setLastLocation(location: Location) {
        val myLocation = LatLng(location.latitude, location.longitude)
        val cameraUpdate = CameraUpdate.scrollTo(myLocation)
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0
    }

    private fun setPolyline(latLng:LatLng){
        if(!check){
            circle.center = latLng
            val color = Color.parseColor("#80D6E6F2")
            circle.color = color
            circle.radius = rangeText.text.toString().toDouble()
            circle.map = naverMap
            check = true
        }else{
            circle.map = null
            check = false
        }
    }
}

