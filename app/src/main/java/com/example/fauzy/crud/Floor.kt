package com.example.fauzy.crud

import com.google.gson.annotations.SerializedName

class Floor {
    @SerializedName("IdFloor")
    var floorId: Int = 0

    @SerializedName("FloorName")
    var floorName: String? = null
}
