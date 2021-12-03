package com.eylulcan.moviefragment.ui.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.ArtistFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.PopularPeopleList
import com.eylulcan.moviefragment.ui.movielist.MovieAdapter

class ArtistAdapter(private val popularPeopleList: PopularPeopleList) :
    RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    companion object {
        private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185"
    }

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
        holder.binding.artistRecyclerPersonName.text = popularPeopleList?.get(position)?.name
        popularPeopleList?.get(position)?.let { peopleList ->
            Glide.with(holder.binding.root).load(setImageUrl(peopleList.profilePath))
                .placeholder(R.drawable.placeholder_profile)
                .into(holder.binding.artistRecyclerRowImage)
            holder.binding.artistRecyclerPersonName.text = peopleList.name
        }
        holder.itemView.setOnClickListener {

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
        return BASE_IMAGE_URL.plus(poster_path)
    }
}