package com.eylulcan.moviefragment.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.databinding.SearchFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.PeopleResult
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.model.ResultTvShow
import com.eylulcan.moviefragment.model.SearchResultList

private const val PERSON_SEARCH = 0
private const val MOVIE_SEARCH = 1
private const val TV_SHOW_SEARCH = 2


class SearchAdapter(private val resultList: SearchResultList) :
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
        when(holder){
            is SearchRecyclerViewHolder.PersonViewHolder -> holder.bind(resultList.searchResults?.get(position)?.data as PeopleResult)
            is SearchRecyclerViewHolder.MovieViewHolder -> holder.bind(resultList.searchResults?.get(position)?.data as ResultMovie)
            is SearchRecyclerViewHolder.TvShowViewHolder -> holder.bind(resultList.searchResults?.get(position)?.data as ResultTvShow)
        }
    }

    override fun getItemCount(): Int = resultList.searchResults?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return when {
            isPerson(position) -> PERSON_SEARCH
            isMovie(position) -> MOVIE_SEARCH
            isTvShow(position) -> TV_SHOW_SEARCH
            else -> PERSON_SEARCH
        }
    }

    private fun isMovie(position: Int): Boolean {
        return resultList.searchResults?.get(position)?.mediaType == "movie"
    }

    private fun isPerson(position: Int): Boolean {
        return resultList.searchResults?.get(position)?.mediaType == "person"
    }

    private fun isTvShow(position: Int): Boolean {
        return resultList.searchResults?.get(position)?.mediaType == "tv"
    }
}


