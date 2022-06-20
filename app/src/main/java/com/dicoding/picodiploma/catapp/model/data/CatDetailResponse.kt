package com.dicoding.picodiploma.catapp.model.data

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

data class CatDetailResponse (
    val name : String,

    val id : String,

    @NonNull
    @SerializedName("reference_image_id")
    val imageId : String,

    @SerializedName("origin")
    val from : String,

    val temperament : String,

    val description : String,

    @SerializedName("wikipedia_url")
    val wikipedia : String,

    @SerializedName("alt_names")
    val anotherName : String,

    @SerializedName("life_span")
    val jangkaHidup : String
)