package com.example.nawanolza.character


import com.example.nawanolza.retrofit.CharacterResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService{
    @GET("collection/{memberId}")

    fun GetCharacter(
        @Path("memberId") memberId: Int,
        @Query("type") type: String,
        @Query("sort") sort: String
        ) : Call<CharacterResponse> //아웃풋 정의
}