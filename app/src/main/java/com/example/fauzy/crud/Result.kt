package com.example.fauzy.crud

import com.google.gson.annotations.SerializedName

//Hanya berisi atribut MutableList untuk menampung data-data lantai
class Result(@field:SerializedName("Result")
             val list: MutableList<Floor>)
