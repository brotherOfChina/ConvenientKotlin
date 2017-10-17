package com.example.administrator.convenientkotlin.domain.commands

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 *
 *
 * 对请求结果返回对象的封装，包含[.status]， [.msg]
 * 和[.response]
 */
class HttpResult : Serializable {
    //返回状态  0成功  1失败
    var status: Int = 0
    var msg: String? = null
    @SerializedName("data")
    var response: String? = null
}

class HttpErrorException : RuntimeException {
    var status: Int = 0
    var msg: String? = null

    constructor() : super() {}

    constructor(status: Int, msg: String) : super(msg) {
        this.status = status
        this.msg = msg
    }
}
