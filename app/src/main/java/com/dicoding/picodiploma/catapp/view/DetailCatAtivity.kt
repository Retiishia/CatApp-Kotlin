package com.dicoding.picodiploma.catapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.catapp.R
import com.dicoding.picodiploma.catapp.databinding.ActivityDetailCatAtivityBinding
import com.dicoding.picodiploma.catapp.model.api.ApiBase
import com.dicoding.picodiploma.catapp.model.data.CatImageResponse
import com.dicoding.picodiploma.catapp.viewmodel.DetailCatViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCatAtivity : AppCompatActivity() {
    private lateinit var catDetailBinding : ActivityDetailCatAtivityBinding
    private lateinit var detailVM : DetailCatViewModel
    lateinit var sharedPreferences: SharedPreferences
    val PREFERENCES_NAME = "cat_app_preferences"
    val KEY_NAME = "key_name"
    private var clickedValue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catDetailBinding = ActivityDetailCatAtivityBinding.inflate(layoutInflater)
        setContentView(catDetailBinding.root)

        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        detailVM = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailCatViewModel::class.java]

        catDetail()
        backToMain()
    }

    private fun getName() : String? = sharedPreferences.getString(KEY_NAME, null)

    private fun catDetail(){
        detailVM.setCatDetail(getName().toString())
        detailVM.getCatDetail().observe(this, {
            catDetailBinding.apply {
                if (it != null) {
                    catName.text = it.name
                    catOrigin.text = it.from
                    altNameValue.text = it.anotherName
                    jangkaHidupValue.text = it.jangkaHidup
                    temperamenValue.text = it.temperament
                    deskripsiValue.text = it.description
                    wikipediaValue.text = it.wikipedia

                    ApiBase.apiInterface.imageCat(it.imageId).enqueue(object  : Callback<CatImageResponse> {
                        override fun onResponse(
                            call: Call<CatImageResponse>,
                            response: Response<CatImageResponse>
                        ) {


                            if (response.isSuccessful){
                                Glide.with(this@DetailCatAtivity)
                                    .load(response.body()?.url)
                                    .fitCenter()
                                    .placeholder(R.drawable.cat_blue)
                                    .error(R.drawable.cat_blue)
                                    .into(catDetailImg)
                            }
                        }

                        override fun onFailure(call: Call<CatImageResponse>, t: Throwable) {
                            Glide.with(this@DetailCatAtivity)
                                .load(R.drawable.cat_white)
                                .fitCenter()
                                .into(catDetailImg)

                            Toast.makeText(this@DetailCatAtivity, "Image not found!", Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
        })
    }

    private fun backToMain() {
        clickedValue = !clickedValue
        catDetailBinding.title.isClickable = true

        if (clickedValue){
            catDetailBinding.title.setOnClickListener {
                val intent = Intent(this@DetailCatAtivity, MainActivity::class.java)

                startActivity(intent)
            }
        }
    }
}