package com.eylulcan.moviefragment.ui.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.Genres
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.databinding.GenresFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.Genre
import com.eylulcan.moviefragment.model.GenreList
import com.eylulcan.moviefragment.model.ResultMovie
import javax.inject.Inject

class GenresAdapter @Inject constructor(private val genresListener: ItemListener) :
    RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

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
            genre.id?.let { id ->
                Genres.valueOfInt(id)?.movieGenreImage()
                    ?.let { holder.binding.genresFragmentImageView.setImageResource(it) }
                holder.itemView.setOnClickListener {
                    genresListener.onItemClicked(id)
                }
            }
        }
    }

    override fun getItemCount(): Int = genreList.size


    private val diffUtil = object : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var genreList: List<Genre>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)
}