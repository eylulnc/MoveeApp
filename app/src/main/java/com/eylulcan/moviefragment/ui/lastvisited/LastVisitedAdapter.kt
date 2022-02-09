package com.eylulcan.moviefragment.ui.lastvisited

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.databinding.LastVisitedRecyclerRowBinding
import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.util.Utils
import javax.inject.Inject

class LastVisitedAdapter @Inject constructor(private var glide: RequestManager) : RecyclerView.Adapter<LastVisitedAdapter.ViewHolder>() {

    private var childOnItemClickListener: ((id: Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        childOnItemClickListener = listener
    }

    class ViewHolder(val binding: LastVisitedRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LastVisitedRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        val url = setImageUrl(movie.posterPath)
        glide.load(url).into(holder.binding.lastVisitedRecyclerRowImage)
        holder.binding.lastVisitedRecyclerRowText.text = movie.title
        holder.itemView.setOnClickListener {
            childOnItemClickListener?.let { it1 -> it1(movie.id.toInt()) } }
        }

    override fun getItemCount(): Int {
        return movieList.size
    }
    private val diffUtil = object : DiffUtil.ItemCallback<MovieDao>() {
        override fun areItemsTheSame(
            oldItem: MovieDao,
            newItem: MovieDao
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: MovieDao,
            newItem: MovieDao
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var movieList: List<MovieDao>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    private fun setImageUrl(file_path: String?): String = Utils.BASE_IMAGE_URL_300.plus(file_path)


}