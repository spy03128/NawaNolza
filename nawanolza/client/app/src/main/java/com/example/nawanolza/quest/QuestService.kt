package com.example.nawanolza.quest

import android.app.appsearch.StorageInfo
import com.example.nawanolza.retrofit.CharacterResponse
import com.example.nawanolza.retrofit.QuestFailResponse
import com.example.nawanolza.retrofit.QuestResponse
import com.example.nawanolza.retrofit.QuestSuccessResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface QuestService {

    @POST("collection/quest/start")
    fun GetQuiz(
        @Body data : Map<String, String>
    ) : Call<QuestResponse> //아웃풋 정의

    @POST("collection/quest/success")
    fun PostSuccess(
        @Body data : Map<String, String>
    ) : Call<QuestSuccessResponse> //아웃풋 정의


    @POST("collection/quest/fail")
    fun PostFail(
        @Body data : Map<String, String>
    ) : Call<QuestFailResponse>

}