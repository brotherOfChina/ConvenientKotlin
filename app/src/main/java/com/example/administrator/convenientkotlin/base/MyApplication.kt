package com.example.administrator.convenientkotlin.base

import android.app.Application
import android.util.Log
import com.example.administrator.convenientkotlin.extensions.DelegatesExt
import com.videogo.openapi.EZOpenSDK
import com.vise.log.ViseLog
import com.vise.log.inner.LogcatTree
import com.vise.utils.assist.SSLUtil
import com.vise.xsnow.http.ViseHttp
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Administrator on 2017/8/29 0029.
 * APP
 */
class MyApplication : Application() {
    companion object {
        var instance: MyApplication by DelegatesExt.notNullSingleValue()
    }
    fun getApplication():Application = instance
    override fun onCreate() {
        super.onCreate()
//        CrashHandlerUtil.getInstance().init(this)
        /**
         * sdk日志开关，正式发布需要去掉
         */
        EZOpenSDK.showSDKLog(false);
        /**
         * 设置是否支持P2P取流,详见api
         */
        EZOpenSDK.enableP2P(true);

        /**
         * APP_KEY请替换成自己申请的
         */
        EZOpenSDK.initLib(this, "16c4d77101c8406c8a207b0dd339839c", "");
        instance = this
        ViseHttp.init(instance)
        ViseLog.getLogConfig()
                .configAllowLog(false)//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("zpj")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE)//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(LogcatTree())//添加打印日志信息到Logcat的树
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl("http://www.jinxiangqizhong.com:83/apicontrol/conv/")
                //配置全局请求头
//                .globalHeaders(HashMap())
//                //配置全局请求参数
//                .globalParams(HashMap())
//                //配置读取超时时间，单位秒
//                .readTimeout(30)
//                //配置写入超时时间，单位秒
//                .writeTimeout(30)
//                //配置连接超时时间，单位秒
//                .connectTimeout(30)
//                //配置请求失败重试次数
//                .retryCount(3)
//                //配置请求失败重试间隔时间，单位毫秒
//                .retryDelayMillis(1000)
//                //配置是否使用cookie
//                .setCookie(true)
//                //配置自定义cookie
//                //                .apiCookie(new ApiCookie(this))
//                //配置是否使用OkHttp的默认缓存
//                .setHttpCache(true)
//                //配置OkHttp缓存路径
//                //                .setHttpCacheDirectory(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR))
//                //配置自定义OkHttp缓存
//                //                .httpCache(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
//                //配置自定义离线缓存
//                //                .cacheOffline(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
//                //配置自定义在线缓存
//                //                .cacheOnline(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
//                //配置开启Gzip请求方式，需要服务器支持
//                //                .postGzipInterceptor()
//                //配置应用级拦截器
//                .interceptor(HttpLogInterceptor()
//                        .setLevel(HttpLogInterceptor.Level.NONE))
                //配置网络拦截器
//                                .networkInterceptor( NoCacheInterceptor())
                //配置转换工厂
                .converterFactory(GsonConverterFactory.create())
                //配置适配器工厂
                .callAdapterFactory(RxJava2CallAdapterFactory.create())
                //配置请求工厂
                //                .callFactory(new Call.Factory() {
                //                    @Override
                //                    public Call newCall(Request request) {
                //                        return null;
                //                    }
                //                })
                //配置连接池
                //                .connectionPool(new ConnectionPool())

                //配置SSL证书验证
                .SSLSocketFactory(SSLUtil.getSslSocketFactory(null, null, null))//配置主机代理
        //                .proxy(new Proxy(Proxy.Type.HTTP, new SocketAddress() {}))
    }

}