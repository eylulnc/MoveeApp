package com.eylulcan.moveetime.ui.artistdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moveetime.databinding.BottomSheetRecyclerRowBinding
import com.eylulcan.moveetime.domain.entity.ProfileImageEntity
import com.eylulcan.moveetime.domain.util.Utils

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

    var artistAlbum: List<ProfileImageEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

}