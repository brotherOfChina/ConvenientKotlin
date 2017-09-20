package com.example.administrator.convenientkotlin.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import android.view.WindowManager
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.base.MyApplication
import com.example.administrator.convenientkotlin.domain.model.ResponseNavBean
import com.example.administrator.convenientkotlin.domain.model.Store
import com.example.administrator.convenientkotlin.domain.model.User
import com.example.administrator.convenientkotlin.extensions.DelegatesExt
import com.example.administrator.convenientkotlin.ui.adapters.StoreAdapter
import com.example.administrator.convenientkotlin.ui.adapters.UserAdapter
import com.example.administrator.convenientkotlin.utils.SignUtil
import com.vise.xsnow.http.ViseHttp
import com.vise.xsnow.http.callback.ACallback
import kotlinx.android.synthetic.main.activity_user.*
import org.jetbrains.anko.toast

class UserActivity : AppCompatActivity() {
    var m_user_id :String by DelegatesExt.preference(MyApplication.instance,UserActivity.USER_ID, D_USER_ID)
    var m_store_id :String by DelegatesExt.preference(MyApplication.instance,UserActivity.STORE_ID,D_STORE_ID)
    var m_store_name :String by DelegatesExt.preference(MyApplication.instance,UserActivity.STORE_NAME,D_STORE_NAME)
    var m_user_name :String by DelegatesExt.preference(MyApplication.instance,UserActivity.USER_NAME,D_USER_NAME)
    companion object {
        var USER_ID ="user_id"
        var D_USER_ID ="64259"
        var STORE_ID :String= "store_id"
        var D_STORE_ID :String= "1"
        var STORE_NAME :String= "store_name"
        var D_STORE_NAME :String= "一家便利（华都店）"
        var USER_NAME:String= "user_name"
        var D_USER_NAME:String= "李鑫宇"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //取消状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_user)
        rv_store.layoutManager = LinearLayoutManager(this)
        rv_user.layoutManager = LinearLayoutManager(this)
        getStore()
    }

    /**
     * 获取店铺
     * 'v' => 'CV1',
    'm' => 'Screen',
    'c' => 'StoreList',
     */
    private fun getStore() {
        val map = mutableMapOf<String, String>()
        map.put("v", "CV1")
        map.put("m", "Screen")
        map.put("c", "StoreList")
        map.put("sign", SignUtil.getSignString(map))
        ViseHttp.POST().addParams(map).request(object : ACallback<ResponseNavBean<Store>>() {
            override fun onFail(errCode: Int, errMsg: String?) {
                toast(errMsg + "")
            }

            override fun onSuccess(data: ResponseNavBean<Store>) {
                val adapter: StoreAdapter by lazy {
                    StoreAdapter(data.data) {
                        with(it){
                            m_store_id=store_id
                            m_store_name=store_name
                        }
                        getUser(it.store_id,it.store_name)
                    }
                }
                rv_store.adapter = adapter
            }
        })
    }

    /**
     * 'v' => 'CV1',
    'm' => 'Screen',
    'c' => 'UserList',
    'store_id'=>'1',
     */
    private fun getUser(store_id: String, store_name: String) {
        val map = mutableMapOf<String, String>()
        map.put("v", "CV1")
        map.put("m", "Screen")
        map.put("c", "UserList")
        map.put("store_id", store_id)
        map.put("sign", SignUtil.getSignString(map))
        ViseHttp.POST().addParams(map).request(object : ACallback<ResponseNavBean<User>>() {
            override fun onFail(errCode: Int, errMsg: String?) {
                toast(errMsg + "")
            }

            override fun onSuccess(data: ResponseNavBean<User>) {
                val adapter: UserAdapter by lazy {
                    UserAdapter(data.data) {
                        m_user_id=it.userid
                        m_user_name=it.truename
                        finish()
                    }
                }
                rv_user.adapter = adapter
            }
        })
    }

    private fun initView() {

    }
}
