package com.example.administrator.convenientkotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.GoodsDataBean
import com.example.administrator.convenientkotlin.extensions.ctx
import kotlinx.android.synthetic.main.adapter_verify.view.*

/**
 * Created by Administrator on 2017/9/8 0008.
 * 完成验单后的adapter
 */
class VerifyAdapter(val data: List<GoodsDataBean>) : RecyclerView.Adapter<VerifyAdapter.ViewHolder>() {
    override fun getItemCount(): Int =
            data.size


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.ctx).inflate(R.layout.adapter_verify, parent,false)
        return ViewHolder(view)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bindView(goodsDataBean: GoodsDataBean) {
            with(goodsDataBean) {
                itemView.tv_name.text = goods_name
                itemView.tv_num.text = "X" + goods_num
                itemView.tv_price.text = "¥" + goods_price
            }
        }
    }
}
