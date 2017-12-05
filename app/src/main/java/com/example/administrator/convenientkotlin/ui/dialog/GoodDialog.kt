package com.example.administrator.convenientkotlin.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.extensions.getQRCode
import com.example.administrator.convenientkotlin.extensions.show
import kotlinx.android.synthetic.main.dialog_good.*


/**
 * Created by Administrator on 2017/9/4 0004.
 * 商品二维码展示框
 *
 */
class GoodDialog(ctx:Context,val goodBean: GoodBean, themeResId:Int=R.style.loadingDialog): Dialog(ctx,themeResId) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_good)
        with(goodBean){
            iv_qr_code.setImageBitmap(Qrcode.getQRCode())
            iv_good_img.show(image_default)
            tv_goods_name.text=name
            tv_goods_price.text="￥"+price
            tv_goods_intro.text=memo
            iv_close.setOnClickListener{dismiss()}

        }
        setCanceledOnTouchOutside(true)


    }


}


