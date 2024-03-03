package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.timer

// View.OnClickListener는 안드로이드에서 클릭 이벤트를 처리하기 위한 인터페이스
class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var btn_start : Button
    private lateinit var btn_refresh: Button
    private lateinit var tv_minute: TextView
    private lateinit var tv_second: TextView
    private lateinit var tv_millisecond: TextView

    private var isRunning = false

    private var timer : Timer? = null
    private var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //뷰 초기화 (버튼, 텍스트뷰를 메인액티비티에서 사용할수 있도록 불러와주는것)
        btn_start = findViewById(R.id.btn_start)
        btn_refresh = findViewById(R.id.btn_refresh)
        tv_minute = findViewById(R.id.tv_minute)
        tv_second = findViewById(R.id.tv_second)
        tv_millisecond = findViewById(R.id.tv_millisecond)

        //여기서 this는 View.OnClickListener를 뜻함
        btn_start.setOnClickListener(this)
        btn_refresh.setOnClickListener(this)

    }

//    View.OnClickListener는 안드로이드에서 클릭 이벤트를 처리하기 위한 인터페이스로, onClick(View v) 메서드를 오버라이드하여 클릭 이벤트가 발생했을 때 실행할 코드를 정의할 수 있습니다.
//    MainActivity 클래스 내에서 btn_start와 btn_refresh 버튼에 대해 setOnClickListener(this)를 호출함으로써, 이 버튼들의 클릭 이벤트가 발생하면 MainActivity의 onClick 메서드가 호출되도록 설정되어 있습니다.
    override fun onClick(view: View?) {
       when(view?.id){
           R.id.btn_start->{
               if(isRunning){
                   pause()
               } else {
                   start()
               }
           }

           R.id.btn_refresh->{
               refresh()
           }

       }
    }

    private fun start(){
        btn_start.text = getString(R.string.btn_puase)
        btn_start.setBackgroundColor(getColor(R.color.btn_puase))
        isRunning = true
        // 타이머 함수는 항상 백그라운드 쓰레드에서 실행됨
        timer = timer(period = 10){
            time++
            val milli_second = time % 100
            val second = (time % 6000) / 100
            val minute = time / 6000
            // 백그라운드 쓰레드에서는 UI를 수정할수 없음
            runOnUiThread{
                if(isRunning){
                tv_millisecond.text = if(milli_second < 10) ".0${milli_second}" else ".${milli_second}"
                tv_second.text = if(second<10) ":0${second}" else "${second}"
                tv_minute.text = "${minute}"
            }}
        }
    }
    private fun pause(){
        btn_start.text = getString(R.string.btn_start)
        btn_start.setBackgroundColor(getColor(R.color.btn_start))
        isRunning = false
        timer?.cancel()
    }
    private fun refresh(){
        timer?.cancel()
        btn_start.text = getString(R.string.btn_start)
        btn_start.setBackgroundColor(getColor(R.color.btn_start))
        isRunning = false

        time = 0
        tv_millisecond.text = ".00"
        tv_second.text = ":00"
        tv_minute.text = "00"
    }
}

