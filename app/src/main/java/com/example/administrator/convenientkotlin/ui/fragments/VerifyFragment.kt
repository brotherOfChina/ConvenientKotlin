package com.example.administrator.convenientkotlin.ui.fragments

import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.base.MyApplication
import com.example.administrator.convenientkotlin.domain.commands.HttpResult
import com.example.administrator.convenientkotlin.domain.model.VerifyOrderBean
import com.example.administrator.convenientkotlin.extensions.DelegatesExt
import com.example.administrator.convenientkotlin.extensions.getName
import com.example.administrator.convenientkotlin.extensions.hidePhone
import com.example.administrator.convenientkotlin.ui.activities.UserActivity
import com.example.administrator.convenientkotlin.ui.adapters.VerifyAdapter
import com.example.administrator.convenientkotlin.utils.SignUtil
import com.google.gson.Gson
import com.iflytek.cloud.*
import com.vise.log.ViseLog
import com.vise.xsnow.http.ViseHttp
import com.vise.xsnow.http.callback.ACallback
import com.vise.xsnow.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_verify.*
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * Created by Administrator on 2017/9/4 0004.
 * 验证单fragment
 */
class VerifyFragment : BaseFragment() {
    val m_user_id: String by DelegatesExt.preference(MyApplication.instance, UserActivity.USER_ID, UserActivity.D_USER_ID)
    val m_user_name: String by DelegatesExt.preference(MyApplication.instance, UserActivity.USER_NAME, UserActivity.D_USER_NAME)
    val m_store_name: String by DelegatesExt.preference(MyApplication.instance, UserActivity.STORE_NAME, UserActivity.D_STORE_NAME)
    var isSuccess = true
    var mEngineType = SpeechConstant.TYPE_CLOUD;
    lateinit var mTts: SpeechSynthesizer

    override fun bindEvent() {
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        et_verify_num.requestFocus()
        tv_title.text = m_store_name.getName()
        tv_title_.text = m_store_name.getName()
        try {

            mTts = SpeechSynthesizer.createSynthesizer(activity) { code -> isSuccess = code == ErrorCode.SUCCESS }
            if (isSuccess) {
                // 清空参数
                mTts.setParameter(SpeechConstant.PARAMS, null)
                // 根据合成引擎设置相应参数
                if (mEngineType == SpeechConstant.TYPE_CLOUD) {
                    mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
                    // 设置在线合成发音人
                    mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan")
                    //设置合成语速
                    mTts.setParameter(SpeechConstant.SPEED, "80")
                    //设置合成音调
                    mTts.setParameter(SpeechConstant.PITCH, "50")
                    //设置合成音量
                    mTts.setParameter(SpeechConstant.VOLUME, "50")
                } else {
                    mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL)
                    // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
                    mTts.setParameter(SpeechConstant.VOICE_NAME, "")
                    /**
                     * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
                     * 开发者如需自定义参数，请参考在线合成参数设置
                     */
                }
                //设置播放器音频流类型
                mTts.setParameter(SpeechConstant.STREAM_TYPE, "3")
                // 设置播放合成音频打断音乐播放，默认为true
                mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true")

                // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
                // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
                mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
                mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory().toString() + "/msc/tts.wav")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getLayoutID(): Int = R.layout.fragment_verify

    override fun processClick(view: View?) {
    }

    override fun initView(contentView: View?) {
        rv_verify.layoutManager = LinearLayoutManager(activity)

        et_verify_num.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //&&s.subSequence(0,3).toString().equals("0715")
                if (s?.length == 18) {
                    requestVerify(s.toString(), m_user_id)
                    ViseLog.i(m_user_id + "===" + m_user_name)
                }
            }

        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        et_verify_num.requestFocus()
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
                        et_verify_num.setText("")
                        val httpResult = HttpResult()
                        val jo = JSONObject(data)
                        httpResult.status = jo.optInt("status")
                        httpResult.msg = jo.optString("msg")
                        httpResult.response = jo.optString("data")
                        if (httpResult.status == 0) {
                            ViseLog.d(httpResult.response)
                            val result = Gson().fromJson(httpResult.response.toString(), VerifyOrderBean::class.java)
//                            val result = Gson().fromJson("{\n" +
//                                    "        \"audit_store_id\": \"1\",\n" +
//                                    "        \"audit_userid\": 581,\n" +
//                                    "        \"order_type\": \"1\",\n" +
//                                    "        \"order_no\": \"2017092016113571243224\",\n" +
//                                    "        \"store_id\": \"1\",\n" +
//                                    "        \"userid\": \"12010\",\n" +
//                                    "        \"username\": \"18404975605\",\n" +
//                                    "        \"goods_data\": [\n" +
//
//                                    "            {\n" +
//                                    "                \"item_id\": \"2305\",\n" +
//                                    "                \"item_status\": \"1\",\n" +
//                                    "                \"store_id\": \"1\",\n" +
//                                    "                \"goods_id\": \"1381\",\n" +
//                                    "                \"goods_sn\": \"2001934\",\n" +
//                                    "                \"goods_name\": \"统一阿萨姆原味奶茶500ml\",\n" +
//                                    "                \"goods_price\": \"3.99\",\n" +
//                                    "                \"goods_sett_price\": \"3.99\",\n" +
//                                    "                \"goods_img\": \"/a1/4a/10/2b/db/d0647800a5b58e951790b1.png\",\n" +
//                                    "                \"goods_num\": \"1\",\n" +
//                                    "                \"lock_stock\": \"0\"\n" +
//                                    "            },\n" +
//                                    "            {\n" +
//                                    "                \"item_id\": \"2305\",\n" +
//                                    "                \"item_status\": \"1\",\n" +
//                                    "                \"store_id\": \"1\",\n" +
//                                    "                \"goods_id\": \"1381\",\n" +
//                                    "                \"goods_sn\": \"2001934\",\n" +
//                                    "                \"goods_name\": \"统一阿萨姆原味奶茶500ml\",\n" +
//                                    "                \"goods_price\": \"3.99\",\n" +
//                                    "                \"goods_sett_price\": \"3.99\",\n" +
//                                    "                \"goods_img\": \"/a1/4a/10/2b/db/d0647800a5b58e951790b1.png\",\n" +
//                                    "                \"goods_num\": \"1\",\n" +
//                                    "                \"lock_stock\": \"0\"\n" +
//                                    "            },\n" +
//                                    "            {\n" +
//                                    "                \"item_id\": \"2305\",\n" +
//                                    "                \"item_status\": \"1\",\n" +
//                                    "                \"store_id\": \"1\",\n" +
//                                    "                \"goods_id\": \"1381\",\n" +
//                                    "                \"goods_sn\": \"2001934\",\n" +
//                                    "                \"goods_name\": \"统一阿萨姆原味奶茶500ml\",\n" +
//                                    "                \"goods_price\": \"3.99\",\n" +
//                                    "                \"goods_sett_price\": \"3.99\",\n" +
//                                    "                \"goods_img\": \"/a1/4a/10/2b/db/d0647800a5b58e951790b1.png\",\n" +
//                                    "                \"goods_num\": \"1\",\n" +
//                                    "                \"lock_stock\": \"0\"\n" +
//                                    "            }\n" +
//                                    "        ],\n" +
//                                    "        \"coupon_data\": {\n" +
//                                    "            \"coupon_type\": \"0\",\n" +
//                                    "            \"full_amount\": \"0.00\",\n" +
//                                    "            \"reduce_amount\": \"0.00\"\n" +
//                                    "        },\n" +
//                                    "        \"total_num\": 1,\n" +
//                                    "        \"total_amount\": \"3.99\",\n" +
//                                    "        \"ctime\": \"2017-09-20 16:13:37\"\n" +
//                                    "    }", VerifyOrderBean::class.java)
                            rl_verify.visibility = View.INVISIBLE
                            val stringBuffer = StringBuffer()
                            stringBuffer.append("您好，您已成功购买")
                            for (item in result.goods_data) {
                                stringBuffer.append(item.goods_name + item.goods_num + "件,")
                            }
                            stringBuffer.append("            ")
                            stringBuffer.append("共消费" + result.total_amount + "元")

                            mTts.startSpeaking(stringBuffer.toString(), object : SynthesizerListener {
                                override fun onBufferProgress(p0: Int, p1: Int, p2: Int, p3: String?) {
                                }

                                override fun onSpeakBegin() {
                                }

                                override fun onSpeakProgress(p0: Int, p1: Int, p2: Int) {
                                }

                                override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Bundle?) {
                                }

                                override fun onSpeakPaused() {
                                }

                                override fun onSpeakResumed() {
                                }

                                override fun onCompleted(p0: SpeechError?) {
                                }

                            })
                            rl_verified.visibility = View.VISIBLE
                            iv_waves.visibility = View.VISIBLE
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
                            tv_sure.visibility = View.VISIBLE
                            tv_num.text = "商品件数:" + result.total_num + ""
                            tv_amount.text = "合计：¥" + result.total_amount
                            tv_phone.text = "用户手机号：" + result.username.hidePhone()
                        } else {
                            activity.toast(httpResult.status.toString() + httpResult.msg + "")
                        }
                        tv_sure.setOnClickListener {
                            iv_waves.visibility = View.INVISIBLE

                            tv_sure.visibility = View.INVISIBLE
                            rl_verify.visibility = View.VISIBLE
                            rl_verified.visibility = View.INVISIBLE
                            et_verify_num.setText("")
                            et_verify_num.requestFocus()
                        }

                    }

                    override fun onFail(errCode: Int, errMsg: String) {
//                        dialog.dismiss()
                        et_verify_num.setText("")
                        ViseLog.i("$errCode+$errMsg")
                    }
                })

    }


}
