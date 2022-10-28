package com.example.nawanolza

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface CreateRoomService {
    @Headers("accept:application/json", "Content-Type: application/json")
    @FormUrlEncoded
    @POST("/room/hide")
    fun createRoom(
        @Body data: CreateRoomRequest
    ):Call<CreateRoomResponse>

}