package com.example.administrator.convenientkotlin.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.administrator.convenientkotlin.base.MyApplication


/**
 * 屏幕相关辅助类
 *
 * @author luxf 2015-7-15 下午3:02:24
 */
class ScreenUtils private constructor() {
    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {

        /**
         * 获得屏幕高度
         *
         * @return
         */
        val screenWidth: Int
            get() {
                val wm = MyApplication.instance.getSystemService(
                        Context.WINDOW_SERVICE) as WindowManager
                val outMetrics = DisplayMetrics()
                wm.defaultDisplay.getMetrics(outMetrics)
                return outMetrics.widthPixels
            }

        /**
         * 获得屏幕宽度
         * @return
         */
        val screenHeight: Int
            get() {
                val wm = MyApplication.instance.getSystemService(
                        Context.WINDOW_SERVICE) as WindowManager
                val outMetrics = DisplayMetrics()
                wm.defaultDisplay.getMetrics(outMetrics)
                return outMetrics.heightPixels
            }

        /**
         * 获得状态栏的高度
         * @return
         */
        val statusHeight: Int
            get() {

                var statusHeight = -1
                try {
                    val clazz = Class.forName("com.android.internal.R\$dimen")
                    val `object` = clazz.newInstance()
                    val height = Integer.parseInt(clazz.getField("status_bar_height")
                            .get(`object`).toString())
                    statusHeight = MyApplication.instance.resources
                            .getDimensionPixelSize(height)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return statusHeight
            }

        /**
         * 获取当前屏幕截图，包含状态栏
         *
         * @param activity
         * @return
         */
        fun snapShotWithStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val width = screenWidth
            val height = screenHeight
            var bp: Bitmap? = null
            bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
            view.destroyDrawingCache()
            return bp

        }

        /**
         * 获取当前屏幕截图，不包含状态栏
         *
         * @param activity
         * @return
         */
        fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val frame = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(frame)
            val statusBarHeight = frame.top

            val width = screenWidth
            val height = screenHeight
            var bp: Bitmap? = null
            bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
            view.destroyDrawingCache()
            return bp

        }
    }

}
