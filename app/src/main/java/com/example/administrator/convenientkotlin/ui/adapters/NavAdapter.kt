package com.example.administrator.convenientkotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.NavBean
import kotlinx.android.synthetic.main.item_nav.view.*

/**
 * Created by Administrator on 2017/9/4 0004.
 * 导航栏适配器
 */
class NavAdapter(val data: List<NavBean>, val itemClick: (NavBean) -> Unit) : RecyclerView.Adapter<NavAdapter.ViewHolder>() {
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindNav(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nav, parent, false)
        view.layoutParams=ViewGroup.LayoutParams(parent.height/4,parent.height/4)
        view.setPadding(parent.width / 8, parent.width / 8, parent.width / 8, parent.width / 8)
        return ViewHolder(view, itemClick)
    }


    class ViewHolder(view: View, val itemClick: (NavBean) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bindNav(nav: NavBean) {
            with(nav) {
                Glide.with(itemView.context)
                        .load(icon)
                        .into(itemView.nav_img)
                itemView.setOnClickListener { itemClick(this) }
            }
        }

    }

}