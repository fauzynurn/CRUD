package com.example.fauzy.crud;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static String url_read = "http://10.10.5.15:38001/api/fam/Floor";
    private static String url_create = "http://10.10.5.15:38001/api/fam/Floor/CreateFloor";
    List<Floor> listFloor = new ArrayList<>();
    CRUDService service;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = new CRUDService(this);
        FloorAdapter adapter = new FloorAdapter(listFloor, this,service);
        service.floorAdapter = adapter;
        Button addFloor = findViewById(R.id.tambah_lantai);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pull_to_refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                service.floorAdapter.floorList.clear();
                service.getData(listFloor);
                service.floorAdapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            }
        });
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
                        try {
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("FloorName", edt.getText().toString());
                            final String requestBody = jsonBody.toString();
                            service.postData(requestBody);
                            service.floorAdapter.notifyItemInserted(listFloor.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        RecyclerView recycler = findViewById(R.id.floor_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(service.floorAdapter);
        service.getData(listFloor);
    }
}
