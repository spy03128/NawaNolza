package com.example.nawanolza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nawanolza.databinding.ActivityCharacterBinding
import com.example.nawanolza.databinding.ActivityCharacterDetailBinding
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
    lateinit var binding: ActivityCharacterDetailBinding
    lateinit var adapter: CharacterDetailRvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                println(characterDetailInfo.history)
                adapter = CharacterDetailRvAdapter(characterDetailInfo.history.asReversed(), application)
                binding.mRecyclerView.adapter = adapter
                binding.mRecyclerView.layoutManager =
                    GridLayoutManager(this@CharacterDetailActivity, 1)


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