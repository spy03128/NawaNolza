package com.example.nawanolza

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GetCharacterService{
    @GET("collection/map")
    fun GetCharacter(
    ) : Call<List<Map<String, String>>> //아웃풋 정의
}