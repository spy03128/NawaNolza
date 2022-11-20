package com.example.nawanolza

import com.example.nawanolza.retrofit.MemberResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService{
    @POST("auth/kakao/callback")
    fun Login(
        @Body data: Map<String, String>
    ) : Call<MemberResponse> //아웃풋 정의
}