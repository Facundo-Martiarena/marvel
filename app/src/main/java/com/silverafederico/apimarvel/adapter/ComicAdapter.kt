package com.silverafederico.apimarvel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.silverafederico.apimarvel.R
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.models.MarvelComic
import com.silverafederico.apimarvel.databinding.ComicRecyclerviewBinding

interface OnItemComicClickListen{
    fun onItemComicClick(item: MarvelComic)
}

class ComicAdapter(private val list: List<MarvelComic>, private val onItemComicClickListen: OnItemComicClickListen): RecyclerView.Adapter<ComicAdapter.ComicViewHolder>(){
    inner class ComicViewHolder(val binding: ComicRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val binding = ComicRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return ComicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val item= list[position]
        with(holder.binding){
            image.load(item.image){
                crossfade(true)
                placeholder(R.drawable.image_placeholder)
            }
            //nameComic.text= item.title
        }
        holder.binding.root.setOnClickListener{
            onItemComicClickListen.onItemComicClick(item)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}