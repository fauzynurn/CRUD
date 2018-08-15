package com.example.fauzy.crud;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("Result")
    private List<Floor> items;

    public List<Floor> getList(){
        return items;
    }

    public Result(List<Floor> floorList){ this.items = floorList;}
}
