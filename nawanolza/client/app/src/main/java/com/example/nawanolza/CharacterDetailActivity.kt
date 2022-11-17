package com.example.nawanolza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.retrofit.CharacterDetailResponse
import kotlinx.android.synthetic.main.activity_character.*
import kotlinx.android.synthetic.main.activity_character_detail.*
import kotlinx.android.synthetic.main.activity_character_detail.close
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterDetailActivity : AppCompatActivity() {

    var characterDetailInfo = CharacterDetailResponse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)


        val characterId = intent.getIntExtra("characterId", -1)
        val memberId = LoginUtil.getMember(this)!!.id


        var retrofit = Retrofit.Builder()
            .baseUrl("https://k7d103.p.ssafy.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service = retrofit.create(CharacterDetailService::class.java)




        service.GetCharacterDetail(memberId, characterId).enqueue(object: Callback<CharacterDetailResponse> {

            override fun onResponse(
                call: Call<CharacterDetailResponse>,
                response: Response<CharacterDetailResponse>
            ) {

                characterDetailInfo = response.body() ?: CharacterDetailResponse()

                chImage.setImageResource(MarkerImageUtil.getImage(characterId.toLong()))
                chTitle.text = characterDetailInfo.name
                chContent.text = characterDetailInfo.description
                chLv.text = "Lv." + characterDetailInfo.level

            }

            override fun onFailure(call: Call<CharacterDetailResponse>, t: Throwable) {
                println(call)
                println(t)
                println("====에러=====")
            }

        })

        close.setOnClickListener(){
            finish()
        }


    }
}