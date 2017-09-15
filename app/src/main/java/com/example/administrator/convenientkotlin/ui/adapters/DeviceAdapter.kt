package com.example.administrator.convenientkotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.extensions.show
import com.videogo.openapi.bean.EZDeviceInfo
import kotlinx.android.synthetic.main.adapter_device.view.*

/**
 * Created by Administrator on 2017/9/12 0012.
 * 设备适配器
 */
class DeviceAdapter(val devices: List<EZDeviceInfo>, val itemClick:(EZDeviceInfo)->Unit):RecyclerView.Adapter<DeviceAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeviceAdapter.ViewHolder {
        val view=LayoutInflater.from(parent?.context).inflate(R.layout.adapter_device,parent,false)
        return ViewHolder(view,itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(devices[position])
    }

    override fun getItemCount(): Int =devices.size

    class ViewHolder(view:View,val itemClick:(EZDeviceInfo)->Unit):RecyclerView.ViewHolder(view){
        fun bindView(device: EZDeviceInfo){
            with(device){
                itemView.iv_device.show(device.deviceCover)
                itemView.tv_device_name.text=deviceName
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }
}