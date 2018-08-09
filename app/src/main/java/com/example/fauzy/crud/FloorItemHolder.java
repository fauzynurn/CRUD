package com.example.fauzy.crud;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class FloorItemHolder extends RecyclerView.ViewHolder {
    TextView floorName;
    TextView idFloor;
    Context c;
    ImageView edit, delete;

    public FloorItemHolder(View v, Context context, final CRUDService crudService) {
        super(v);
        c = context;
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
                        try {
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("FloorName", edt.getText().toString());
                            jsonBody.put("IdFloor", idFloor.getText().toString());
                            final String requestBody = jsonBody.toString();
                            crudService.putData(requestBody);
                            crudService.floorAdapter.notifyDataSetChanged();
                            dialog.dismiss();
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("IdFloor", idFloor.getText().toString());
                    jsonBody.put("FloorName",floorName.getText().toString());
                    final String requestBody = "["+jsonBody.toString()+"]";
                    crudService.deleteData(requestBody);
                    Toast.makeText(c, requestBody, Toast.LENGTH_SHORT).show();
                    crudService.floorAdapter.notifyItemRemoved(crudService.floorAdapter.floorList.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
