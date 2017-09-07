package com.example.administrator.convenientkotlin.extensions

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import java.util.*

/**
 * Created by Administrator on 2017/9/5 0005.
 * string 的扩展
 */
fun String.getQRCode( format: BarcodeFormat=BarcodeFormat.QR_CODE):Bitmap{
    val write=MultiFormatWriter()
    val hst=Hashtable<EncodeHintType,Any>()
    hst.put(EncodeHintType.CHARACTER_SET, "UTF-8")//编码
    hst.put(EncodeHintType.MARGIN, 1) //二维码边框宽度，这里文档说设置0-4
    val matrix=write.encode(this,format,400,400,hst)
    val width = matrix.width
    val height = matrix.height
    val pixels = IntArray(width * height)
    for (y in 0 until height) {
        (0 until width)//返回一个从这个值,但不包括指定的()值
                .filter { matrix.get(it, y) }//返回一个列表只包含元素匹配给定的
                .forEach { pixels[y * width + it] = 0xff000000.toInt() }//对每个元素执行给定的[行动];
    }
    val bitmap = Bitmap.createBitmap(width, height,
            Bitmap.Config.ARGB_4444)
    // 通过像素数组生成bitmap,具体参考api
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    return bitmap
}
