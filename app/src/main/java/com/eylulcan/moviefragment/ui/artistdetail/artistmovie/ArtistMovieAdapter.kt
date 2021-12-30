package com.eylulcan.moviefragment.ui.artistdetail.artistmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.Genres
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.ArtistMovieFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.ArtistMovieCredits
import com.eylulcan.moviefragment.util.Utils

class ArtistMovieAdapter(
    private val movieCredits: ArtistMovieCredits,
    private val artistMovieClickListener: ArtistMovieClickListener
) :
    RecyclerView.Adapter<ArtistMovieAdapter.ViewHolder>() {

    private var genreNames: String = ""

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
        Glide.with(holder.binding.root).load(setImageUrl(movie?.backdropPath))
            .placeholder(R.color.grey)
            .into(holder.binding.movieBackdropImage)
        genreNames = ""
        movie?.genreIds?.forEach { genreId ->
            genreNames = genreNames.plus(Genres.valueOfInt(genreId)?.movieGenreName()).plus(" | ")
        }
        holder.binding.genresArtistMovie.text = genreNames
        holder.binding.ratingBarArtistMovie.rating =
            (movie?.voteAverage?.toFloat()?.div(2) ?: 0f)
        holder.itemView.setOnClickListener {
            movie?.id?.let { id -> artistMovieClickListener.onMovieClick(id) }
        }
    }

    override fun getItemCount(): Int = movieCredits.cast?.size ?: 0

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_300.plus(poster_path)

}