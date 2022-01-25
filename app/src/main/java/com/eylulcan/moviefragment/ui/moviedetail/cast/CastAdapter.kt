package com.eylulcan.moviefragment.ui.moviedetail.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.CastFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.MovieCast
import com.eylulcan.moviefragment.model.MovieCredits
import com.eylulcan.moviefragment.model.PeopleResult
import com.eylulcan.moviefragment.util.Utils
import javax.inject.Inject

class CastAdapter @Inject constructor( private val glide : RequestManager) :
    RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    private var onItemClickListener: ((id: Int) -> Unit)? = null
    class ViewHolder(val binding: CastFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CastFragmentRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = movieCredits[position]
        holder.binding.castArtistName.text = artist.name
        holder.binding.characterName.text = artist.character
        glide.load(setImageUrl(artist.profilePath)).into(holder.binding.castArtistImage)
        holder.itemView.setOnClickListener {
            artist.id?.let { onItemClickListener?.let { it1 -> it1(it) } }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<MovieCast>() {
        override fun areItemsTheSame(
            oldItem: MovieCast,
            newItem: MovieCast
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: MovieCast,
            newItem: MovieCast
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var movieCredits: List<MovieCast>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun getItemCount(): Int = movieCredits.size

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_185.plus(poster_path)

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        onItemClickListener = listener
    }

}