package com.dicoding.picodiploma.catapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.catapp.R
import com.dicoding.picodiploma.catapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.catapp.model.api.ApiBase
import com.dicoding.picodiploma.catapp.model.data.CatImageResponse
import com.dicoding.picodiploma.catapp.model.data.CatResponse
import com.dicoding.picodiploma.catapp.view.adapter.CatAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding : ActivityMainBinding
    private var catList = ArrayList<CatResponse>()
    private var catImage = ArrayList<CatImageResponse>()
    private lateinit var catAdapter : CatAdapter
    private var clickedValue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        catList = arrayListOf()
        catImage = arrayListOf()

        searchConfig()
        notSearch(true)
        notFound(false)
        showSearchArea(false)
        showHeader(true)
    }

    override fun onBackPressed() {
        if (clickedValue) {

            this.finishAffinity()
            exitProcess(0)
            return
        }
        this.clickedValue = true
        val exitText = resources.getText(R.string.exit)
        Toast.makeText(this, exitText, Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ clickedValue = false }, 2000)
        showSearchArea(false)
    }

    private fun notFound(booleanFound: Boolean){
        mainBinding.apply {
            notFoundImg.visibility = if (booleanFound) View.VISIBLE else View.INVISIBLE
            notFoundText.visibility = if (booleanFound) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun notSearch(booleanSearch: Boolean){
        mainBinding.apply {
            notSearchImg.visibility = if (booleanSearch) View.VISIBLE else View.INVISIBLE
            notSearchText.visibility = if (booleanSearch) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun showSearchArea(booleanSearchArea: Boolean){
        mainBinding.apply {
            searchEt.visibility = if (booleanSearchArea) View.VISIBLE else View.INVISIBLE

            searchEt.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    catRecycler()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    private fun showHeader(booleanHeader: Boolean){
        mainBinding.apply {
            title.visibility = if (booleanHeader) View.VISIBLE else View.INVISIBLE
            searchIc.visibility = if (booleanHeader) View.VISIBLE else View.INVISIBLE
            view.visibility = if (booleanHeader) View.VISIBLE else View.INVISIBLE
            searchIc.isClickable = true

        }
    }

    private fun searchConfig(){
        mainBinding.searchIc.setOnClickListener {
            showHeader(false)
            showSearchArea(true)
        }
    }

    private fun catRecycler(){
        mainBinding.apply {
            catRv.setHasFixedSize(true)
            catRv.layoutManager = LinearLayoutManager(this@MainActivity)

            val input = mainBinding.searchEt.text.toString()

            ApiBase.apiInterface.searchCat(input).enqueue(object : Callback<ArrayList<CatResponse>>{
                override fun onResponse(
                    call: Call<ArrayList<CatResponse>>,
                    response: Response<ArrayList<CatResponse>>
                ) {
                    catAdapter = CatAdapter(catList)
                    catRv.adapter = catAdapter
                    catList.clear()
                    response.body()?.let { catList.addAll(it) }
                    catAdapter.listCat(catList)

                    notSearch(false)
                    showHeader(true)
                    showSearchArea(false)

                    if (response.body() == null) {
                        catRv.visibility = View.INVISIBLE
                        notSearch(false)
                        showHeader(true)
                        notFound(true)
                    }
                }

                override fun onFailure(call: Call<ArrayList<CatResponse>>, t: Throwable) {
                    notFound(true)
                }

            })
        }
    }
}