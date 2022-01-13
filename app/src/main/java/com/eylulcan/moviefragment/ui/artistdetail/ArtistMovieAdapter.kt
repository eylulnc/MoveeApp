package com.eylulcan.moviefragment.ui.artistdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.ArtistMovieFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.ArtistMovieCredits
import com.eylulcan.moviefragment.util.Utils

class ArtistMovieAdapter(
    private val movieCredits: ArtistMovieCredits,
    private val artistMovieClickListener: ItemListener
) :
    RecyclerView.Adapter<ArtistMovieAdapter.ViewHolder>() {

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
        val movie = movieCredits.cast?.get(position)
        holder.binding.movieNameArtistMovie.text = movie?.title
        Glide.with(holder.binding.root).load(setImageUrl(movie?.posterPath))
            .placeholder(R.color.grey)
            .into(holder.binding.movieImage)
        holder.itemView.setOnClickListener {
            movie?.id?.let { id -> artistMovieClickListener.onItemClicked(id) }
        }
    }

    override fun getItemCount(): Int = movieCredits.cast?.size ?: 0

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(poster_path)

}