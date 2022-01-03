package com.eylulcan.moviefragment.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.databinding.DiscoverChildRecyclerRowBinding
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.util.Utils

class DiscoverChildAdapter(private val children : List<ResultMovie>, private val listener: MovieListener)
    : RecyclerView.Adapter<DiscoverChildAdapter.ViewHolder>(){

    class ViewHolder(val binding: DiscoverChildRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DiscoverChildRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        val child = children[position]
        Glide.with(holder.binding.movieListRecyclerViewImage).load(setImageUrl(child.posterPath))
            .into(holder.binding.movieListRecyclerViewImage)
        holder.binding.movieListRecyclerViewName.text = child.title
        holder.itemView.setOnClickListener {
            listener.onMovieClicked(child)
        }
    }

    fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_185.plus(poster_path)
}