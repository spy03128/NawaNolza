package com.example.nawanolza.retrofit.createroom


import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteRoomService {
    @DELETE("room/{entryCode}")
    fun deleteRoom(
        @Path("entryCode") entryCode: String
    ): Single<Response<Void>>
}