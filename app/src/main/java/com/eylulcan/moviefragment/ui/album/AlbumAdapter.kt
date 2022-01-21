package com.eylulcan.moviefragment.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.databinding.AlbumFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.ProfileImage
import com.eylulcan.moviefragment.util.Utils
import javax.inject.Inject

class AlbumAdapter @Inject constructor(private val imageListener: ImageListener) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    class ViewHolder(val binding: AlbumFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AlbumFragmentRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = setImageUrl(album[position].filePath)
        Glide.with(holder.binding.root).load(url).into(holder.binding.imageView)
        holder.itemView.setOnClickListener {
            imageListener.onImageClicked(album, position)
        }
    }

    override fun getItemCount(): Int = album.size

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

    var album: List<ProfileImage>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

}