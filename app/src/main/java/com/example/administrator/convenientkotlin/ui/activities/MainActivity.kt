package com.example.administrator.convenientkotlin.ui.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.base.MyApplication
import com.example.administrator.convenientkotlin.domain.model.BuyData
import com.example.administrator.convenientkotlin.domain.model.NavBean
import com.example.administrator.convenientkotlin.domain.model.ResponseNavBean
import com.example.administrator.convenientkotlin.domain.model.YsyBean
import com.example.administrator.convenientkotlin.extensions.DelegatesExt
import com.example.administrator.convenientkotlin.extensions.getName
import com.example.administrator.convenientkotlin.extensions.hidePhone
import com.example.administrator.convenientkotlin.ui.adapters.FrgmentAdapter
import com.example.administrator.convenientkotlin.ui.adapters.NavAdapter
import com.example.administrator.convenientkotlin.ui.dialog.ListDialog
import com.example.administrator.convenientkotlin.ui.fragments.GoodsFragment
import com.example.administrator.convenientkotlin.ui.fragments.RXFragment
import com.example.administrator.convenientkotlin.ui.fragments.TypeFragment
import com.example.administrator.convenientkotlin.ui.fragments.VerifyFragment
import com.example.administrator.convenientkotlin.utils.SignUtil
import com.ezvizuikit.open.EZUIError
import com.ezvizuikit.open.EZUIKit
import com.ezvizuikit.open.EZUIPlayer
import com.videogo.openapi.bean.EZDeviceInfo
import com.vise.log.ViseLog
import com.vise.xsnow.http.ViseHttp
import com.vise.xsnow.http.callback.ACallback
import com.vise.xsnow.ui.BaseActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.configuration
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : BaseActivity() {


    val m_store_id: String by DelegatesExt.preference(MyApplication.instance, UserActivity.STORE_ID, UserActivity.D_STORE_ID)
    val m_store_name: String by DelegatesExt.preference(MyApplication.instance, UserActivity.STORE_NAME, UserActivity.D_STORE_NAME)
    val version = "2"
    var accessToken: String by DelegatesExt.preference(MyApplication.instance, LensActivity.ACCESS_TOKEN, LensActivity.DEFAULT_ACCESS_TOKEN)
    var url_top: String by DelegatesExt.preference(MyApplication.instance, LensActivity.URL_Top, LensActivity.DEFAULT_URL_TOP)
    var url_bottom: String by DelegatesExt.preference(MyApplication.instance, LensActivity.URL_BOTTOM, LensActivity.DEFAULT_URL_BOTTOM)
    lateinit var ezPlayTop: EZUIPlayer
    lateinit var ezPlayBottom: EZUIPlayer

    override fun bindEvent() {

        //请求数据所用的参数，sign将用一个通用方法进行添加
        val map = mutableMapOf<String, String>()
        map.put("c", "Nav")
        map.put("m", "Screen")
        map.put("v", "CV1")

        map.put("sign", SignUtil.getSignString(map))
        ViseHttp.POST().addParams(map)
                .request(object : ACallback<ResponseNavBean<NavBean>>() {
                    override fun onSuccess(data: ResponseNavBean<NavBean>?) {
                        if (data != null) {
                            val nav = data.data
                            rv_nav.adapter = NavAdapter(nav) {
                                when {
                                    it.nav_id == "2" -> vp_content.currentItem = 1
                                    it.nav_id == "3" -> vp_content.currentItem = 0
                                    it.nav_id == "4" -> vp_content.currentItem = 2
                                    it.nav_id == "5" -> vp_content.currentItem = 3
                                }
                            }
                        }
                    }

                    override fun onFail(errCode: Int, errMsg: String?) {
                        ViseLog.i(errMsg + errCode)
                    }

                })
    }



    override fun initView() {

        store_name.text = "华都店"
        val names = mapOf(
                "华都店" to "64259",
                "迎宾西街店" to "76620",
                "颐景店" to "2342",
                "东阳店" to "1888",
                "新华店" to "93044"
        )
        val storeDialog: ListDialog by lazy {
            ListDialog(this, names)
        }
        store_name.setOnClickListener {
            //            storeDialog.show()

//            ViseLog.i(user_id)
            startActivity(Intent(this@MainActivity, UserActivity::class.java))
        }
        val fragmentList = listOf<Fragment>(
                RXFragment(), TypeFragment(), GoodsFragment(), VerifyFragment()
        )
        vp_content.adapter = FrgmentAdapter(supportFragmentManager, fragmentList)
        vp_content.currentItem = 1
        vp_content.offscreenPageLimit = 4
    }

    fun getBuyData(store_id: String) {
        val currentTime = System.currentTimeMillis() / 1000
        val startTime = System.currentTimeMillis() / 1000 - 24 * 60 * 60 * 7
        /**
         * v:CV1
        m:Order
        c:List
        store_id:1
        rec_type:1
        s_time:1499999520
        e_time:1505183520
        page:1
        perpage:2
        sign:96d72983c955cc5a310e673c07b75328
         */
        val map = mutableMapOf<String, String>()
        map.put("v", "CV1")
        map.put("m", "Order")
        map.put("c", "List")
        map.put("store_id", store_id)
        map.put("rec_type", "1")
        map.put("s_time", "$startTime")
        map.put("e_time", "$currentTime")
        map.put("page", "1")
        map.put("perpage", "10")
        map.put("sign", SignUtil.getSignString(map))
        ViseHttp.POST().addParams(map).request(object : ACallback<BuyData>() {
            override fun onSuccess(data: BuyData?) {
                if (data?.status == 0) {
                    for (bean in data.data.list) {
                        val ll_content: View by lazy {
                            View.inflate(mContext, R.layout.adapter_flipper, null)
                        }
                        val tv: TextView = ll_content.findViewById(R.id.tv_data) as TextView
                        var s = StringBuffer()
                        for (item in bean.items) {
                            s = s.append("“" + item.goods_name + "”*" + item.goods_num)
                        }
                        tv.text = "用户" + bean.phone.hidePhone() + "购买了:" + s
                        vp_buy_data.addView(ll_content)
                    }
                    vp_buy_data.animation
                    vp_buy_data.isAutoStart = true
                    vp_buy_data.startFlipping()
                }
                Observable.create(ObservableOnSubscribe<Int> { emitter ->
                    Thread.sleep(60000 * 10)
                    emitter.onNext(1)
                }).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            getBuyData(m_store_id)
                        })
            }

            override fun onFail(errCode: Int, errMsg: String?) {
                Observable.create(ObservableOnSubscribe<Int> { emitter ->
                    Thread.sleep(60000 * 10)
                    emitter.onNext(1)
                }).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            getBuyData(m_store_id)
                        })
                toast(errMsg + "")
            }
        })
    }

    override fun initData() {

    }

    override fun processClick(view: View?) {
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setSurfaceSize()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //取消状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        rv_nav.layoutManager = LinearLayoutManager(this)
        tv_top_name.setOnClickListener {
            ViseLog.d("到门口啊")
            vp_content.currentItem = 0
        }
        ezPlayTop = findViewById(R.id.ez_play_top) as EZUIPlayer
        ezPlayBottom = findViewById(R.id.ez_play_bottom) as EZUIPlayer
        val map = HashMap<String, String>()
        map.put("appKey", "16c4d77101c8406c8a207b0dd339839c")
        map.put("appSecret", "993336fe7aa09017ee9dcd3a10c8c979")
        ViseHttp.POST().baseUrl("https://open.ys7.com/api/lapp/token/get/").addParams(map)
                .request(object : ACallback<YsyBean>() {
                    override fun onSuccess(data: YsyBean) {
                        accessToken = data.data.accessToken
                        play()
                        ViseLog.d("token" + data.data.accessToken)
                    }

                    override fun onFail(errCode: Int, errMsg: String) {

                    }
                })
//        getBuyData(m_store_id)
        setSurfaceSize()
    }

    override fun onPause() {
        super.onPause()
        ezPlayTop.stopPlay()
        ezPlayBottom.stopPlay()

    }

    private fun play() {

        EZUIKit.setAccessToken(accessToken)

        //appkey初始化
        EZUIKit.initWithAppKey(this.application, "16c4d77101c8406c8a207b0dd339839c")

        //设置debug模式，输出log信息
        EZUIKit.setDebug(true)
        //设置播放资源参数
        ezPlayTop.setCallBack(object :EZUIPlayer.EZUIPlayerCallBack {
            override fun onPlayTime(p0: Calendar?) {
            }

            override fun onPrepared() {
                ezPlayTop.startPlay()
            }

            override fun onVideoSizeChange(p0: Int, p1: Int) {
                Log.i("zpj","宽："+p0+"高："+p1)
            }

            override fun onPlayFail(p0: EZUIError?) {
            }

            override fun onPlaySuccess() {
            }

            override fun onPlayFinish() {
                ezPlayTop.stopPlay()
                ezPlayTop.releasePlayer()
            }

        })
        ezPlayTop.setSurfaceSize(600, 0)
//        ezPlayTop.setUrl("ezopen://open.ys7.com/813756259/11.hd.live")
        ezPlayBottom.setSurfaceSize(600, 0)
//        ezPlayBottom.setUrl("ezopen://open.ys7.com/813756259/7.hd.live")
        ezPlayBottom.setCallBack(object :EZUIPlayer.EZUIPlayerCallBack{
            override fun onPlayTime(p0: Calendar?) {
            }

            override fun onPrepared() {
                ezPlayBottom.startPlay()
            }

            override fun onVideoSizeChange(p0: Int, p1: Int) {
            }

            override fun onPlayFail(p0: EZUIError?) {
            }

            override fun onPlaySuccess() {
            }

            override fun onPlayFinish() {
                ezPlayBottom.startPlay()
                ezPlayBottom.releasePlayer()
            }
        })
        ViseLog.d("url_top:" + url_top)


    }

    override fun onStop() {
        ezPlayTop.stopPlay()
        ezPlayTop.pausePlay()
        ezPlayBottom.stopPlay()
        ezPlayBottom.pausePlay()
        super.onStop()
    }

    private fun setSurfaceSize() {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val isWideScrren = configuration.orientation == Configuration.ORIENTATION_PORTRAIT;
        ezPlayTop.setSurfaceSize(dm.widthPixels, 0)
//        val isWideScrren = MyOrientationDetector(this).isWideScrren
//        //竖屏
//        if (isWideScrren) {
//            //竖屏调整播放区域大小，宽全屏，高根据视频分辨率自适应
//
//        } else {
//            //横屏屏调整播放区域大小，宽、高均全屏，播放区域根据视频分辨率自适应
//            ViseLog.d("widthPixels:"+dm.widthPixels+"heightPixels:"+dm.heightPixels)
//            ezPlayTop.setSurfaceSize(dm.widthPixels, dm.heightPixels)
//        }
    }

    override fun onResume() {
        super.onResume()
        getBuyData(m_store_id)
        store_name.text = m_store_name.getName()
        ezPlayTop.setUrl("ezopen://open.ys7.com/813756259/11.hd.live")
        ezPlayBottom.setUrl("ezopen://open.ys7.com/813756259/7.hd.live")

    }


    override fun onDestroy() {
        super.onDestroy()
        ezPlayTop.stopPlay()
        ezPlayTop.releasePlayer()
        ezPlayBottom.stopPlay()
        ezPlayBottom.releasePlayer()
    }


    lateinit var deviceNames: ArrayList<String>
    private fun updateUI(devives: List<EZDeviceInfo>) {
        for (device in devives) {
            deviceNames.add(device.deviceName)
        }

    }

}
