package com.example.pruebaexm.user.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pruebaexm.R
import com.example.pruebaexm.database.model.Register
import com.example.pruebaexm.databinding.ItemRegisterBinding

class RegisterAdapter: RecyclerView.Adapter<RegisterAdapter.ViewHolder>() {
    private var listData = mutableListOf<Register>()

    class ViewHolder(vBind: View) : RecyclerView.ViewHolder(vBind) {
        val vBind = ItemRegisterBinding.bind(vBind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_register, parent, false)
    )

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = listData[position]
        holder.vBind.apply {
        tvName.text = "${dataItem.name} ${dataItem.surnames}"
        tvId.text = dataItem.id.toString()
        tvPhone.text = dataItem.phone
        tvEmail.text = dataItem.email
        tvLatitude.text = dataItem.latitude.toString() + ", " + dataItem.longitude.toString()
            Glide.with(ivImage.context).load(dataItem.image).into(ivImage)
        }

    }

    override fun getItemCount() = listData.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(data: List<Register>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

}