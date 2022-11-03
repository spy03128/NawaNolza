package com.example.nawanolza

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nawanolza.retrofit.CharacterResponse
import com.example.nawanolza.retrofit.MemberResponse
import kotlinx.android.synthetic.main.activity_character.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterActivity : AppCompatActivity() {

    var characterInfo = CharacterResponse()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)



        val memberInfo: MemberResponse = intent.getSerializableExtra("memberInfo") as MemberResponse
        close.setOnClickListener(){
            finish()
        }

        //캐릭터 도감 받아오기
        var retrofit = Retrofit.Builder()
            .baseUrl("https://k7d103.p.ssafy.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service = retrofit.create(CharacterService::class.java)
        service.GetCharacter(memberInfo.member.id, type = "", sort = "" )



        service.GetCharacter(memberInfo.member.id,type = "", sort = "").enqueue(object: Callback<CharacterResponse> {

            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                val body = response.body()

                characterInfo = response.body() ?: CharacterResponse()
//                println(characterInfo)

                println(characterInfo)
                println("=====캐릭터 정보======")
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                println(call)
                println(t)
                println("====캐릭터 정보 에러===")
            }

        })


    }
}