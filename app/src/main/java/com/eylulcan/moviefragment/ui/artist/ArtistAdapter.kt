package com.eylulcan.moviefragment.ui.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.ArtistFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.PeopleResult
import com.eylulcan.moviefragment.util.Utils

class ArtistAdapter(
    private val artistListener: ItemListener
) :
    RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    class ViewHolder(val binding: ArtistFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ArtistFragmentRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        peopleResult[position].let { artist ->
            holder.binding.artistRecyclerPersonName.text = artist.name
            Glide.with(holder.binding.root).load(setImageUrl(artist.profilePath))
                .placeholder(R.color.greylight)
                .into(holder.binding.artistRecyclerRowImage)
            holder.binding.artistRecyclerPersonName.text = artist.name
        }
        holder.itemView.setOnClickListener {
            peopleResult[position].id?.let { artistId ->
                artistListener.onItemClicked(artistId)
            }
        }
    }

    override fun getItemCount(): Int = peopleResult.size

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_185.plus(poster_path)

    private val diffUtil = object : DiffUtil.ItemCallback<PeopleResult>() {
        override fun areItemsTheSame(
            oldItem: PeopleResult,
            newItem: PeopleResult
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: PeopleResult,
            newItem: PeopleResult
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var peopleResult: List<PeopleResult>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)
}