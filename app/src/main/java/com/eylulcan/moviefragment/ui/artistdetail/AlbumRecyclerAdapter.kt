package com.eylulcan.moviefragment.ui.artistdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.databinding.BottomSheetRecyclerRowBinding
import com.eylulcan.moviefragment.model.ProfileImage
import com.eylulcan.moviefragment.util.Utils
import javax.inject.Inject

class AlbumRecyclerAdapter @Inject constructor(private val artistAlbum: List<ProfileImage>) :
    RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding: BottomSheetRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            BottomSheetRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = setImageUrl(artistAlbum[position].filePath)
        Glide.with(holder.binding.root).load(url).into(holder.binding.artistImage)
    }

    override fun getItemCount(): Int = artistAlbum.size

    private fun setImageUrl(file_path: String?): String = Utils.BASE_IMAGE_URL_300.plus(file_path)

}