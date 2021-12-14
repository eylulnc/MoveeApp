package com.eylulcan.moviefragment.ui.genres.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.Genres
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.GenreMovieListRecyclerRowBinding
import com.eylulcan.moviefragment.model.Movie
import com.eylulcan.moviefragment.ui.moviedetail.MovieDetailListener
import com.eylulcan.moviefragment.util.Utils

class GenreMovieListAdapter (private val movieList: Movie, private val movieDetailListener: MovieDetailListener) :
    RecyclerView.Adapter<GenreMovieListAdapter.ViewHolder>() {

    class ViewHolder(val binding: GenreMovieListRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GenreMovieListRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList.results?.get(position)
        holder.binding.movieNameMore.text = movie?.title
        Glide.with(holder.binding.root).load(setImageUrl(movie?.backdropPath))
            .placeholder(R.color.grey)
            .into(holder.binding.movieBackdropImage)
        var genresString = ""
        movie?.genreIds?.forEach { genreId ->
            genresString =
                genresString.plus(genreId?.let { Genres.valueOfInt(it)?.movieGenreName() }).plus(" | ")
        }
        holder.binding.genresMore.text = genresString
        holder.binding.ratingBarMore.rating = (movie?.voteAverage?.toFloat()?.div(2) ?: 0) as Float
        holder.itemView.setOnClickListener {
            movie?.id?.let { movieDetailListener.onMovieClicked(it) }
        }
    }

    override fun getItemCount(): Int {
        movieList.results?.size.let {
            return it ?: run {
                return 0
            }
        }
    }

    private fun setImageUrl(poster_path: String?): String {
        return Utils.BASE_IMAGE_URL_300.plus(poster_path)
    }
}