package com.example.administrator.convenientkotlin.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Administrator on 2017/9/4 0004.
 */
class FrgmentAdapter(fm: FragmentManager,val fragmentList:List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int =fragmentList.size


}