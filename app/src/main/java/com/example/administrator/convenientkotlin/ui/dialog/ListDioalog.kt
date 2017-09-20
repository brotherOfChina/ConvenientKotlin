package com.example.administrator.convenientkotlin.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.ui.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_store_names.*

/**
 * Created by Administrator on 2017/9/11 0011.
 * 店名列表dialog
 */
class ListDialog( val fragment: MainActivity, private val maps:  Map<String,String>, themeResId:Int= R.style.loadingDialog): Dialog(fragment,themeResId) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_store_names)
        val names= mutableListOf<String>()
        val user_ids= mutableListOf<String>()
        for ((k,v) in maps){
            names.add(k)
            user_ids.add(v)
        }
       lv_name.adapter=ArrayAdapter(fragment,android.R.layout.simple_list_item_1 ,names)
        lv_name.setOnItemClickListener { _, _, i, _ ->
            fragment.store_name.text=names[i]
            when(names[i]){
//                "华都店" -> MainActivity.store_id="1"
//                "迎宾西街店" -> MainActivity.store_id="6"
//                "颐景店" -> MainActivity.store_id="2"
//                "东阳店" -> MainActivity.store_id="3"
//                "新华店" -> MainActivity.store_id="5"
//                "蕴华店" -> MainActivity.store_id="7"
            }
//            MainActivity.user_id=user_ids[i]
            dismiss()
        }
        setCanceledOnTouchOutside(true)


    }


}