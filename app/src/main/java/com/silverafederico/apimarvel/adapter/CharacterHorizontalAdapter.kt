package com.silverafederico.apimarvel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.silverafederico.apimarvel.R
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.models.MarvelComic
import com.silverafederico.apimarvel.databinding.CharacterVerticalRecyclerviewBinding
import com.silverafederico.apimarvel.databinding.ComicRecyclerviewBinding


class CharacterHorizontalAdapter(private val list: List<MarvelCharacter>, private val onItemClickListen: OnItemClickListen): RecyclerView.Adapter<CharacterHorizontalAdapter.CharacterViewHolder>(){
    inner class CharacterViewHolder(val binding: CharacterVerticalRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterVerticalRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item= list[position]
        with(holder.binding){
            image.load(item.image){
                crossfade(true)
                placeholder(R.drawable.image_placeholder)
            }
            nameCharacter.text= item.name
        }
        holder.binding.root.setOnClickListener{
            onItemClickListen.onItemCharacterClick(item)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}