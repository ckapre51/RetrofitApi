package com.example.retrofit_api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_api.databinding.ApidisplayBinding

class PopulationAdapter:RecyclerView.Adapter<PopulationAdapter.PopulationViewholder> (){

    inner class PopulationViewholder(val binding: ApidisplayBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Population>(){
        override fun areContentsTheSame(oldItem: Population, newItem: Population): Boolean {
            return  oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Population, newItem: Population): Boolean {
            return  oldItem.city == newItem.city
        }
    }
    private  val differ = AsyncListDiffer(this,diffCallback)
    var populations: List<Population>

    get() = differ.currentList
    set(value) {differ.submitList(value)}

    override fun getItemCount() = populations.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopulationViewholder {
        return PopulationViewholder(ApidisplayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }
//    fun String.appendA():String{
//        return "$this A"
//    }
    override fun onBindViewHolder(holder: PopulationViewholder, position: Int) {
        holder.binding.apply{
            val population = populations[position]
            title.text = population.city


        }
    }

}