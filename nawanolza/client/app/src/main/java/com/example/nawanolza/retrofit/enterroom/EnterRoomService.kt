package com.example.nawanolza.retrofit.enterroom

import retrofit2.Call
import retrofit2.http.*

interface EnterRoomService {
    @Headers("accept: application/json", "content-type: application/json")
    @POST("room/hide/participation")
    fun postEnterRoom(
        @Body data: EnterRoomRequest
    ): Call<EnterRoomResponse>
}