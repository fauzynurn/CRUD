package com.example.fauzy.crud;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    List<Floor> listFloor = new ArrayList<>();
    ApiInterface service;
    FloorAdapter adapter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = ApiClient.getClient().create(ApiInterface.class);
        final RecyclerView recycler = findViewById(R.id.floor_recycler);
        adapter = new FloorAdapter(listFloor, this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        Call<Result> result = service.getAllFloor();
        result.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                adapter.floorList = response.body().getList();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
        Button addFloor = findViewById(R.id.tambah_lantai);
        addFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                final View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_layout, null);
                dialogBuilder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

                dialogBuilder.setTitle("Tambah Lantai");
                dialogBuilder.setMessage("Masukkan nama lantai");
                dialogBuilder.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Floor f = new Floor();
                        f.setFloorName(edt.getText().toString());
                        Call<ResponseData> responseRequest = service.createFloor(f);
                        responseRequest.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                Log.i(TAG, "the size: "+adapter.floorList.size());
                                //Lakukan proses delete semua list pada adapter, lalu get ulang?
                            }

                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {

                            }
                        });
                        Call<Result> getData = service.getAllFloor();
                        getData.enqueue(new Callback<Result>() {
                            @Override
                            public void onResponse(Call<Result> call, Response<Result> response) {
                                listFloor = response.body().getList();
                                adapter.floorList.clear();
                                adapter.floorList.addAll(listFloor);
                                adapter.notifyDataSetChanged();
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
    }
}
