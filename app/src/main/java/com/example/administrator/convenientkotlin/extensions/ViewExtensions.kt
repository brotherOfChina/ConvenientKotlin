package com.example.administrator.convenientkotlin.extensions

import android.content.Context
import android.view.View
import android.widget.TextView

/**
 * Created by Administrator on 2017/8/27 0027.
 * view 的工具类
 */
val View.ctx:Context get() = context
var TextView.textColor:Int
    get() = currentTextColor
    set(value) = setTextColor(value)
fun View.slideExt(){
    if (translationY==0f){
        animate().translationY(-height.toFloat())
    }
}
fun View.slideEnter(){
    if (translationY<0f){
        animate().translationY(0f)
    }
}