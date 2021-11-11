package com.eylulcan.moviefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.databinding.RecyclerRowBinding

class MovieAdapter(private var movieList: ArrayList<Movie>, private var movieListener: MovieListener): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.recyclerImage.setImageResource(movieList[position].image)
        holder.binding.recyclerName.text = movieList[position].name
        holder.itemView.setOnClickListener {
            movieListener.onMovieClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}