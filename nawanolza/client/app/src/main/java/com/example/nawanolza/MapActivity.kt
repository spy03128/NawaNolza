package com.example.nawanolza

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.UiThread
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.nawanolza.hideandseek.MainHideSeek
import com.example.nawanolza.minigame.CalcGameActivity
import com.example.nawanolza.minigame.CardGameActivity
import com.example.nawanolza.minigame.GameBoomActivity
import com.example.nawanolza.minigame.NumberPuzzleGameActivity
import com.example.nawanolza.retrofit.CharacterLocationResponse
import com.example.nawanolza.retrofit.CharacterLocationResponseItem
import com.example.nawanolza.retrofit.MemberResponse
import com.example.nawanolza.retrofit.QuestResponse
import com.google.android.gms.location.*
import com.google.gson.GsonBuilder
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.dialog_success.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

private const val TAG = "MapActivity_맵에서"

class MapActivity :OnMapReadyCallback, AppCompatActivity() {
    val permission_request = 99
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    var quizInfo = QuestResponse()



    var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)

    var characterInfo = CharacterLocationResponse()

    var memberInfo: MemberResponse? = null

    var currentMarker by Delegates.notNull<Long>()

    lateinit var clickMarkerInfo: Marker

    lateinit var activityResult: ActivityResultLauncher<Intent>

    lateinit var curMyLocation: Location

    //캐릭터 받아오기
    var retrofit = Retrofit.Builder()
        .baseUrl("https://k7d103.p.ssafy.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_map)

        memberInfo = LoginUtil.getMemberInfo(this)
        val url = memberInfo!!.member.image


        Log.d(TAG, "init: ${memberInfo!!.member}")


        if (isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, permission_request)
        }//권한 확인

        Glide.with(this)
            .load(url) // 불러올 이미지 url
            .circleCrop() // 동그랗게 자르기
            .into(CircleImageView) // 이미지를 넣을 뷰



        var service = retrofit.create(GetCharacterService::class.java)



        service.GetCharacter().enqueue(object:Callback<CharacterLocationResponse> {

            override fun onResponse(
                call: Call<CharacterLocationResponse>,
                response: Response<CharacterLocationResponse>
            ) {
                characterInfo = response.body() ?: CharacterLocationResponse()

                updateMapMarkers(characterInfo)
            }

            override fun onFailure(call: Call<CharacterLocationResponse>, t: Throwable) {
                println(call)
                println(t)
            }

        })

        CircleImageView.setOnClickListener{
            val intent = Intent(this@MapActivity, CharacterActivity::class.java)
            intent.apply {
                putExtra("memberInfo", memberInfo)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }

        activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            println("퀘스트 실행 결과")
            val booleanExtra = it.data?.getBooleanExtra("result", false)
            println(booleanExtra)
            if (it.resultCode == RESULT_OK && booleanExtra == true) {

                val dialog = AlertDialog.Builder(this@MapActivity).apply {

                }.create()



                dialog.window?.apply {
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }

                dialog.apply {

                    val dialogView = layoutInflater.inflate(R.layout.dialog_success,null)
                    dialogView.character.setImageResource(MarkerImageUtil.getImage(currentMarker))
                    setView(dialogView)

                    show()
                }



            }
            else{
                val dialog = AlertDialog.Builder(this@MapActivity).apply {

                }.create()


                dialog.window?.apply {
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }

                dialog.apply {
                    setView(layoutInflater.inflate(R.layout.dialog_fail,null))

                    show()
                }
            }
        }
    }

    private fun updateMapMarkers(characterInfo: CharacterLocationResponse) {
        if(characterInfo != null  && characterInfo.size>0){
            for(current in characterInfo){
                val marker = Marker()
                marker.position= LatLng(current.lat, current.lng)
                marker.width  = 250
                marker.height = 250


                marker.icon = OverlayImage.fromResource(MarkerImageUtil.getImage(current.characterId) as Int)
                marker.map = naverMap
                marker.tag = current


                val function: (Overlay) -> Boolean = { o ->

                    currentMarker = (marker.tag as CharacterLocationResponseItem).characterId
                    clickMarkerInfo = marker


                    var service = retrofit.create(QuestService::class.java)

                    var markerInfo = marker.tag as CharacterLocationResponseItem


                    service.GetQuiz(mapOf("markerId" to markerInfo.markerId.toString(), "questType" to markerInfo.questType.toString())).enqueue(object: Callback<QuestResponse> {

                        override fun onResponse(
                            call: Call<QuestResponse>,
                            response: Response<QuestResponse>
                        ) {

                            quizInfo = response.body() ?: QuestResponse()

                            checkQuestType()
                        }

                        private fun checkQuestType() {

                            if (!quizInfo.accessible) {
                                val dialog = AlertDialog.Builder(this@MapActivity).apply {

                                }.create()


                                dialog.window?.apply {
                                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                }

                                dialog.apply {
                                    setView(layoutInflater.inflate(R.layout.dialog_play, null))

                                    show()
                                }
                            } else {
                                if (markerInfo.questType == 0) {
                                    val intent = Intent(this@MapActivity, QuizActivity::class.java)

                                    intent.putExtra(
                                        "markerId",
                                        (marker.tag as CharacterLocationResponseItem).markerId
                                    )

                                    intent.putExtra(
                                        "characterId",
                                        (marker.tag as CharacterLocationResponseItem).characterId
                                    )
                                    intent.putExtra(
                                        "quizInfo",
                                        GsonBuilder().create().toJson(quizInfo)
                                    )



                                    activityResult.launch(intent)
                                } else if (markerInfo.questType == 1) {
                                    val intent =
                                        Intent(this@MapActivity, GameBoomActivity::class.java)

                                    intent.putExtra(
                                        "markerId",
                                        (marker.tag as CharacterLocationResponseItem).markerId
                                    )

                                    intent.putExtra(
                                        "characterId",
                                        (marker.tag as CharacterLocationResponseItem).characterId
                                    )


                                    activityResult.launch(intent)
                                } else if (markerInfo.questType == 2) {
                                    val intent =
                                        Intent(this@MapActivity, CardGameActivity::class.java)

                                    intent.putExtra(
                                        "markerId",
                                        (marker.tag as CharacterLocationResponseItem).markerId
                                    )

                                    intent.putExtra(
                                        "characterId",
                                        (marker.tag as CharacterLocationResponseItem).characterId
                                    )


                                    activityResult.launch(intent)
                                } else if (markerInfo.questType == 3) {
                                    val intent =
                                        Intent(this@MapActivity, CalcGameActivity::class.java)

                                    intent.putExtra(
                                        "markerId",
                                        (marker.tag as CharacterLocationResponseItem).markerId
                                    )

                                    intent.putExtra(
                                        "characterId",
                                        (marker.tag as CharacterLocationResponseItem).characterId
                                    )


                                    activityResult.launch(intent)
                                } else if (markerInfo.questType == 4) {
                                    val intent =
                                        Intent(this@MapActivity, NumberPuzzleGameActivity::class.java)

                                    intent.putExtra(
                                        "markerId",
                                        (marker.tag as CharacterLocationResponseItem).markerId
                                    )

                                    intent.putExtra(
                                        "characterId",
                                        (marker.tag as CharacterLocationResponseItem).characterId
                                    )


                                    activityResult.launch(intent)
                                }
                            }
                        }

                        override fun onFailure(call: Call<QuestResponse>, t: Throwable) {
                            println(call)
                            println(t)

                        }
                    })

                    true

                }
                marker.setOnClickListener {
                    val lat = (it.tag as CharacterLocationResponseItem).lat
                    val lng = (it.tag as CharacterLocationResponseItem).lng
                    if (MainHideSeek.DistanceManager.getDistance(curMyLocation.latitude, curMyLocation.longitude, lat, lng) <= 10)
                        function(it)
                    else{
                        val dialog = AlertDialog.Builder(this@MapActivity).apply {
                        }.create()

                        dialog.window?.apply {
                            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        }

                        dialog.apply {
                            setView(layoutInflater.inflate(R.layout.dialog_distance_notice,null))
                            show()
                        }
                    }

                    true
                }
            }
        }
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

        val cameraPosition = CameraPosition(
            LatLng(36.1071562, 128.4164185),  // 위치 지정
            16.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition


        this.naverMap = naverMap

        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true //위치 버튼 활성화

        naverMap.locationSource = locationSource // 좌표 눌렀을때 현재 위치로 이동
        naverMap.locationTrackingMode = LocationTrackingMode.Follow


        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this) //gps 자동으로 받아오기
        setUpdateLocationListener() //내위치를 가져오는 코드
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
            interval = 1000 //1초에 한번씩 GPS 요청
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for ((i, location) in locationResult.locations.withIndex()) {
                    Log.d("location: ", "${location.latitude}, ${location.longitude}")
                    curMyLocation = location
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
        marker.position = myLocation
        marker.map = null
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0

        updateLocationOverlay(location)
    }

    private fun updateLocationOverlay(location: Location){
        val myLocation = LatLng(location.latitude, location.longitude)
        naverMap.locationOverlay.position = LatLng(myLocation.latitude, myLocation.longitude)
        naverMap.locationOverlay.isVisible = true
    }

}
