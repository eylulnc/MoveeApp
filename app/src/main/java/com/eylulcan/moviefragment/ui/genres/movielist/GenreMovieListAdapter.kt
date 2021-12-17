package com.eylulcan.moviefragment.ui.genres.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.Genres
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.GenreMovieListRecyclerRowBinding
import com.eylulcan.moviefragment.model.Movie
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.model.SearchResult
import com.eylulcan.moviefragment.ui.moviedetail.MovieDetailListener
import com.eylulcan.moviefragment.util.Utils

class GenreMovieListAdapter(
    private val movieDetailListener: MovieDetailListener
) :
    RecyclerView.Adapter<GenreMovieListAdapter.ViewHolder>() {

    private var genreNames: String = ""
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
        val movie = movieResult[position]
        holder.binding.movieNameMore.text = movie.title
        Glide.with(holder.binding.root).load(setImageUrl(movie.backdropPath))
            .placeholder(R.color.grey)
            .into(holder.binding.movieBackdropImage)
        movie.genreIds?.forEach { genreId ->
            genreNames =
                genreNames.plus(genreId?.let { Genres.valueOfInt(it)?.movieGenreName() })
                    .plus(" | ")
        }
        holder.binding.genresMore.text = genreNames
        holder.binding.ratingBarMore.rating = (movie.voteAverage?.toFloat()?.div(2) ?: 0f)
        holder.itemView.setOnClickListener {
            movie.id?.let { movieDetailListener.onMovieClicked(it) }
        }
    }

    override fun getItemCount(): Int = movieResult.size ?: 0

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_300.plus(poster_path)

    private val diffUtil = object : DiffUtil.ItemCallback<ResultMovie>() {
        override fun areItemsTheSame(
            oldItem: ResultMovie,
            newItem: ResultMovie
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ResultMovie,
            newItem: ResultMovie
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var movieResult: List<ResultMovie>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)
}