package com.example.administrator.convenientkotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.extensions.ctx
import com.example.administrator.convenientkotlin.extensions.show
import kotlinx.android.synthetic.main.item_goods_list.view.*

/**
 * Created by Administrator on 2017/9/4 0004.
 * 商品adapter
 */
class GoodsAdapter(val data:List<GoodBean>,val itemClick: (GoodBean) -> Unit):RecyclerView.Adapter<GoodsAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(data[position])
    }

    override fun getItemCount(): Int =data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.ctx).inflate(R.layout.item_goods_list,parent,false)
        return ViewHolder(view,itemClick )
    }

    class ViewHolder(view:View ,val itemClick:(GoodBean)->Unit):RecyclerView.ViewHolder(view){
        fun bindView(goodBean: GoodBean){
            with(goodBean){
                itemView?.iv_goods_img?.show(image_default)
                itemView.tv_goods_name.text=name
                itemView?.tv_goods_intro?.text=memo
                itemView?.tv_goods_price?.text=price
                itemView?.setOnClickListener { itemClick(this) }
            }
        }
    }
}