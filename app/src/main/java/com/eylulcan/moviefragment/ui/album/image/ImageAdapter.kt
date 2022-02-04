package com.eylulcan.moviefragment.ui.album.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.databinding.ImageFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.domain.entity.ProfileImageEntity
import com.eylulcan.moviefragment.domain.util.Utils
import javax.inject.Inject

class ImageAdapter @Inject constructor(private var glide: RequestManager) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(val binding: ImageFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ImageFragmentRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imagePath = album[position].filePath
        glide.load(setImageUrl(imagePath)).into(holder.binding.artistImageView)

    }

    override fun getItemCount(): Int = album.size

    private fun setImageUrl(profile_path: String?): String =
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(profile_path)

    private val diffUtil = object : DiffUtil.ItemCallback<ProfileImageEntity>() {
        override fun areItemsTheSame(
            oldItem: ProfileImageEntity,
            newItem: ProfileImageEntity
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ProfileImageEntity,
            newItem: ProfileImageEntity
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var album: List<ProfileImageEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

}