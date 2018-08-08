package com.example.fauzy.crud;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FloorAdapter extends RecyclerView.Adapter<FloorItemHolder> {
    List<Floor> floorList = new ArrayList<>();
    Context c;

    public FloorAdapter(List<Floor> list, Context context){
        this.floorList = list;
        c = context;
    }

    @NonNull
    @Override
    public FloorItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.floor_item_layout,parent,false);
        return new FloorItemHolder(itemView, c);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorItemHolder holder, int position) {
        Floor floor = floorList.get(position);
        holder.floorName.setText(floor.getFloorName());
        holder.idFloor.setText(String.valueOf(floor.getFloorId()));
    }

    @Override
    public int getItemCount() {
        return floorList.size();
    }
}
