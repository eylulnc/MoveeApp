package com.eylulcan.moviefragment.ui.moviedetail.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.ReviewFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.Review
import com.eylulcan.moviefragment.util.Utils
import java.text.SimpleDateFormat
import java.util.*

class ReviewsAdapter :
    RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ReviewFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ReviewFragmentRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        reviewResult[position].let { review ->
            holder.binding.authorName.text = review.author
            holder.binding.expandTextView.text = review.content
            holder.binding.reviewsRatingBar.rating =
                review.authorDetails?.rating?.toFloat()?.div(2f) ?: 0f
            review.updatedAt?.let { it ->
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
                val output = formatter.format(parser.parse(it))
                holder.binding.postDateText.text = output
            }
            Glide.with(holder.binding.root).load(setImageUrl(review.authorDetails?.avatarPath))
                .placeholder(R.color.grey_light).into(holder.binding.authorProfileImage)
        }
    }

    override fun getItemCount(): Int = reviewResult.size

    private fun setImageUrl(poster_path: Any?): String = Utils.BASE_IMAGE_URL_185.plus(poster_path)

    private val diffUtil = object : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(
            oldItem: Review,
            newItem: Review
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Review,
            newItem: Review
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var reviewResult: List<Review>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

}