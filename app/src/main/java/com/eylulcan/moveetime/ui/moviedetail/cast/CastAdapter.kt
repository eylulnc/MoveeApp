package com.eylulcan.moveetime.ui.moviedetail.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moveetime.databinding.CastFragmentRecyclerRowBinding
import com.eylulcan.moveetime.domain.entity.MovieCastEntity
import com.eylulcan.moveetime.domain.util.Utils
import javax.inject.Inject

class CastAdapter @Inject constructor(private val glide: RequestManager) :
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
            artist.id.let { onItemClickListener?.let { it1 -> it1(it) } }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<MovieCastEntity>() {
        override fun areItemsTheSame(
            oldItem: MovieCastEntity,
            newItem: MovieCastEntity
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: MovieCastEntity,
            newItem: MovieCastEntity
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var movieCredits: List<MovieCastEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun getItemCount(): Int = movieCredits.size

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_185.plus(poster_path)

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        onItemClickListener = listener
    }

}