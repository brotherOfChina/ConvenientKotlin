package com.example.administrator.convenientkotlin.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.example.administrator.convenientkotlin.ui.activities.YSYActivity
import com.videogo.openapi.EZOpenSDK
import com.videogo.openapi.bean.EZAccessToken
import com.vise.log.ViseLog

/**
 * Created by Administrator on 2017/9/12 0012.
 * 萤石云广播
 */
class YSYReceiver : BroadcastReceiver(){
    override fun onReceive(context:  Context?, intent: Intent?) {

        if(TextUtils.equals(intent?.action,"com.videogo.action.OAUTH_SUCCESS_ACTION")){
            val accessToken:EZAccessToken=EZOpenSDK.getInstance().ezAccessToken
            EZOpenSDK.getInstance().ezAccessToken.accessToken=accessToken.accessToken
            ViseLog.i(accessToken.accessToken)
            val sayHelloIntent = Intent(context, YSYActivity::class.java)
            sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            sayHelloIntent.putExtra("accessToken",accessToken.toString())
            context?.startActivity(sayHelloIntent)
        }
    }

}
