package com.example.administrator.convenientkotlin.domain.model

/**
 * Created by Administrator on 2017/8/27 0027.
 */
data class NavBean( val nav_id: String,  val nav_type: String,
                    val nav_name: String,  val icon: String,
                    val m_timestamp: String,
                    val is_default: String , val is_layout: String,
                    val category_id: String)
data class NavList(val status:Int,val msg :String,val navList:List<NavBean>){
    val size:Int  get()=navList.size
    operator fun get(position:Int)=navList[position]

}