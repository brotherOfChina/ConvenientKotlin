package com.example.administrator.convenientkotlin.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.data.Server.NavTypeByRequest
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        rv_nav.layoutManager=LinearLayoutManager(this)
        loadNavBean()

    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadNavBean()=async(UI) {
        val map = mapOf<String,String>("c" to "Nav","m" to "Screen" ,"sign" to "c56bb05340cfc3ae006945cc1520ace3" ,"v" to "CV1" )
        bg {  NavTypeByRequest(map).excute() }

    }
}
