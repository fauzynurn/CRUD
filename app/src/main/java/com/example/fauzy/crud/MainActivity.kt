package com.example.fauzy.crud

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    internal var listFloor: MutableList<Floor> = ArrayList()
    internal var service: ApiInterface? = ApiClient.client?.create(ApiInterface::class.java)
    internal var adapter: FloorAdapter = FloorAdapter(listFloor, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recycler = findViewById<RecyclerView>(R.id.floor_recycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
        val result = service?.allFloor
        result?.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                adapter.floorList = response.body()!!.list
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {

            }
        })
        val addFloor = findViewById<Button>(R.id.tambah_lantai)
        addFloor.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this@MainActivity)
            val dialogView = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_layout, null)
            dialogBuilder.setView(dialogView)

            val edt = dialogView.findViewById<View>(R.id.edit1) as EditText

            dialogBuilder.setTitle("Tambah Lantai")
            dialogBuilder.setMessage("Masukkan nama lantai")
            dialogBuilder.setPositiveButton("Tambah") { dialog, whichButton ->
                val f = Floor()
                f.floorName = edt.text.toString()
                val responseRequest = service?.createFloor(f)
                responseRequest?.enqueue(object : Callback<ResponseCallback> {
                    override fun onResponse(call: Call<ResponseCallback>, response: Response<ResponseCallback>) {
                        Log.i(TAG, "the size: " + adapter.floorList.size)
                    }

                    override fun onFailure(call: Call<ResponseCallback>, t: Throwable) {

                    }
                })
                val getData = service?.allFloor
                getData?.enqueue(object : Callback<Result> {
                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        listFloor = response.body()!!.list
                        adapter.floorList.clear()
                        adapter.floorList.addAll(listFloor)
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<Result>, t: Throwable) {

                    }
                })
            }
            dialogBuilder.setNegativeButton("Cancel") { dialog, whichButton -> dialog.dismiss() }
            val b = dialogBuilder.create()
            b.show()
        }
    }

    companion object {
        private val TAG = "MainActivity"
    }
}
