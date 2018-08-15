package com.example.fauzy.crud;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.FloorItemHolder> {
    List<Floor> floorList = new ArrayList<>();
    Context c;
    Floor floor;
    FloorItemHolder itemHolder;

    public FloorAdapter(List<Floor> list, Context context) {
        this.floorList = list;
        c = context;
    }

    @NonNull
    @Override
    public FloorItemHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.floor_item_layout, parent, false);
        itemHolder = new FloorItemHolder(itemView, c);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FloorItemHolder holder, int position) {
        floor = floorList.get(position);
        holder.floorName.setText(floor.getFloorName());
        holder.idFloor.setText(String.valueOf(floor.getFloorId()));
    }

    @Override
    public int getItemCount() {
        return floorList.size();
    }

    public class FloorItemHolder extends RecyclerView.ViewHolder {
        TextView floorName;
        TextView idFloor;
        Context c;
        ApiInterface service;
        JSONArray jArray;
        ImageView edit, delete;

        public FloorItemHolder(View v, Context context) {
            super(v);
            c = context;
            service = ApiClient.getClient().create(ApiInterface.class);
            idFloor = v.findViewById(R.id.id_floor);
            floorName = v.findViewById(R.id.floor_name);
            edit = v.findViewById(R.id.edit);
            delete = v.findViewById(R.id.delete);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(c);
                    final View dialogView = LayoutInflater.from(c).inflate(R.layout.dialog_layout, null);
                    dialogBuilder.setView(dialogView);

                    final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

                    dialogBuilder.setTitle("Edit Lantai");
                    dialogBuilder.setMessage("Masukkan nama lantai");
                    dialogBuilder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Floor f = new Floor();
                            f.setFloorId(Integer.valueOf(idFloor.getText().toString()));
                            f.setFloorName(edt.getText().toString());
                            Call<ResponseData> request = service.updateFloor(f);
                            request.enqueue(new Callback<ResponseData>() {
                                @Override
                                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                    Toast.makeText(c, response.body().responseMessage, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseData> call, Throwable t) {

                                }
                            });
                            Call<Result> getData = service.getAllFloor();
                            getData.enqueue(new Callback<Result>() {
                                @Override
                                public void onResponse(Call<Result> call, Response<Result> response) {
                                    floorList.clear();
                                    floorList.addAll(response.body().getList());
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<Result> call, Throwable t) {

                                }
                            });
                        }
                    });
                    dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog b = dialogBuilder.create();
                    b.show();
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Floor f = new Floor();
                    f.setFloorId(Integer.valueOf(idFloor.getText().toString()));
                    f.setFloorName(floorName.getText().toString());
                    List<Floor> newList = new ArrayList<>();
                    newList.add(f);
                    Call<ResponseData> request = service.deleteFloor(newList);
                    request.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            Toast.makeText(c, response.body().responseMessage, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {

                        }
                    });
                    Call<Result> getData = service.getAllFloor();
                    getData.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            floorList.clear();
                            floorList.addAll(response.body().getList());
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });
                }
            });
        }
    }
}
