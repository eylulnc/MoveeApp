package com.eylulcan.moveetime.ui.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moveetime.Genres
import com.eylulcan.moveetime.databinding.GenresFragmentRecyclerRowBinding
import com.eylulcan.moveetime.domain.entity.GenreEntity
import javax.inject.Inject

class GenresAdapter @Inject constructor() :
    RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    private var onItemClickListener: ((id: Int) -> Unit)? = null

    class ViewHolder(val binding: GenresFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GenresFragmentRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        genreList[position].let { genre ->
            holder.binding.genresRowText.text = genre.name
            genre.id.let { id ->
                Genres.valueOfInt(id)?.movieGenreImage()
                    ?.let { holder.binding.genresFragmentImageView.setImageResource(it) }
                holder.itemView.setOnClickListener {
                    onItemClickListener?.let { it1 -> it1(id) }
                }
            }
        }
    }

    override fun getItemCount(): Int = genreList.size


    private val diffUtil = object : DiffUtil.ItemCallback<GenreEntity>() {
        override fun areItemsTheSame(
            oldItem: GenreEntity,
            newItem: GenreEntity
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: GenreEntity,
            newItem: GenreEntity
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var genreList: List<GenreEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        onItemClickListener = listener
    }
}