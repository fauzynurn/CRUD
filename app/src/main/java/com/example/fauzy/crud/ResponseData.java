package com.example.fauzy.crud;

import com.google.gson.annotations.SerializedName;

public class ResponseData {

    @SerializedName("ResponseCode")
    public String responseCode;

    @SerializedName("ResponseMessage")
    public String responseMessage;

    @SerializedName("ResponseData")
    public String responseData;
}
