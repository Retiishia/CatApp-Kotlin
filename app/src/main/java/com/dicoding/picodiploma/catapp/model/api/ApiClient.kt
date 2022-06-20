package com.dicoding.picodiploma.catapp.model.api

import com.dicoding.picodiploma.catapp.model.data.CatDetailResponse
import com.dicoding.picodiploma.catapp.model.data.CatImageResponse
import com.dicoding.picodiploma.catapp.model.data.CatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("v1/breeds/search")
    @Headers("x-api-key: d64f0810-5b68-4e9f-b18e-bc1dea001957")
    fun searchCat(@Query("q")query : String) : Call<ArrayList<CatResponse>>

    @GET("v1/images/{images_id}")
    @Headers("x-api-key: d64f0810-5b68-4e9f-b18e-bc1dea001957")
    fun imageCat(@Path("images_id")imageId: String) : Call<CatImageResponse>

    @GET("v1/breeds/{id}")
    @Headers("x-api-key: d64f0810-5b68-4e9f-b18e-bc1dea001957")
    fun detailCat(@Path("id")id : String) : Call<CatDetailResponse>
}