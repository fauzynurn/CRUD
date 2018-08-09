package com.example.fauzy.crud;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDService {
    private static String url_read =  "http://10.10.5.15:38001/api/fam/Floor";
    private static String url_create =  "http://10.10.5.15:38001/api/fam/Floor/CreateFloor";
    private static String url_edit = "http://10.10.5.15:38001/api/fam/Floor/UpdateFloor";
    private static String url_delete = "http://10.10.5.15:38001/api/fam/Floor/DeleteFloor";
    FloorAdapter floorAdapter;
    RequestQueue mRequestQueue;
    private static final String TAG = "CRUDService";
    Context c;

    public CRUDService(Context context){
        c = context;
        mRequestQueue = Volley.newRequestQueue(c);
    }

    public void getData(final List<Floor> list){
        JsonObjectRequest jArr = new JsonObjectRequest(Request.Method.GET,url_read,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray obj = response.getJSONArray("Result");
                    for (int i = 0; i < obj.length(); i++) {
                        Floor floor = new Floor();
                        floor.setFloorId(obj.getJSONObject(i).getInt("IdFloor"));
                        floor.setFloorName(obj.getJSONObject(i).getString("FloorName"));
                        list.add(floor);
                        floorAdapter.notifyItemInserted(i);
                    }
                    Log.i(TAG, "jumlah elemen list: "+list.size());
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error: " + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("idinternal", "027");
                return params;
            }
        };
        mRequestQueue.add(jArr);
    }

    public void postData(final String requestBody){
        RequestQueue requestQueue = Volley.newRequestQueue(c);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_create, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("idinternal", "027");
                return params;
            }
        };
        mRequestQueue.add(stringRequest);
    }

    public void putData(final String requestBody){
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url_edit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("idinternal", "027");
                return params;
            }
        };
        mRequestQueue.add(stringRequest);
    }

    public void deleteData(final String requestBody){
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url_delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("idinternal", "027");
                return params;
            }
        };
        mRequestQueue.add(stringRequest);
    }
}
