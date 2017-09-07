package com.example.administrator.convenientkotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.TypeBean
import com.example.administrator.convenientkotlin.extensions.ctx
import com.example.administrator.convenientkotlin.extensions.show
import kotlinx.android.synthetic.main.item_store_nav.view.*

/**
 * Created by Administrator on 2017/9/5 0005.
 * 类别adapter
 *
 */
class TypeAdapter (val data:List<TypeBean>,val itemClick: (TypeBean) -> Unit):RecyclerView.Adapter<TypeAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view=LayoutInflater.from(parent.ctx).inflate(R.layout.item_store_nav,parent,false)
        view.layoutParams=ViewGroup.LayoutParams(parent.width/5,parent.height/2)
        return ViewHolder(view,itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(data[position])
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View ,val itemClick: (TypeBean)->Unit):RecyclerView.ViewHolder(view){
        fun bindView(typeBean: TypeBean){
            with(typeBean){
                itemView.nav_img.show(icon)
                itemView.setOnClickListener {itemClick(this)  }
            }
        }
    }
}