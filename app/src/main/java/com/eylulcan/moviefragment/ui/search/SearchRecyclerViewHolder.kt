package com.eylulcan.moviefragment.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.databinding.SearchFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.domain.entity.SearchResultEntity
import com.eylulcan.moviefragment.domain.util.Utils

open class SearchRecyclerViewHolder(binding: SearchFragmentRecyclerRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class PersonViewHolder(private val binding: SearchFragmentRecyclerRowBinding) :
        SearchRecyclerViewHolder(binding) {

        fun bind(person: SearchResultEntity?, listener: SearchListener, glide: RequestManager) {
            glide.load(setImageUrl(person?.profilePath)).into(binding.searchItemImage)
            binding.searchItemName.text = person?.name
            itemView.setOnClickListener {
                person?.id?.let { listener.onPersonClicked(it) }
            }
        }

        fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

    class MovieViewHolder(private val binding: SearchFragmentRecyclerRowBinding) :
        SearchRecyclerViewHolder(binding) {

        fun bind(movie: SearchResultEntity, listener: SearchListener, glide: RequestManager) {
            glide.load(setImageUrl(movie.posterPath)).into(binding.searchItemImage)
            binding.searchItemName.text = movie.title
            itemView.setOnClickListener {
                movie.id.let { listener.onMovieClicked(it) }
            }

        }

        fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

    class TvShowViewHolder(private val binding: SearchFragmentRecyclerRowBinding) :
        SearchRecyclerViewHolder(binding) {

        fun bind(tvShow: SearchResultEntity, glide: RequestManager) {
            glide.load(setImageUrl(tvShow.posterPath)).into(binding.searchItemImage)
            binding.searchItemName.text = tvShow.name
        }

        fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }


}
