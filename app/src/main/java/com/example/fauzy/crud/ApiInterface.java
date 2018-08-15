package com.example.fauzy.crud;

import org.json.JSONArray;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @Headers({"Content-Type: application/json","idinternal: 027"})
    @GET("Floor")
    Call<Result> getAllFloor();

    @GET("Floor/{id}")
    Call<Floor> getFloorById(@Path("id") int id);

    @Headers({"Content-Type: application/json","idinternal: 027"})
    @POST("Floor/CreateFloor")
    Call<ResponseData> createFloor(@Body Floor floor);

    @Headers({"Content-Type: application/json","idinternal: 027"})
    @PUT("Floor/UpdateFloor")
    Call<ResponseData> updateFloor(@Body Floor floor);

    @Headers({"Content-Type: application/json","idinternal: 027"})
    @PUT("Floor/DeleteFloor")
    Call<ResponseData> deleteFloor(@Body List<Floor> listFloor);
}
