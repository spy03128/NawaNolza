package com.example.nawanolza.retrofit.finishroom

import com.example.nawanolza.stomp.FinishResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FinishRoomService{
    @GET("game/finish/{entryCode}")
    fun finishRoom(
        @Path("entryCode") entryCode: String
    ): Call<FinishResponse>
}