package com.silverafederico.apimarvel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.R
import coil.load
import com.silverafederico.apimarvel.databinding.CharacterRecyclerviewBinding

interface OnItemClickListen{
    fun onItemClick(item: MarvelCharacter)
}

class CharacterAdapter(private val list: List<MarvelCharacter>, private val onItemClickListen: OnItemClickListen): RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>(){
    inner class CharacterViewHolder(val binding: CharacterRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item= list[position]
        with(holder.binding){
            image.load(item.image){
                crossfade(true)
                placeholder(R.drawable.image_placeholder)
            }
            name.text= item.name
            description.text= item.description
        }
        holder.binding.root.setOnClickListener{
            onItemClickListen.onItemClick(item)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}