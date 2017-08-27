package com.example.administrator.convenientkotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.NavBean
import com.example.administrator.convenientkotlin.domain.model.NavList
import com.example.administrator.convenientkotlin.extensions.ctx
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_nav.view.*

/**
 * Created by Administrator on 2017/8/27 0027.
 */
class NavAdapter(val navList: NavList,val itemClick: (NavBean) -> Unit):RecyclerView.Adapter<NavAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent?.ctx).inflate(R.layout.item_nav,parent,false)
        return ViewHolder(view,itemClick)
    }


    override fun getItemCount(): Int = navList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindNavBean(navList[position])
    }

    class ViewHolder(view :View ,val itemClick :(NavBean)->Unit): RecyclerView.ViewHolder(view) {
        fun bindNavBean(navBean: NavBean){
            with(navBean){
                try {
                    Picasso.with(itemView.ctx).load(icon).into(itemView.nav_img)
                    itemView.setOnClickListener { itemClick(this) }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}