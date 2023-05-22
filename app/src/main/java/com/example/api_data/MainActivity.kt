package com.example.api_data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


data class User(val busRouteAbrv: Int, val busRouteId: Int, val busRouteNm: Int)


// ApiService 인터페이스 정의
interface ApiService {
    // API 요청 메소드
    // 예를 들어, 사용자 정보를 가져오는 API를 호출하는 메소드입니다.
    // 실제 API의 엔드포인트와 파라미터에 맞게 수정해야 합니다.
    @GET("/users")
    suspend fun getUsers(): List<User>
}

suspend fun main() {
    // Gson 객체 생성
    val gson: Gson = GsonBuilder().create()

    // Retrofit 객체 생성 및 설정
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?ServiceKey=aa%2Fun7llG%2BBArgYxreQ7G4BrqKgLkMU71r%2B5JCtH2eFC5l6TkorlwIwRBc0acbCNk9XiTsDSMTJl4Ik%2FqY89Tw%3D%3D&strSrch=3") // API의 기본 URL을 지정합니다.
        .addConverterFactory(GsonConverterFactory.create(gson)) // Gson 변환기를 추가합니다.
        .build()

    // ApiService 인터페이스 정의


    // ApiService 인터페이스를 사용하여 Retrofit 인스턴스 생성
    val apiService: ApiService = retrofit.create(ApiService::class.java)

    // API 호출 및 응답 처리
    // 예를 들어, 사용자 정보를 가져오는 API를 호출하고 응답을 받아옵니다.
    // 실제 API 호출 및 응답 처리 코드를 작성해야 합니다.
    val users: List<User> = apiService.getUsers()
    // 여기서 users 변수에는 API에서 받아온 사용자 목록이 들어 있습니다.
    // 이후 원하는 작업을 수행할 수 있습니다.
}

class MainActivity : AppCompatActivity()  {

    //private lateinit var textView: TextView
   // private lateinit var textView2: TextView
    val button: Button = findViewById(R.id.button)
    val textView: TextView = findViewById(R.id.textView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val users: List<User> = apiService.getUsers()
                    val userText = StringBuilder()
                    for (user in users) {
                        userText.append("Name: ${user.busRouteAbrv}, Email: ${user.busRouteId}\n")
                    }
                    textView.text = userText.toString()
                } catch (e: Exception) {
                    textView.text = "API 요청 실패: ${e.message}"
                }
            }
        }
       // textView = findViewById(R.id.textView)
       // textView2 = findViewById(R.id.textView2)
    }
}



