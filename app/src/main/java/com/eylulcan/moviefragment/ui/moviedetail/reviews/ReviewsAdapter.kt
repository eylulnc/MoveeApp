package com.eylulcan.moviefragment.ui.moviedetail.reviews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.ReviewFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.ReviewList
import com.eylulcan.moviefragment.util.Utils
import java.text.SimpleDateFormat
import java.util.*

class ReviewsAdapter(private val reviewList: ReviewList) :
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
        reviewList.results?.get(position)?.let { review ->
            holder.binding.authorName.text = review.author
            holder.binding.expandTextView.text = review.content
            holder.binding.reviewsRatingBar.rating = review.authorDetails?.rating?.toFloat()?.div(2f) ?: 0f
            val format = SimpleDateFormat("yyyy-MM-dd")
            review.updatedAt?.let {  it ->
                val date: Date? = format.parse(it)
                holder.binding.postDateText.text = date.toString()
            }
            Glide.with(holder.binding.root).load(setImageUrl(review.authorDetails?.avatarPath)).placeholder(R.color.greylight).into(holder.binding.authorProfileImage)
        }
    }

    override fun getItemCount(): Int {
        return reviewList.results?.size ?: 0
    }

    private fun setImageUrl(poster_path: Any?): String {
        return Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

}