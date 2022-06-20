package com.dicoding.picodiploma.catapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.picodiploma.catapp.model.data.CatResponse

class ListCatDiff(
    private val old : ArrayList<CatResponse>,
    private val new : ArrayList<CatResponse>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].name == new[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPosition = old[oldItemPosition]
        val newPosition = new[newItemPosition]

        return oldPosition.name == newPosition.name
    }
}