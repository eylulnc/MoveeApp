package com.eylulcan.moviefragment.ui.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.DiscoverChildRecyclerRowBinding
import com.eylulcan.moviefragment.databinding.DiscoverParentFragmentBinding
import com.eylulcan.moviefragment.model.ResultMovie

class FlexibleAdapter
    (private val parents: List<List<ResultMovie>>, private val listener: MovieListener) :
    RecyclerView.Adapter<FlexibleAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    class ViewHolder(val binding: DiscoverParentFragmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DiscoverParentFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return parents.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val parent = parents[position]

        if (position == 2) {
            holder.binding.categoryText.text =
                holder.itemView.context.getString(R.string.now_playing)
            val childLayoutManager =
                GridLayoutManager(holder.binding.discoverRecyclerView.context, 3)
            childLayoutManager.initialPrefetchItemCount = 3
            holder.binding.discoverRecyclerView.apply {
                layoutManager = childLayoutManager
                adapter = DiscoverChildAdapter(parent, listener)
                setRecycledViewPool(viewPool)
            }
        } else {
            if (position == 0) {
                holder.binding.categoryText.text =
                    holder.itemView.context.getString(R.string.most_popular)
            } else if (position == 1) {
                holder.binding.categoryText.text =
                    holder.itemView.context.getString(R.string.top_rated)
            }
            val childLayoutManager =
                LinearLayoutManager(
                    holder.binding.discoverRecyclerView.context,
                    RecyclerView.HORIZONTAL,
                    false
                )
            childLayoutManager.initialPrefetchItemCount = 3
            holder.binding.discoverRecyclerView.apply {
                layoutManager = childLayoutManager
                adapter = DiscoverChildAdapter(parent, listener)
                setRecycledViewPool(viewPool)
            }
        }
    }

}