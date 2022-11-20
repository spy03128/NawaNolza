package com.example.nawanolza.retrofit.createroom

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CreateRoomHideService {
    @Headers("accept: application/json", "content-type: application/json")
    @POST("room/hide")
    fun postCreateRoomHide(
        @Body data: CreateRoomRequest
    ): Call<CreateRoomHideResponse>
}