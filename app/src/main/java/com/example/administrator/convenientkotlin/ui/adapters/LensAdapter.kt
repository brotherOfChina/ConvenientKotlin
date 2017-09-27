package com.example.administrator.convenientkotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.extensions.show
import com.videogo.openapi.EZOpenSDK
import com.videogo.openapi.bean.EZCameraInfo
import com.vise.log.ViseLog
import kotlinx.android.synthetic.main.adapter_device.view.*
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by Administrator on 2017/9/12 0012.
 * 设备适配器
 */
class LensAdapter(val devices: List<EZCameraInfo>, val itemClick:(EZCameraInfo)->Unit): RecyclerView.Adapter<LensAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LensAdapter.ViewHolder {
        val view= LayoutInflater.from(parent?.context).inflate(R.layout.adapter_device,parent,false)
        return ViewHolder(view,itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(devices[position])
    }

    override fun getItemCount(): Int =devices.size

    class ViewHolder(view: View, val itemClick:(EZCameraInfo)->Unit): RecyclerView.ViewHolder(view){
        fun bindView(device: EZCameraInfo){
            with(device){
                var camera=   bg { EZOpenSDK.getInstance().captureCamera(deviceSerial,cameraNo)

                }
                 ViseLog.i(camera.toString())
//                if (camera==null){
//                    camera=device.cameraCover
//                }
                itemView.iv_device.show(device.cameraCover)
                itemView.tv_device_name.text=cameraName
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }
}