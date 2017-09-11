package com.example.administrator.convenientkotlin.ui.fragments

import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.commands.HttpResult
import com.example.administrator.convenientkotlin.domain.model.VerifyOrderBean
import com.example.administrator.convenientkotlin.extensions.hidePhone
import com.example.administrator.convenientkotlin.ui.adapters.VerifyAdapter
import com.example.administrator.convenientkotlin.ui.dialog.ListDialog
import com.example.administrator.convenientkotlin.utils.SignUtil
import com.google.gson.Gson
import com.vise.log.ViseLog
import com.vise.xsnow.http.ViseHttp
import com.vise.xsnow.http.callback.ACallback
import com.vise.xsnow.ui.BaseFragment
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_verify.*
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * Created by Administrator on 2017/9/4 0004.
 * 验证单fragment
 */
class VerifyFragment : BaseFragment() {
    var user_id:String="64259"
    override fun bindEvent() {
    }

    override fun initData() {
        et_verify_num.requestFocus()
    }

    override fun getLayoutID(): Int = R.layout.fragment_verify

    override fun processClick(view: View?) {
    }

    override fun initView(contentView: View?) {
        rv_verify.layoutManager = LinearLayoutManager(activity)
        store_name.text="(华都店)"
        val names= mapOf(
                "(华都店)" to "64259",
                "(迎宾西街店)" to "76620",
                "(颐景店)" to "2342",
                "(东阳店)" to "1888",
                "(新华店)"  to "93044"
        )
        val storeDialog:ListDialog by lazy {
            ListDialog(this,names)
        }
        store_name.setOnClickListener {
            storeDialog.show()
            ViseLog.i(user_id)
        }
        et_verify_num.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //&&s.subSequence(0,3).toString().equals("0715")
                if (s?.length == 18) {
                    requestVerify(s.toString(),user_id )

                }

            }

        })
    }

    private fun requestVerify(num: String, user_id: String) {
        var s: String
        val map = mutableMapOf<String, String>()
        map.put("v", "CV1")
        map.put("m", "Order")
        map.put("c", "Audit")
        map.put("rec_code", num)
        map.put("audit_userid", user_id)
        map.put("sign", SignUtil.getSignString(map))
        ViseLog.i(map)
        ViseHttp.POST().addParams(map)
                .request(object : ACallback<String>() {
                    override fun onSuccess(data: String) {

                        val httpResult = HttpResult()
                        val jo = JSONObject(data)
                        httpResult.status = jo.optInt("status")
                        httpResult.msg = jo.optString("msg")
                        httpResult.response = jo.optString("data")
                        if (httpResult.status == 0) {
                            ViseLog.d(httpResult.response)
                            val result = Gson().fromJson(httpResult.response,VerifyOrderBean::class.java)
                            if (result != null) {
                                val adapter: VerifyAdapter by lazy {
                                    VerifyAdapter(result.goods_data)
                                }
                                rv_verify.adapter = adapter
                            }
                            s = when (result.coupon_data.coupon_type) {
                                "1" -> "全场通用券"//通用 全体店
                                "2" -> "全场通用券"//通用个体店
                                "3" -> "满减卷"//满减卷
                                "4" -> "满减卷"
                                else -> ""
                            }
                            tv_order.text = "订单号：" + result.order_no
                            if (result.coupon_data.coupon_type == "0") {
                                tv_preferential.text = "优惠:无"
                            } else {
                                tv_preferential.text = "优惠:" + s + " 满" + result.coupon_data.full_amount + "减" + result.coupon_data.reduce_amount

                            }

                            tv_num.text = "商品件数:" + result.total_num + ""
                            tv_amount.text = "合计：¥" + result.total_amount
                            tv_phone.text = "用户手机号：" + result.username.hidePhone()
                        } else {
                            activity.toast(httpResult.status.toString() + httpResult.msg + "")
                        }
                        Observable.create(ObservableOnSubscribe<Int> { emitter ->
                            Thread.sleep(3000)
                            emitter.onNext(1)
                        }).subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    et_verify_num.setText("")
                                })
                    }
                    override fun onFail(errCode: Int, errMsg: String) {
                        ViseLog.i("$errCode+$errMsg")
                    }
                })

    }


}
