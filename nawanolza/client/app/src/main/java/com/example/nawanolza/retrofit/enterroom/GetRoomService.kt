package com.example.nawanolza.retrofit.enterroom

import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.GET
import retrofit2.http.Path

interface GetRoomService{
    @Headers("accept: application/json", "content-type: application/json")
    @GET("game/start/{entryCode}")
    fun getRoomInfo(
        @Path("entryCode") entryCode: String
    ): Call<GetRoomResponse>
}