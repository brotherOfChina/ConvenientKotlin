package com.example.administrator.convenientkotlin.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.convenientkotlin.ui.activities.MainActivity;
import com.vise.log.ViseLog;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class Receiver extends BroadcastReceiver {
    boolean isFirst=true;
    @Override
    public void onReceive(Context context, Intent intent) {
        ViseLog.d(intent.getAction());
        if (intent.getAction()!=null){
           if (isFirst){
               Intent sayHelloIntent=new Intent(context,MainActivity.class);
               sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(sayHelloIntent);
               isFirst=false;
           }

        }
    }
}
