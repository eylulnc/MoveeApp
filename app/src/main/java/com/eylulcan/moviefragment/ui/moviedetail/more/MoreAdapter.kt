package com.eylulcan.moviefragment.ui.moviedetail.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.Genres
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.MoreFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.Movie
import com.eylulcan.moviefragment.ui.moviedetail.MovieDetailListener
import com.eylulcan.moviefragment.util.Utils

class MoreAdapter(private val movie: Movie, private val movieListener: MovieDetailListener) :
    RecyclerView.Adapter<MoreAdapter.ViewHolder>() {

    private var genreNames: String = ""

    class ViewHolder(val binding: MoreFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MoreFragmentRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movie.results?.get(position)
        holder.binding.movieNameMore.text = movie?.title
        Glide.with(holder.binding.root).load(setImageUrl(movie?.backdropPath))
            .placeholder(R.color.grey)
            .into(holder.binding.movieBackdropImage)
        movie?.genreIds?.forEach { genreId ->
            genreNames =
                genreNames.plus(genreId?.let { Genres.valueOfInt(it)?.movieGenreName() })
                    .plus(" | ")
        }
        holder.binding.genresMore.text = genreNames
        holder.binding.ratingBarMore.rating = (movie?.voteAverage?.toFloat()?.div(2) ?: 0f)
        holder.itemView.setOnClickListener {
            movie?.id?.let { movieListener.onMovieClicked(it) }
        }
    }

    override fun getItemCount(): Int = movie.results?.size ?: 0

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_300.plus(poster_path)

}