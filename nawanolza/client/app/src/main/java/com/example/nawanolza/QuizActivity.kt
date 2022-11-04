package com.example.nawanolza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.databinding.ActivityQuizBinding
import com.example.nawanolza.retrofit.CharacterLocationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizActivity : AppCompatActivity() {


    var quizInfo = CharacterLocationResponse()
    lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //퀴즈 받아오기
        var retrofit = Retrofit.Builder()
            .baseUrl("https://k7d103.p.ssafy.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service = retrofit.create(GetCharacterService::class.java)


        service.GetCharacter().enqueue(object: Callback<CharacterLocationResponse> {

            override fun onResponse(
                call: Call<CharacterLocationResponse>,
                response: Response<CharacterLocationResponse>
            ) {
                val body = response.body()

                quizInfo = response.body() ?: CharacterLocationResponse()
//                println(characterInfo)

                println(quizInfo)
                println("=====okay======")
            }

            override fun onFailure(call: Call<CharacterLocationResponse>, t: Throwable) {
                println(call)
                println(t)
                println("====ㅇ[러디===")
            }

        })


    }
}