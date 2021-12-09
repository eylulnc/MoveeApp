package com.eylulcan.moviefragment.ui.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.ArtistFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.PopularPeopleList
import com.eylulcan.moviefragment.util.Utils

class ArtistAdapter(
    private val popularPeopleList: PopularPeopleList,
    private val artistListener: ArtistListener
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
        val popularPeopleList = popularPeopleList.results
        val artist = popularPeopleList?.get(position)
        holder.binding.artistRecyclerPersonName.text = artist?.name
        artist?.let { artist ->
            Glide.with(holder.binding.root).load(setImageUrl(artist.profilePath))
                .placeholder(R.color.greylight)
                .into(holder.binding.artistRecyclerRowImage)
            holder.binding.artistRecyclerPersonName.text = artist.name
        }
        holder.itemView.setOnClickListener {
            artist?.id?.let { id ->
                artistListener.onArtistClicked(id)
            }
        }
    }

    override fun getItemCount(): Int {
        popularPeopleList.results?.let { results ->
            return results.size
        } ?: run {
            return 0
        }
    }

    private fun setImageUrl(poster_path: String?): String {
        return Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }
}