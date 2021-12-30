package com.eylulcan.moviefragment.ui.discover

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.databinding.DiscoverRecyclerRowBinding
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.util.Utils

open class DiscoverViewHolder(binding: DiscoverRecyclerRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class MostPopular(private val binding: DiscoverRecyclerRowBinding) :
        DiscoverViewHolder(binding) {
        fun bind(movie: ResultMovie, listener: MovieListener) {
            Glide.with(binding.root).load(setImageUrl(movie.posterPath))
                .into(binding.movieListRecyclerViewImage)
            binding.movieListRecyclerViewName.text = movie.title
            itemView.setOnClickListener {
                listener.onMovieClicked(movie)
            }
        }

        private fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

    class TopRated(private val binding: DiscoverRecyclerRowBinding) : DiscoverViewHolder(binding) {
        fun bind(movie: ResultMovie, listener: MovieListener) {
            Glide.with(binding.root).load(setImageUrl(movie.posterPath))
                .into(binding.movieListRecyclerViewImage)
            binding.movieListRecyclerViewName.text = movie.title
            itemView.setOnClickListener {
                listener.onMovieClicked(movie)
            }
        }

        private fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

    class NowPlaying(private val binding: DiscoverRecyclerRowBinding) :
        DiscoverViewHolder(binding) {
        fun bind(movie: ResultMovie, listener: MovieListener) {
            Glide.with(binding.root).load(setImageUrl(movie.posterPath))
                .into(binding.movieListRecyclerViewImage)
            binding.movieListRecyclerViewName.text = movie.title
            itemView.setOnClickListener {
                listener.onMovieClicked(movie)
            }
        }

        private fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

}