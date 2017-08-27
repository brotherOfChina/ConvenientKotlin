package com.example.administrator.convenientkotlin.data.Server

import android.util.Log
import com.google.gson.Gson
import java.net.URL


/**
 * Created by Administrator on 2017/8/27 0027.
 */
class NavTypeByRequest(val map:Map<String,String>,val gson:Gson=Gson() ){
    companion object {

        private val URLs = "http://www.jinxiangqizhong.com:83/apicontrol/conv/"

    }
    //:NavTypeResult
    fun excute(){
        val navTypeJsonStr=URL(URLs+map).readText()
        Log.i("zpj", ": "+navTypeJsonStr)
//        return gson.fromJson(navTypeJsonStr,NavTypeResult::class.java)
    }
}