package com.example.administrator.convenientkotlin.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.example.administrator.convenientkotlin.ui.activities.MainActivity


/**
 * Created by Administrator on 2017/7/26 0026.
 */

class Receiver : BroadcastReceiver() {
    internal var isFirst = true
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != null) {
            if (isFirst) {
                val sayHelloIntent = Intent(context, MainActivity::class.java)
                sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                context.startActivity(sayHelloIntent)
                isFirst = false
            }

        }
    }

    companion object {
        internal val ACTION = "android.intent.action.BOOT_COMPLETED"
    }
}
