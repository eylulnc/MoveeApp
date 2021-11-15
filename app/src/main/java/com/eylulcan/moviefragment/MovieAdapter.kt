package com.eylulcan.moviefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.databinding.RecyclerRowBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult


class MovieAdapter(
    private val movieList: ArrayList<Movie>,
    private val movieListener: MovieListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    fun MovieAdapter(movieList: ArrayList<Movie>) {
        this.movieList.addAll(movieList)
    }

    class ViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.movieLisRecyclerViewImage.setImageResource(movieList[position].image)
        holder.binding.movieLisRecyclerViewImage.transitionName = movieList[position].name
        holder.binding.movieLisRecyclerViewName.text = movieList[position].name
        holder.itemView.setOnClickListener {
            movieListener.onMovieClicked(position,holder.binding.movieLisRecyclerViewImage)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

     fun updateMovieList(newMovieList: ArrayList<Movie>){
        val diffCallback = MovieDiffUtil(this.movieList, newMovieList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.movieList.clear()
        this.movieList.addAll(newMovieList)
        diffResult.dispatchUpdatesTo(this)
    }
}