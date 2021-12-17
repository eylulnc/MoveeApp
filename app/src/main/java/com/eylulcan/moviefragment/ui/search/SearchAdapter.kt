package com.eylulcan.moviefragment.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.SearchFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.SearchResult
import com.eylulcan.moviefragment.model.SearchResultList

private const val PERSON_SEARCH = 0
private const val MOVIE_SEARCH = 1
private const val TV_SHOW_SEARCH = 2

class SearchAdapter(
    private val searchListener: SearchListener
) :
    RecyclerView.Adapter<SearchRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecyclerViewHolder {
        return when (viewType) {
            PERSON_SEARCH -> SearchRecyclerViewHolder.PersonViewHolder(
                SearchFragmentRecyclerRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            MOVIE_SEARCH -> SearchRecyclerViewHolder.MovieViewHolder(
                SearchFragmentRecyclerRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TV_SHOW_SEARCH -> SearchRecyclerViewHolder.TvShowViewHolder(
                SearchFragmentRecyclerRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> SearchRecyclerViewHolder.PersonViewHolder(
                SearchFragmentRecyclerRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: SearchRecyclerViewHolder, position: Int) {
        when (getItemViewType(position)) {
            PERSON_SEARCH -> {
                holder.apply {
                    (holder as SearchRecyclerViewHolder.PersonViewHolder)
                    holder.bind(searchResult[position])

                }
            }
            MOVIE_SEARCH -> {
                holder.apply {
                    (holder as SearchRecyclerViewHolder.MovieViewHolder)
                    holder.bind(searchResult[position])
                    holder.itemView.setOnClickListener {
                        searchResult[position].id?.let { id ->
                            searchListener.onMovieClicked(
                                id,
                                holder.itemView.findViewById(R.id.searchItemImage)
                            )
                        }
                    }
                }
            }
            TV_SHOW_SEARCH -> {
                holder.apply {
                    (holder as SearchRecyclerViewHolder.TvShowViewHolder)
                    holder.bind(searchResult[position])
                }
            }
        }
    }

    override fun getItemCount(): Int = searchResult.size

    override fun getItemViewType(position: Int): Int {
        return when {
            isPerson(position) -> PERSON_SEARCH
            isMovie(position) -> MOVIE_SEARCH
            isTvShow(position) -> TV_SHOW_SEARCH
            else -> PERSON_SEARCH
        }
    }

    private fun isMovie(position: Int): Boolean {
        return searchResult[position].mediaType == "movie"
    }

    private fun isPerson(position: Int): Boolean {
        return searchResult[position].mediaType == "person"
    }

    private fun isTvShow(position: Int): Boolean {
        return searchResult[position].mediaType == "tv"
    }

    private val diffUtil = object : DiffUtil.ItemCallback<SearchResult>() {
        override fun areItemsTheSame(
            oldItem: SearchResult,
            newItem: SearchResult
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SearchResult,
            newItem: SearchResult
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var searchResult: List<SearchResult>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

}

