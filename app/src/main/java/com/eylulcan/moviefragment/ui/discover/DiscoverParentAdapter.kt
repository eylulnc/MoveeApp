package com.eylulcan.moviefragment.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.DiscoverParentFragmentBinding
import com.eylulcan.moviefragment.model.ResultMovie

class FlexibleAdapter
    (private val parents: List<List<ResultMovie>>, private val listener: MovieListener, private val recyclerViewListener: RecyclerViewListener) :
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
                adapter = DiscoverChildAdapter(movieResults, listener)
                setRecycledViewPool(viewPool)
            }
            holder.binding.discoverRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    recyclerViewListener.recyclerScrollListener(recyclerView)

                }
            })

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

    private val diffUtil = object : DiffUtil.ItemCallback<ResultMovie>() {
        override fun areItemsTheSame(
            oldItem: ResultMovie,
            newItem: ResultMovie
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ResultMovie,
            newItem: ResultMovie
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var movieResults: List<ResultMovie>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


}