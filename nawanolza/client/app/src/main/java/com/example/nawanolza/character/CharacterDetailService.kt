package com.example.nawanolza.character


import com.example.nawanolza.retrofit.CharacterDetailResponse
import com.example.nawanolza.retrofit.CharacterResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterDetailService{
    @GET("collection/{memberId}/detail/{characterId}")
    fun GetCharacterDetail(
        @Path("memberId") memberId: Int,
        @Path("characterId") characterId: Int,
        ) : Call<CharacterDetailResponse> //아웃풋 정의
}