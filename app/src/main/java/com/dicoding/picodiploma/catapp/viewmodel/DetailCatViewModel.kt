package com.dicoding.picodiploma.catapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.catapp.model.api.ApiBase
import com.dicoding.picodiploma.catapp.model.data.CatDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCatViewModel : ViewModel() {
    val catDetail = MutableLiveData<CatDetailResponse>()

    fun getCatDetail() : LiveData<CatDetailResponse>{
        return catDetail
    }

    fun setCatDetail(name : String){
        ApiBase.apiInterface.detailCat(name).enqueue(object : Callback<CatDetailResponse>{
            override fun onResponse(call: Call<CatDetailResponse>, response: Response<CatDetailResponse>) {
                if (response.isSuccessful){
                    catDetail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<CatDetailResponse>, t: Throwable) {
                Log.d("Failure...", t.message.toString())
            }

        })
    }
}