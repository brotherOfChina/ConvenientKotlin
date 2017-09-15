package com.example.administrator.convenientkotlin.ui.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.convenientkotlin.R;
import com.example.administrator.convenientkotlin.domain.model.YsyBean;
import com.example.administrator.convenientkotlin.utils.WindowSizeChangeNotifier;
import com.ezvizuikit.open.EZUIError;
import com.ezvizuikit.open.EZUIKit;
import com.ezvizuikit.open.EZUIPlayer;
import com.videogo.openapi.bean.EZCameraInfo;
import com.vise.log.ViseLog;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import java.util.Calendar;
import java.util.HashMap;

public class PlayActivity extends AppCompatActivity implements EZUIPlayer.EZUIPlayerCallBack, View.OnClickListener, WindowSizeChangeNotifier.OnWindowSizeChangedListener {
    private EZUIPlayer ezuiPlayer;
    private Button mBtnPlay;
    private Button mBtnBack;
    private EZCameraInfo cameraInfo;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);
        cameraInfo = getIntent().getParcelableExtra("cameraNo");
        url = "ezopen://open.ys7.com/" + cameraInfo.getDeviceSerial() + "/" + cameraInfo.getCameraNo() + "." + "hd.live";
        ezuiPlayer = findViewById(R.id.play);
        mOrientationDetector = new MyOrientationDetector(this);

        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPlay.setOnClickListener(this);
        mBtnBack = (Button) findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);
        HashMap<String, String> map = new HashMap<>();
        map.put("appKey", "16c4d77101c8406c8a207b0dd339839c");
        map.put("appSecret", "993336fe7aa09017ee9dcd3a10c8c979");
        ViseHttp.POST().baseUrl("https://open.ys7.com/api/lapp/token/get/").addParams(map)
                .request(new ACallback<YsyBean>() {
                    @Override
                    public void onSuccess(YsyBean data) {
                        preparePlay(data.getData().getAccessToken());

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        setSurfaceSize();
    }

    private MyOrientationDetector mOrientationDetector;

    private void setSurfaceSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        boolean isWideScrren = mOrientationDetector.isWideScrren();
        //竖屏
        if (!isWideScrren) {
            //竖屏调整播放区域大小，宽全屏，高根据视频分辨率自适应
            ezuiPlayer.setSurfaceSize(dm.widthPixels, 0);
        } else {
            //横屏屏调整播放区域大小，宽、高均全屏，播放区域根据视频分辨率自适应
            ezuiPlayer.setSurfaceSize(dm.widthPixels, dm.heightPixels);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOrientationDetector.disable();
    }

    private boolean isResumePlay = false;

    @Override
    protected void onResume() {
        super.onResume();
        mOrientationDetector.enable();
        //界面stop时，如果在播放，那isResumePlay标志位置为true，resume时恢复播放
        if (isResumePlay) {
            isResumePlay = false;
            mBtnPlay.setText("停止");
            ezuiPlayer.startPlay();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //界面stop时，如果在播放，那isResumePlay标志位置为true，以便resume时恢复播放
        if (ezuiPlayer.getStatus() != EZUIPlayer.STATUS_STOP) {
            isResumePlay = true;
        }
        //停止播放
        ezuiPlayer.stopPlay();
    }

    /**
     * 准备播放资源参数
     * @param accessToken
     */
    private void preparePlay(String accessToken) {
        //设置授权accesstoken
        EZUIKit.setAccessToken(accessToken);
        //appkey初始化
        EZUIKit.initWithAppKey(this.getApplication(), "16c4d77101c8406c8a207b0dd339839c");

        //设置debug模式，输出log信息
        EZUIKit.setDebug(true);
        //设置播放资源参数
        ezuiPlayer.setCallBack(this);
        ViseLog.i(url);
        ezuiPlayer.setUrl(url);
    }

    @Override
    public void onPlaySuccess() {
        mBtnPlay.setText("暂停");

    }

    @Override
    public void onPlayFail(EZUIError error) {
        if (error.getErrorString().equals(EZUIError.UE_ERROR_INNER_VERIFYCODE_ERROR)) {

        } else if (error.getErrorString().equalsIgnoreCase(EZUIError.UE_ERROR_NOT_FOUND_RECORD_FILES)) {
            // TODO: 2017/5/12
            //未发现录像文件
            Toast.makeText(this, "未找到录像文件", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onVideoSizeChange(int i, int i1) {
        ViseLog.i("width"+i+"height"+i1);
    }

    @Override
    public void onPrepared() {
        ezuiPlayer.startPlay();
    }

    @Override
    public void onPlayTime(Calendar calendar) {

    }

    @Override
    public void onPlayFinish() {
        ezuiPlayer.releasePlayer();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ezuiPlayer.releasePlayer();
    }


    @Override
    public void onClick(View view) {
        if (view == mBtnPlay) {
            // TODO: 2017/2/14
            if (ezuiPlayer.getStatus() == EZUIPlayer.STATUS_PLAY) {

                //播放状态，点击停止播放
                mBtnPlay.setText("播放");
                ezuiPlayer.stopPlay();
            } else if (ezuiPlayer.getStatus() == EZUIPlayer.STATUS_STOP) {
                //停止状态，点击播放
                mBtnPlay.setText("停止");
                ezuiPlayer.startPlay();
            }
        } else if (view == mBtnBack) {
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setSurfaceSize();

    }

    @Override
    public void onWindowSizeChanged(int w, int h, int oldW, int oldH) {
        if (ezuiPlayer != null) {
            setSurfaceSize();
        }
    }

    public class MyOrientationDetector extends OrientationEventListener {

        private WindowManager mWindowManager;
        private int mLastOrientation = 0;

        public MyOrientationDetector(Context context) {
            super(context);
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }

        public boolean isWideScrren() {
            Display display = mWindowManager.getDefaultDisplay();
            Point pt = new Point();
            display.getSize(pt);
            return pt.x > pt.y;
        }

        @Override
        public void onOrientationChanged(int orientation) {
            int value = getCurentOrientationEx(orientation);
            if (value != mLastOrientation) {
                mLastOrientation = value;
                int current = getRequestedOrientation();
                if (current == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        || current == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
        }

        private int getCurentOrientationEx(int orientation) {
            int value = 0;
            if (orientation >= 315 || orientation < 45) {
                // 0度
                value = 0;
                return value;
            }
            if (orientation >= 45 && orientation < 135) {
                // 90度
                value = 90;
                return value;
            }
            if (orientation >= 135 && orientation < 225) {
                // 180度
                value = 180;
                return value;
            }
            if (orientation >= 225 && orientation < 315) {
                // 270度
                value = 270;
                return value;
            }
            return value;
        }
    }
}
