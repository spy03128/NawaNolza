package com.example.nawanolza

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://k7d103.p.ssafy.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val enterRoom : EnterRoomService by lazy{
        retrofit.create(EnterRoomService::class.java)
    }
    val createRoom: CreateRoomService by lazy{
        retrofit.create(CreateRoomService::class.java)
    }
    val Test: TestService by lazy{
        retrofit.create(TestService::class.java)
    }
}
