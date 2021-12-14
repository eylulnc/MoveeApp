package com.eylulcan.moviefragment.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.databinding.SearchFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.PeopleResult
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.model.ResultTvShow
import com.eylulcan.moviefragment.util.Utils

open class SearchRecyclerViewHolder(binding: SearchFragmentRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {

    class PersonViewHolder(private val binding: SearchFragmentRecyclerRowBinding) : SearchRecyclerViewHolder(binding){
        fun bind(person: PeopleResult){
            Glide.with(binding.root).load(setImageUrl(person.profilePath)).into(binding.searchItemImage)
            binding.searchItemName.text = person.name
        }
        fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

    class MovieViewHolder(private val binding: SearchFragmentRecyclerRowBinding) : SearchRecyclerViewHolder(binding){
        fun bind(movie: ResultMovie){
            Glide.with(binding.root).load(setImageUrl(movie.posterPath)).into(binding.searchItemImage)
            binding.searchItemName.text = movie.title
        }
        fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

    class TvShowViewHolder(private val binding: SearchFragmentRecyclerRowBinding) : SearchRecyclerViewHolder(binding){
        fun bind(tvShow: ResultTvShow){
            Glide.with(binding.root).load(setImageUrl(tvShow.posterPath)).into(binding.searchItemImage)
            binding.searchItemName.text = tvShow.name

        }
        fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

}
