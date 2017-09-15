package com.example.administrator.convenientkotlin.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.ui.fragments.VerifyFragment
import kotlinx.android.synthetic.main.dialog_store_names.*
import kotlinx.android.synthetic.main.fragment_verify.*

/**
 * Created by Administrator on 2017/9/11 0011.
 * 店名列表dialog
 */
class ListDialog( val fragment: VerifyFragment, private val maps:  Map<String,String>, themeResId:Int= R.style.loadingDialog): Dialog(fragment.activity,themeResId) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_store_names)
        val names= mutableListOf<String>()
        val user_ids= mutableListOf<String>()
        for ((k,v) in maps){
            names.add(k)
            user_ids.add(v)
        }
       lv_name.adapter=ArrayAdapter(fragment.activity,android.R.layout.simple_list_item_1 ,names)
        lv_name.setOnItemClickListener { _, _, i, _ ->
            fragment.store_name.text=names[i]
            fragment.user_id=user_ids[i]
            dismiss()
        }
        setCanceledOnTouchOutside(true)


    }


}