package com.cos.phoneapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PhoneService {

    @GET("phone")
    Call<CommonRespDto<List<Phone>>> findAll();

    @GET("phone/{id}")
    Call<CommonRespDto<Phone>> findById(@Path("id") Long id);

    @PUT("phone/{id}")
    Call<CommonRespDto<Phone>> update(@Path("id") Long id, @Body Phone phone);

    @POST("phone")
    Call<CommonRespDto<Phone>> save(@Body Phone phone);

    @DELETE("phone/{id}")
    Call<CommonRespDto<Void>> delete(@Path("id") Long id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://172.30.1.60:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
