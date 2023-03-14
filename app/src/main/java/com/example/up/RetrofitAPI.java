package com.example.up;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("user/login")
    Call<MaskaUser> createUser(@Body UserModel userModel);
}
