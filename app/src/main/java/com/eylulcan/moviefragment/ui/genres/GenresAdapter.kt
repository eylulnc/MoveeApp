package com.eylulcan.moviefragment.ui.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.Genres
import com.eylulcan.moviefragment.databinding.GenresFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.GenreList

class GenresAdapter(private val genreList: GenreList, private val genresListener: GenreListener) :
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
        val genres = genreList.genres
        genres?.get(position)?.let { genre ->
            holder.binding.genresRowText.text = genre.name
            genre.id?.let { id ->
                Genres.valueOfInt(id)?.movieGenreImage()
                    ?.let { holder.binding.genresFragmentImageView.setImageResource(it) }
                holder.itemView.setOnClickListener {
                    genresListener.onGenreClicked(id)
                }
            }
        }
    }

    override fun getItemCount(): Int = genreList.genres?.size ?: 0

}