package com.eylulcan.moviefragment.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.databinding.MovieListRecyclerRowBinding
import com.eylulcan.moviefragment.model.Movie
import com.eylulcan.moviefragment.util.Utils

class DiscoverAdapter(private val movieItem: Movie, private val listener: MovieListener) :
    RecyclerView.Adapter<DiscoverAdapter.ViewHolder>() {

    class ViewHolder(val binding: MovieListRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MovieListRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieList = movieItem.results
        movieList?.get(position)?.let {  resultMovie ->
            Glide.with(holder.binding.root).load(setImageUrl(resultMovie.posterPath))
                .into(holder.binding.movieListRecyclerViewImage)
            holder.binding.movieListRecyclerViewName.text = resultMovie.title
            holder.itemView.setOnClickListener {
                listener.onMovieClicked(resultMovie,holder.binding.movieListRecyclerViewImage)
            }
        }
    }

    override fun getItemCount(): Int {
        movieItem.results?.let { movieList ->
            return movieList.size
        } ?: run {
            return 0
        }
    }

    private fun setImageUrl(poster_path: String?): String {
        return Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }
}