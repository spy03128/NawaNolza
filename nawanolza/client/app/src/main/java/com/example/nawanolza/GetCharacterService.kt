package com.example.nawanolza

import com.example.nawanolza.retrofit.CharacterLocationResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GetCharacterService{
    @GET("collection/map")
    fun GetCharacter(
    ) : Call<CharacterLocationResponse> //아웃풋 정의
}