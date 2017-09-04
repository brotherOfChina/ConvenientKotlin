package com.example.administrator.convenientkotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.extensions.ctx
import com.vise.log.ViseLog
import kotlinx.android.synthetic.main.item_goods_list.view.*

/**
 * Created by Administrator on 2017/9/4 0004.
 * 商品adapter
 */
class GoodsAdapter(val data:List<GoodBean>,val itemClick: (GoodBean) -> Unit):RecyclerView.Adapter<GoodsAdapter.ViewHoder>() {
    override fun onBindViewHolder(holder: ViewHoder?, position: Int) {
        holder?.bindView(data[position])
    }

    override fun getItemCount(): Int =data.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHoder {
        val view=LayoutInflater.from(parent?.ctx).inflate(R.layout.fragment_goods,parent,false)
        return ViewHoder(view,itemClick )

    }

    class ViewHoder(view:View ,val itemClick:(GoodBean)->Unit):RecyclerView.ViewHolder(view){
        fun bindView(goodBean: GoodBean){
            with(goodBean){
                Glide.with(itemView.ctx).load(image_default).into(itemView.iv_goods_img)
                itemView.tv_goods_name.text=name
                itemView.tv_goods_intro.text=memo
                itemView.tv_goods_price.text=price
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}