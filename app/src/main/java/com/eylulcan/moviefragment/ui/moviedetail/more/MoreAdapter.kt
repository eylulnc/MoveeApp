package com.eylulcan.moviefragment.ui.moviedetail.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.Genres
import com.eylulcan.moviefragment.databinding.MoreFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.domain.entity.ResultMovieEntity
import com.eylulcan.moviefragment.domain.util.Utils
import javax.inject.Inject

class MoreAdapter @Inject constructor(private var glide: RequestManager) :
    RecyclerView.Adapter<MoreAdapter.ViewHolder>() {

    private var onItemClickListener: ((id: Int) -> Unit)? = null
    private var genreNames: String = ""

    class ViewHolder(val binding: MoreFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MoreFragmentRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieEntities[position]
        holder.binding.movieNameMore.text = movie.title
        glide.load(setImageUrl(movie.backdropPath)).into(holder.binding.movieImage)
        genreNames = ""
        movie.genreIds?.forEach { genreId ->
            genreNames =
                genreNames.plus(genreId?.let { Genres.valueOfInt(it)?.movieGenreName() })
                    .plus(" | ")
        }
        holder.binding.genresMore.text = genreNames
        holder.binding.ratingBarMore.rating = (movie.voteAverage?.toFloat()?.div(2) ?: 0f)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it1 -> movie.id?.let { it2 -> it1(it2) } }
        }
    }

    override fun getItemCount(): Int = movieEntities.size

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_300.plus(poster_path)

    private val diffUtil = object : DiffUtil.ItemCallback<ResultMovieEntity>() {
        override fun areItemsTheSame(
            oldItem: ResultMovieEntity,
            newItem: ResultMovieEntity
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ResultMovieEntity,
            newItem: ResultMovieEntity
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var movieEntities: List<ResultMovieEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        onItemClickListener = listener
    }

}