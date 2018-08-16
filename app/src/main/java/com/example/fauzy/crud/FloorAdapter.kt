package com.example.fauzy.crud

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import org.json.JSONArray

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FloorAdapter(list: MutableList<Floor>, internal var c: Context) : RecyclerView.Adapter<FloorAdapter.FloorItemHolder>() {
    internal var floorList: MutableList<Floor> = ArrayList()
    internal lateinit var floor: Floor
    internal lateinit var itemHolder: FloorItemHolder

    init {
        this.floorList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.floor_item_layout, parent, false)
        itemHolder = FloorItemHolder(itemView, c)
        return itemHolder
    }

    override fun onBindViewHolder(holder: FloorItemHolder, position: Int) {
        floor = floorList[position]
        holder.floorName.text = floor.floorName
        holder.idFloor.text = floor.floorId.toString()
    }

    override fun getItemCount(): Int {
        return floorList.size
    }

    inner class FloorItemHolder(v: View, internal var c: Context) : RecyclerView.ViewHolder(v) {
        internal var floorName: TextView
        internal var idFloor: TextView
        internal var service: ApiInterface?
        internal var jArray: JSONArray? = null
        internal var edit: ImageView
        internal var delete: ImageView

        init {
            service = ApiClient.client?.create(ApiInterface::class.java)
            idFloor = v.findViewById(R.id.id_floor)
            floorName = v.findViewById(R.id.floor_name)
            edit = v.findViewById(R.id.edit)
            delete = v.findViewById(R.id.delete)
            edit.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(c)
                val dialogView = LayoutInflater.from(c).inflate(R.layout.dialog_layout, null)
                dialogBuilder.setView(dialogView)

                val edt = dialogView.findViewById<View>(R.id.edit1) as EditText

                dialogBuilder.setTitle("Edit Lantai")
                dialogBuilder.setMessage("Masukkan nama lantai")
                dialogBuilder.setPositiveButton("Edit") { dialog, whichButton ->
                    val f = Floor()
                    f.floorId = Integer.valueOf(idFloor.text.toString())
                    f.floorName = edt.text.toString()
                    val request = service?.updateFloor(f)
                    request?.enqueue(object : Callback<ResponseCallback> {
                        override fun onResponse(call: Call<ResponseCallback>, response: Response<ResponseCallback>) {
                            Toast.makeText(c, response.body()!!.responseMessage, Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<ResponseCallback>, t: Throwable) {

                        }
                    })
                    val getData = service?.allFloor
                    getData?.enqueue(object : Callback<Result> {
                        override fun onResponse(call: Call<Result>, response: Response<Result>) {
                            floorList.clear()
                            floorList.addAll(response.body()!!.list)
                            notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<Result>, t: Throwable) {

                        }
                    })
                }
                dialogBuilder.setNegativeButton("Cancel") { dialog, whichButton -> dialog.dismiss() }
                val b = dialogBuilder.create()
                b.show()
            }
            delete.setOnClickListener {
                val f = Floor()
                f.floorId = Integer.valueOf(idFloor.text.toString())
                f.floorName = floorName.text.toString()
                val newList = ArrayList<Floor>()
                newList.add(f)
                val request = service?.deleteFloor(newList)
                request?.enqueue(object : Callback<ResponseCallback> {
                    override fun onResponse(call: Call<ResponseCallback>, response: Response<ResponseCallback>) {
                        Toast.makeText(c, response.body()!!.responseMessage, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<ResponseCallback>, t: Throwable) {

                    }
                })
                val getData = service?.allFloor
                getData?.enqueue(object : Callback<Result> {
                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        floorList.clear()
                        floorList.addAll(response.body()!!.list)
                        notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<Result>, t: Throwable) {

                    }
                })
            }
        }
    }
}
