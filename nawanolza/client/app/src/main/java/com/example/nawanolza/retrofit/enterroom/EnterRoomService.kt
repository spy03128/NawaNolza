package com.example.nawanolza.retrofit.enterroom

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface EnterRoomService {
    @Headers("accept: application/json", "content-type: application/json")
    @POST("room/hide/participant")
    fun postEnterRoom(
        @Body data: EnterRoomRequest
    ): Call<EnterRoomResponse>
}