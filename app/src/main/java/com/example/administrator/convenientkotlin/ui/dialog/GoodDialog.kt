package com.example.administrator.convenientkotlin.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.extensions.show
import kotlinx.android.synthetic.main.dialog_good.*


/**
 * Created by Administrator on 2017/9/4 0004.
 * 商品二维码展示框
 *
 */
class GoodDialog(ctx:Context,val goodBean: GoodBean): Dialog(ctx) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_good)
        iv_good_img.show(goodBean.image_default)

    }


}