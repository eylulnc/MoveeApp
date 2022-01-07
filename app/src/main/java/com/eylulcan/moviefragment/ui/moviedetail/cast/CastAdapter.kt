package com.eylulcan.moviefragment.ui.moviedetail.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.CastFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.MovieCredits
import com.eylulcan.moviefragment.util.Utils

class CastAdapter(
    private val movieCredits: MovieCredits,
    private val artistListener: ItemListener
) :
    RecyclerView.Adapter<CastAdapter.ViewHolder>() {

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
        val artist = movieCredits.cast?.get(position)
        holder.binding.castArtistName.text = artist?.name
        Glide.with(holder.binding.root).load(setImageUrl(artist?.profilePath))
            .placeholder(R.color.greylight).into(holder.binding.castArtistImage)
        holder.itemView.setOnClickListener {
            artist?.id?.let { artistListener.onItemClicked(it) }
        }
    }

    override fun getItemCount(): Int = movieCredits.cast?.size ?: 0

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_185.plus(poster_path)

}