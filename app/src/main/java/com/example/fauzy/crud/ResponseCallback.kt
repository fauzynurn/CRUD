package com.example.fauzy.crud

import com.google.gson.annotations.SerializedName

class ResponseCallback {

    @SerializedName("ResponseCode")
    var responseCode: String? = null

    @SerializedName("ResponseMessage")
    var responseMessage: String? = null

    @SerializedName("ResponseCallback")
    var responseData: String? = null
}
