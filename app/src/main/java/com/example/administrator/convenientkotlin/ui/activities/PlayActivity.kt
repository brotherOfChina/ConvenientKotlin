package com.example.administrator.convenientkotlin.ui.activities

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.OrientationEventListener
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.YsyBean
import com.example.administrator.convenientkotlin.utils.WindowSizeChangeNotifier
import com.ezvizuikit.open.EZUIError
import com.ezvizuikit.open.EZUIKit
import com.ezvizuikit.open.EZUIPlayer
import com.videogo.openapi.bean.EZCameraInfo
import com.vise.log.ViseLog
import com.vise.xsnow.http.ViseHttp
import com.vise.xsnow.http.callback.ACallback
import java.util.*

class PlayActivity : AppCompatActivity(), EZUIPlayer.EZUIPlayerCallBack, View.OnClickListener, WindowSizeChangeNotifier.OnWindowSizeChangedListener {
    private var ezuiPlayer: EZUIPlayer? = null
    private var mBtnPlay: Button? = null
    private var mBtnBack: Button? = null
    private var cameraInfo: EZCameraInfo? = null
    private var url: String? = null

    private var mOrientationDetector: MyOrientationDetector? = null

    private var isResumePlay = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //取消状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_play)
        cameraInfo = intent.getParcelableExtra("cameraNo")
        url = "ezopen://open.ys7.com/" + cameraInfo!!.deviceSerial + "/" + cameraInfo!!.cameraNo + "." + "hd.live"
        ezuiPlayer = findViewById(R.id.play) as EZUIPlayer
        mOrientationDetector = MyOrientationDetector(this)

        mBtnPlay = findViewById(R.id.btn_play) as Button
        mBtnPlay!!.setOnClickListener(this)
        mBtnBack = findViewById(R.id.btn_back) as Button
        mBtnBack!!.setOnClickListener(this)
        val map = HashMap<String, String>()
        map.put("appKey", "16c4d77101c8406c8a207b0dd339839c")
        map.put("appSecret", "993336fe7aa09017ee9dcd3a10c8c979")
        ViseHttp.POST().baseUrl("https://open.ys7.com/api/lapp/token/get/").addParams(map)
                .request(object : ACallback<YsyBean>() {
                    override fun onSuccess(data: YsyBean) {
                        preparePlay(data.data.accessToken)
                        ViseLog.d("token" + data.data.accessToken)
                    }

                    override fun onFail(errCode: Int, errMsg: String) {

                    }
                })

//                setSurfaceSize();
    }

    private fun setSurfaceSize() {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val isWideScrren = mOrientationDetector!!.isWideScrren
        //竖屏
        if (!isWideScrren) {
            //竖屏调整播放区域大小，宽全屏，高根据视频分辨率自适应
            ezuiPlayer!!.setSurfaceSize(dm.widthPixels, 0)
        } else {
            //横屏屏调整播放区域大小，宽、高均全屏，播放区域根据视频分辨率自适应
            ezuiPlayer!!.setSurfaceSize(300, 0)
        }
    }

    override fun onPause() {
        super.onPause()
        mOrientationDetector!!.disable()
    }

    override fun onResume() {
        super.onResume()
        mOrientationDetector!!.enable()
        //界面stop时，如果在播放，那isResumePlay标志位置为true，resume时恢复播放
        if (isResumePlay) {
            isResumePlay = false
            mBtnPlay!!.text = "停止"
            ezuiPlayer!!.startPlay()
        }
    }

    override fun onStop() {
        super.onStop()
        //界面stop时，如果在播放，那isResumePlay标志位置为true，以便resume时恢复播放
        if (ezuiPlayer!!.status != EZUIPlayer.STATUS_STOP) {
            isResumePlay = true
        }
        //停止播放
        ezuiPlayer!!.stopPlay()
    }

    /**
     * 准备播放资源参数
     * @param accessToken
     */
    private fun preparePlay(accessToken: String) {
        //设置授权accesstoken
        EZUIKit.setAccessToken(accessToken)

        //appkey初始化
        EZUIKit.initWithAppKey(this.application, "16c4d77101c8406c8a207b0dd339839c")

        //设置debug模式，输出log信息
        EZUIKit.setDebug(true)
        ezuiPlayer!!.setSurfaceSize(1920, 0)
        //设置播放资源参数
        ezuiPlayer!!.setCallBack(this)
        ezuiPlayer!!.setUrl(url)
    }

    override fun onPlaySuccess() {
        mBtnPlay!!.text = "暂停"

    }

    override fun onPlayFail(error: EZUIError) {
        if (error.errorString == EZUIError.UE_ERROR_INNER_VERIFYCODE_ERROR) {

        } else if (error.errorString.equals(EZUIError.UE_ERROR_NOT_FOUND_RECORD_FILES, ignoreCase = true)) {
            // TODO: 2017/5/12
            //未发现录像文件
            Toast.makeText(this, "未找到录像文件", Toast.LENGTH_LONG).show()
        }
    }

    override fun onVideoSizeChange(i: Int, i1: Int) {
        ViseLog.i("width" + i + "height" + i1)
    }

    override fun onPrepared() {
        ezuiPlayer!!.startPlay()
    }

    override fun onPlayTime(calendar: Calendar) {

    }

    override fun onPlayFinish() {
        ezuiPlayer!!.releasePlayer()

    }

    override fun onDestroy() {
        ezuiPlayer!!.stopPlay()
        ezuiPlayer!!.releasePlayer()
        super.onDestroy()

    }


    override fun onClick(view: View) {
        if (view === mBtnPlay) {
            // TODO: 2017/2/14
            if (ezuiPlayer!!.status == EZUIPlayer.STATUS_PLAY) {

                //播放状态，点击停止播放
                mBtnPlay!!.text = "播放"
                ezuiPlayer!!.stopPlay()
            } else if (ezuiPlayer!!.status == EZUIPlayer.STATUS_STOP) {
                //停止状态，点击播放
                mBtnPlay!!.text = "停止"
                ezuiPlayer!!.startPlay()
            }
        } else if (view === mBtnBack) {
            ezuiPlayer!!.stopPlay()
            finish()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //        setSurfaceSize();

    }

    override fun onWindowSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        if (ezuiPlayer != null) {
            //            setSurfaceSize();
        }
    }

    inner class MyOrientationDetector(context: Context) : OrientationEventListener(context) {

        private val mWindowManager: WindowManager
        private var mLastOrientation = 0

        val isWideScrren: Boolean
            get() {
                val display = mWindowManager.defaultDisplay
                val pt = Point()
//                display.getSize(pt)
                return pt.x > pt.y
            }

        init {
            mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }

        override fun onOrientationChanged(orientation: Int) {
            val value = getCurentOrientationEx(orientation)
            if (value != mLastOrientation) {
                mLastOrientation = value
                val current = requestedOrientation
                if (current == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || current == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
                }
            }
        }

        private fun getCurentOrientationEx(orientation: Int): Int {
            var value = 0
            if (orientation >= 315 || orientation < 45) {
                // 0度
                value = 0
                return value
            }
            if (orientation >= 45 && orientation < 135) {
                // 90度
                value = 90
                return value
            }
            if (orientation >= 135 && orientation < 225) {
                // 180度
                value = 180
                return value
            }
            if (orientation >= 225 && orientation < 315) {
                // 270度
                value = 270
                return value
            }
            return value
        }
    }
}
