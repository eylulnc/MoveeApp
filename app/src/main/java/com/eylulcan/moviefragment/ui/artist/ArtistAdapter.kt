package com.eylulcan.moviefragment.ui.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.databinding.ArtistFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.PopularPeopleList

class ArtistAdapter(private val popularPeopleList: PopularPeopleList) :
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
        holder.binding.artistRecyclerPersonName.text = popularPeopleList?.get(position)?.name
    }

    override fun getItemCount(): Int {
        popularPeopleList.results?.let { results ->
            return results.size
        } ?: run {
            return 0
        }
    }

}