package com.eylulcan.moviefragment.ui.artistdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.databinding.BottomSheetRecyclerRowBinding
import com.eylulcan.moviefragment.model.ProfileImage
import com.eylulcan.moviefragment.util.Utils
import javax.inject.Inject

class AlbumRecyclerAdapter @Inject constructor(private var glide: RequestManager) :
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
        glide.load(url).into(holder.binding.artistImage)
    }

    override fun getItemCount(): Int = artistAlbum.size

    private fun setImageUrl(file_path: String?): String = Utils.BASE_IMAGE_URL_300.plus(file_path)

    private val diffUtil = object : DiffUtil.ItemCallback<ProfileImage>() {
        override fun areItemsTheSame(
            oldItem: ProfileImage,
            newItem: ProfileImage
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ProfileImage,
            newItem: ProfileImage
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var artistAlbum: List<ProfileImage>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

}