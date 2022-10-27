package com.example.nawanolza

import retrofit2.http.Field
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EnterRoomService{

    @FormUrlEncoded
    @POST("/room/")
    fun requestEnterRoom(
        @Field("code") code:String
    ) : Call<EnterRoom> //아웃풋 정의
}