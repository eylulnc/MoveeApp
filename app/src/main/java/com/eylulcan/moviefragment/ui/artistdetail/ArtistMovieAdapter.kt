package com.eylulcan.moviefragment.ui.artistdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.databinding.ArtistMovieFragmentRecyclerRowBinding

class ArtistMovieAdapter : RecyclerView.Adapter<ArtistMovieAdapter.ViewHolder>() {

    class ViewHolder(val binding: ArtistMovieFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ArtistMovieFragmentRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 0
    }
}