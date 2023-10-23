package com.demo.aspectj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    var index = 0

    @SingleClick
    fun onClick(view: View) {
        index++
        (view as TextView).text = "点击：$index"
    }
}