package com.example.fauzy.crud;

import com.google.gson.annotations.SerializedName;

public class Floor {
    @SerializedName("IdFloor")
    private int floorId;

    @SerializedName("FloorName")
    private String floorName;

    public int getFloorId() {
        return floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }
}
