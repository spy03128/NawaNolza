package com.example.nawanolza

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nawanolza.databinding.ActivityCharacterBinding
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

    lateinit var binding: ActivityCharacterBinding
    lateinit var adapter: CharacterRvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        var typeName = ""
        var sortName = ""

        displayCollectionBySort(service, memberInfo, typeName, sortName)


        sortDogam.setOnClickListener(){
            sortName = ""
            displayCollectionBySort(service, memberInfo, typeName, sortName)
            sortDogam.setImageResource(R.drawable.dogam_on)
            sortLevel.setImageResource(R.drawable.level_off)
        }

        sortLevel.setOnClickListener(){
            sortName = "level"
            displayCollectionBySort(service, memberInfo, typeName, sortName)
            sortDogam.setImageResource(R.drawable.dogam_off)
            sortLevel.setImageResource(R.drawable.level_on)
        }
        cBtnAll.setOnClickListener(){
            typeName = ""
            displayCollectionBySort(service, memberInfo, typeName, sortName)
        }
        cBtn1.setOnClickListener(){
            typeName = "힘"
            displayCollectionBySort(service, memberInfo, typeName, sortName)
        }
        cBtn2.setOnClickListener(){
            typeName = "지능"
            displayCollectionBySort(service, memberInfo, typeName, sortName)
        }
        cBtn3.setOnClickListener(){
            typeName = "민첩"
            displayCollectionBySort(service, memberInfo, typeName, sortName)
        }
        cBtn4.setOnClickListener(){
            typeName = "방어"
            displayCollectionBySort(service, memberInfo, typeName, sortName)
        }








    }

    private fun displayCollectionBySort(
        service: CharacterService,
        memberInfo: MemberResponse,
        typeName: String,
        sortName: String
    ) {
        service.GetCharacter(memberInfo.member.id, type = typeName, sort = sortName)
            .enqueue(object : Callback<CharacterResponse> {

                override fun onResponse(
                    call: Call<CharacterResponse>,
                    response: Response<CharacterResponse>
                ) {
                    val body = response.body()

                    characterInfo = response.body() ?: CharacterResponse()
                    println(memberInfo.member.id)
                    println(characterInfo)
                    println("=====캐릭터 정보======")


                    adapter = CharacterRvAdapter(characterInfo.collection, application)
                    binding.charRecyclerView.adapter = adapter
                    binding.charRecyclerView.layoutManager =
                        GridLayoutManager(this@CharacterActivity, 3)


                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    println(call)
                    println(t)
                    println("====캐릭터 정보 에러===")
                }

            })
    }
}