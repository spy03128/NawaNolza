package com.example.nawanolza

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nawanolza.retrofit.MemberResponse
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.util.helper.Utility
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)


        // 로그인 정보 확인
//        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//            if (error != null) {
//                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
//            }
//            else if (tokenInfo != null) {
//                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
//
//            }
//        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        println("기타에러 "+ error.toString())
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                println("토큰  " + token)

//                finish()

                //레트로핏
                var retrofit = Retrofit.Builder()
                    .baseUrl("https://k7d103.p.ssafy.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                var service = retrofit.create(LoginService::class.java)


                service.Login(mapOf("accessToken" to token.accessToken)).enqueue(object: Callback<MemberResponse> {

                    override fun onResponse(
                        call: Call<MemberResponse>,
                        response: Response<MemberResponse>
                    ) {
//                        response.body()?.let { updateMember(it) }
                        var memberInfo = response.body() ?: MemberResponse()

                        LoginUtil.setMemberInfo(this@LoginActivity, memberInfo)

                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                        println(call)
                        println(t)
                    }

                })

            }
        }

        loginButton.setOnClickListener{

            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)


            }else{
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }

//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.loginActivity, fragment_home)
//                .commit()

        }
    }
//
//    private fun updateMember(MemberData: MemberResponse) {
//        println("==========멤버 데이터=======")
//        println(MemberData.member)
//
//
//    }
}