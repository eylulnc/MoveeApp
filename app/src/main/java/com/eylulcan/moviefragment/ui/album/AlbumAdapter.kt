package com.eylulcan.moviefragment.ui.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.databinding.AlbumFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.domain.entity.ProfileImageEntity
import com.eylulcan.moviefragment.domain.util.Utils
import me.samlss.broccoli.Broccoli
import java.util.*
import javax.inject.Inject

class AlbumAdapter @Inject constructor(private var glide: RequestManager) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    private val mViewPlaceholderManager: HashMap<View, Broccoli> = HashMap<View, Broccoli>()
    private val mTaskManager: HashMap<View, Runnable> = HashMap<View, Runnable>()
    private val broccoli = Broccoli()
    private val placeholderNeeded = arrayListOf<View>()

    private var onItemClickListener: ((album: List<ProfileImageEntity>?, position: Int) -> Unit)? =
        null

    fun setOnItemClickListener(listener: (album: List<ProfileImageEntity>?, position: Int) -> Unit) {
        onItemClickListener = listener
    }

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
        var broccoli = mViewPlaceholderManager[holder.itemView]
        if (broccoli == null) {
            broccoli = Broccoli()
            mViewPlaceholderManager[holder.itemView] = broccoli
        }
        setPlaceholders(holder)
        val url = setImageUrl(album[position].filePath)
        glide.load(url).into(holder.binding.imageView)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it1 -> it1(album, position) }
        }
        var task: Runnable? = mTaskManager[holder.itemView]
        if (task == null) {
            task = Runnable {
                run {
                    removePlaceholders()
                }
            }
            mTaskManager[holder.itemView] = task
        } else {
            holder.itemView.removeCallbacks(task)
        }
        holder.itemView.postDelayed(task, 2000)
    }

    override fun getItemCount(): Int = album.size

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

    var album: List<ProfileImageEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    private fun setPlaceholders(holder: ViewHolder) {
        placeholderNeeded.addAll(
            arrayListOf(
                holder.binding.templateImageView
            )
        )
        Utils.addPlaceholders(broccoli = broccoli, placeholderNeeded)
    }

    private fun removePlaceholders() {
        placeholderNeeded.forEach { view ->
            view.apply {
                broccoli.clearPlaceholder(this)
                this.isVisible = false
            }
        }
    }

}