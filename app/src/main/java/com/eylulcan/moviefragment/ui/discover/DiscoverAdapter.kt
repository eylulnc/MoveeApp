package com.eylulcan.moviefragment.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.databinding.DiscoverRecyclerRowBinding
import com.eylulcan.moviefragment.model.ResultMovie

private const val MOST_POPULAR = 0
private const val TOP_RATED = 1
private const val THIRD = 2

class DiscoverAdapter(
    private val listener: MovieListener,
    private val title: String
) :
    RecyclerView.Adapter<DiscoverViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        return when (viewType) {
            MOST_POPULAR -> DiscoverViewHolder.MostPopular(
                DiscoverRecyclerRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TOP_RATED -> DiscoverViewHolder.TopRated(
                DiscoverRecyclerRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            THIRD -> DiscoverViewHolder.ThirdRecycler(
                DiscoverRecyclerRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> DiscoverViewHolder.MostPopular(
                DiscoverRecyclerRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MOST_POPULAR -> {
                holder.apply {
                    (holder as DiscoverViewHolder.MostPopular)
                    movieResultList?.get(position)?.let { holder.bind(it, listener) }
                }
            }
            TOP_RATED -> {
                holder.apply {
                    (holder as DiscoverViewHolder.TopRated)
                    movieResultList?.get(position)?.let { holder.bind(it, listener) }
                }
            }
            THIRD -> {
                holder.apply {
                    (holder as DiscoverViewHolder.ThirdRecycler)
                    movieResultList?.get(position)?.let { holder.bind(it, listener) }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isMostPopular() -> MOST_POPULAR
            isTopRated() -> TOP_RATED
            isThird() -> THIRD
            else -> MOST_POPULAR
        }
    }

    private fun isMostPopular(): Boolean = title == "Most Popular"


    private fun isTopRated(): Boolean = title == "Top Rated"


    private fun isThird(): Boolean = title == "Third Recycler View"

    override fun getItemCount(): Int {
        return movieResultList?.size ?: 0
    }

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

    var movieResultList: List<ResultMovie>?
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)
}