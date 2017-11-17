package com.example.administrator.convenientkotlin.extensions

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.administrator.convenientkotlin.R



/**
 * Created by Administrator on 2017/8/27 0027.
 * view 的工具类
 */
val View.ctx: Context get() = context
var TextView.textColor: Int
    get() = currentTextColor
    set(value) = setTextColor(value)


fun ImageView?.show(url: String?) {
    Glide.with(this?.ctx).load(url)
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.default_error)
            .centerCrop()
            .crossFade()
            .dontAnimate().
            into(this)
}
fun Float.dp2px(context: Context):Int{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            this, context.resources.displayMetrics).toInt()
}