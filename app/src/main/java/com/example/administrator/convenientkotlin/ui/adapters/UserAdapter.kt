package com.example.administrator.convenientkotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.User
import kotlinx.android.synthetic.main.adapter_content.view.*

/**
 * Created by Administrator on 2017/9/20 0020.
 * 店铺adapter
 */
class UserAdapter(val data:List<User>, val itemClick: (User) -> Unit) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent?.context).inflate(R.layout.adapter_content,parent,false)
        return ViewHolder(view,itemClick)
    }

    override fun getItemCount(): Int =
       data.size


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(data[position])
    }

    class ViewHolder(view: View, val itemClick:(User)->Unit) : RecyclerView.ViewHolder(view) {
        fun bindView(store: User){
            with(store){
                itemView.tv_content.text=truename
                itemView.setOnClickListener{itemClick(this)}
            }
        }
    }
}