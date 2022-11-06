package com.example.nawanolza

import com.example.nawanolza.retrofit.CharacterResponse
import com.example.nawanolza.retrofit.QuestResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface QuestService {

    @POST("collection/quest/start")

    fun GetQuiz(
        @Body data : Map<String, String>
    ) : Call<QuestResponse> //아웃풋 정의
}