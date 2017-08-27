package com.example.administrator.convenientkotlin.data.Server

import com.example.administrator.convenientkotlin.domain.model.NavBean

/**
 * Created by Administrator on 2017/8/27 0027.
 */
data class NavTypeResult(val status:Int,val msg :String ,val navTypes:List<NavBean>)