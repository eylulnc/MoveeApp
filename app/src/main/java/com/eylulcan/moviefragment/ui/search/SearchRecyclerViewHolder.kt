package com.eylulcan.moviefragment.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.SearchFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.SearchResult
import com.eylulcan.moviefragment.util.Utils

open class SearchRecyclerViewHolder(binding: SearchFragmentRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {

    class PersonViewHolder(private val binding: SearchFragmentRecyclerRowBinding) : SearchRecyclerViewHolder(binding){
        fun bind(person: SearchResult?){
            Glide.with(binding.root).load(setImageUrl(person?.profilePath)).placeholder(R.color.greylight).into(binding.searchItemImage)
            binding.searchItemName.text = person?.name
        }
        fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

    class MovieViewHolder(private val binding: SearchFragmentRecyclerRowBinding) : SearchRecyclerViewHolder(binding){
        fun bind(movie: SearchResult?){
            Glide.with(binding.root).load(setImageUrl(movie?.posterPath)).placeholder(R.color.greylight).into(binding.searchItemImage)
            binding.searchItemName.text = movie?.title
        }
        fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

    class TvShowViewHolder(private val binding: SearchFragmentRecyclerRowBinding) : SearchRecyclerViewHolder(binding){
        fun bind(tvShow: SearchResult?){
            Glide.with(binding.root).load(setImageUrl(tvShow?.posterPath)).placeholder(R.color.greylight).into(binding.searchItemImage)
            binding.searchItemName.text = tvShow?.name

        }
        fun setImageUrl(poster_path: String?): String =
            Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

}
