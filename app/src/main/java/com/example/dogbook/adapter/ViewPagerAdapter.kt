package com.example.dogbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogbook.R
import com.example.dogbook.model.Dog
import kotlinx.android.synthetic.main.item_container_dog.view.*

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    private var dogsData: List<Dog> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dogName : TextView = itemView.dogName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_container_dog, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDog = dogsData[position]
        holder.dogName.text = currentDog.dogName
    }

    override fun getItemCount(): Int {
        return dogsData.size
    }

    fun setDogsData(dogsData: List<Dog>) {
        this.dogsData = dogsData
        notifyItemRangeChanged(0, itemCount)
    }
}