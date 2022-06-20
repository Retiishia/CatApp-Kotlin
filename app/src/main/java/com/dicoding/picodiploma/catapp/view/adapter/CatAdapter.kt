package com.dicoding.picodiploma.catapp.view.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.catapp.R
import com.dicoding.picodiploma.catapp.databinding.ItemCatBinding
import com.dicoding.picodiploma.catapp.model.api.ApiBase
import com.dicoding.picodiploma.catapp.model.data.CatImageResponse
import com.dicoding.picodiploma.catapp.model.data.CatResponse
import com.dicoding.picodiploma.catapp.utils.GlideApp
import com.dicoding.picodiploma.catapp.utils.ListCatDiff
import com.dicoding.picodiploma.catapp.view.DetailCatAtivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatAdapter(private val catList : ArrayList<CatResponse>) : RecyclerView.Adapter<CatAdapter.CatViewHolder>(){
    lateinit var sharedPreferences: SharedPreferences
    val PREFERENCES_NAME = "cat_app_preferences"
    val KEY_NAME = "key_name"
    private var catLists = arrayListOf<CatResponse>()

    fun listCat(arrayCat : ArrayList<CatResponse>){
        val diffCallback = ListCatDiff(this.catLists, arrayCat)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        diffResult.dispatchUpdatesTo(this)

        this.catLists = arrayCat
    }

    inner class CatViewHolder(private val catItemListBinding : ItemCatBinding): RecyclerView.ViewHolder(catItemListBinding.root) {
        fun catListBind (catResponse: CatResponse){
            with(catItemListBinding){

                ApiBase.apiInterface.imageCat(catResponse.imageId).enqueue(object : Callback<CatImageResponse>{
                    override fun onResponse(
                        call: Call<CatImageResponse>,
                        response: Response<CatImageResponse>
                    ) {
                        if (response.isSuccessful){

                            GlideApp.with(itemView.context)
                                .load(response.body()?.url)
                                .fitCenter()
                                .placeholder(R.drawable.cat_blue)
                                .error(R.drawable.sad_ic)
                                .into(catImg)
                        }
                    }

                    override fun onFailure(call: Call<CatImageResponse>, t: Throwable) {
                        GlideApp.with(itemView.context)
                            .load(R.drawable.cat_white)
                            .fitCenter()
                            .into(catImg)

                        Toast.makeText(itemView.context, "Image not found!", Toast.LENGTH_SHORT).show()
                    }


                })

                catName.text = catResponse.name
                catOrigin.text = catResponse.from


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val data = catLists[position]
        holder.catListBind(data)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailCatAtivity::class.java)

            sharedPreferences = holder.itemView.context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            saveName(data.id)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = catList.size

    private fun saveName(catName : String){
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putString(KEY_NAME, catName)
        name.apply()
    }
}