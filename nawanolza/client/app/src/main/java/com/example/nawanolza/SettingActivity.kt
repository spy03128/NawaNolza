package com.example.nawanolza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nawanolza.databinding.ActivityMainBinding
import com.example.nawanolza.databinding.ActivitySettingBinding
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.fragment_game_room_intro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SettingActivity : OnMapReadyCallback, AppCompatActivity() {
    val permission_request = 99
    private lateinit var naverMap: NaverMap
    var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var time = 60
        var gameTime = 300
        plusButton.setOnClickListener {
            time += 10
            var minute = if(time%60==0) "00" else time%60
            binding.hideTime.text = "${time/60}:${minute}"
        }
            minusButton.setOnClickListener {
                if(time > 10){
                    time -= 10
                    var minute = if(time%60==0) "00" else time%60
                    binding.hideTime.text = "${time/60}:${minute}"
                }
                else{
                    binding.hideTime.text = "0:10"
                }
        }

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

        var retrofit = Retrofit.Builder()
            .baseUrl("https://k7d103.p.ssafy.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service = retrofit.create(TestService::class.java)


        val testRequest = TestRequest(
            100,
            "노현우"
        )

        createButton.setOnClickListener {
            service.Test(testRequest).enqueue(object: Callback<TestResponse> {
                override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                    println(response)
                    println("okay")
                }

                override fun onFailure(call: Call<TestResponse>, t: Throwable) {

                    println(call)
                    println(t)
                }

            })
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
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap){

        val cameraPosition = CameraPosition(
            LatLng(37.5666102, 126.9783881),  // 위치 지정
            16.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition
        this.naverMap = naverMap

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this) //gps 자동으로 받아오기
        setUpdateLocationListner() //내위치를 가져오는 코드
    }
    //내 위치를 가져오는 코드
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient //자동으로 gps값을 받아온다.
    lateinit var locationCallback: LocationCallback //gps응답 값을 가져온다.
    //lateinit: 나중에 초기화 해주겠다는 의미

    @SuppressLint("MissingPermission")
    fun setUpdateLocationListner() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도
//            interval = 1000 //1초에 한번씩 GPS 요청
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for ((i, location) in locationResult.locations.withIndex()) {
                    Log.d("location: ", "${location.latitude}, ${location.longitude}")
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
        val marker = Marker()
        marker.position = LatLng(myLocation.latitude, myLocation.longitude)
        println(LatLng(location.latitude, location.longitude))
//        marker.position = LatLng(
//            naverMap.cameraPosition.target.latitude,
//            naverMap.cameraPosition.target.longitude
//        )
        marker.map = naverMap
        //마커
        val cameraUpdate = CameraUpdate.scrollTo(myLocation)
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0
        updateLocationOverlay(location)

        //marker.map = null
    }

    private fun updateLocationOverlay(location: Location){
        val myLocation = LatLng(location.latitude, location.longitude)

        naverMap.locationOverlay.position = LatLng(myLocation.latitude, myLocation.longitude)
        naverMap.locationOverlay.isVisible = true
        naverMap.locationOverlay.circleRadius = (100 / naverMap.projection.metersPerPixel).toInt()

    }
}