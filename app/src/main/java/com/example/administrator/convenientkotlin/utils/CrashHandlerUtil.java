package com.example.administrator.convenientkotlin.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class CrashHandlerUtil implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    // CrashHandler 实例
    private static CrashHandlerUtil INSTANCE = new CrashHandlerUtil();

    // 法式的 Context 工具
    private Context mContext;

    // 体系默认的 UncaughtException 处置惩罚类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    // 用来存储装备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    // 用于花样化日期,作为日记文件名的一部门
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /** 包管只有一个 CrashHandler 实例 */
    private CrashHandlerUtil() {
    }

    /** 获取 CrashHandler 实例 ,单例模式 */
    public static CrashHandlerUtil getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;

        // 获取体系默认的 UncaughtException 处置惩罚器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该 CrashHandler 为法式的默认处置惩罚器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 产生时会转入该函数来处置惩罚
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 若是用户没有处置惩罚则让体系默认的异常处置惩罚器来处置惩罚
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }

            // 退出法式,注释下面的重启启动法式代码
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(-1);
            // 从新启动法式，注释上面的退出法式
           /*Intent intent = new Intent();
           intent.setClass(mContext,BaseActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           mContext.startActivity(intent);
           android.os.Process.killProcess(android.os.Process.myPid());*/
        }
    }

    /**
     * 自界说毛病处置惩罚，网络毛病信息，发送毛病陈诉等操作均在此完成
     *
     * @param ex
     * @return true：若是处置惩罚了该异常信息；不然返回 false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }


        // 网络装备参数信息
        collectDeviceInfo(mContext);
        // 生存日记文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 网络装备参数信息
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 生存毛病信息到文件中
     *
     * @param ex
     * @return  返回文件名称,便于将文件传送到办事器
     */
    private String saveCrashInfo2File(Throwable ex) {
        String time = formatter.format(new Date());
        StringBuffer sb = new StringBuffer();
        sb.append(time+"\n");
        for (Map.Entry<String, String> entry : infos.entrySet()) {  //装备信息
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result+"\n");
        try {

            String fileName = "error.log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file=new File(path+fileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(path+fileName, true);
                fos.write(sb.toString().getBytes());
                fos.close();
            }

            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }

        return null;
    }
}
