package com.eylulcan.moviefragment.ui.moviedetail.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentReviewsBinding
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.eylulcan.moviefragment.domain.entity.ReviewEntity
import com.eylulcan.moviefragment.ui.moviedetail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReviewsFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentReviewsBinding
    private val detailViewModel: DetailViewModel by activityViewModels()

    @Inject
    lateinit var reviewsAdapter: ReviewsAdapter
    private var lastLoadedPage: Int = 1
    private var reviewResultList: ArrayList<ReviewEntity> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewsBinding.bind(view)
        binding.reviewFragmentRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.reviewFragmentRecyclerView.adapter = reviewsAdapter
        observeViewModel()
        binding.reviewFragmentRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    lastLoadedPage += 1
                    binding.textView.isVisible = false
                    detailViewModel.getReviews(movieId = id, pageNo = lastLoadedPage)
                }
            }
        })
    }

    private fun observeViewModel() {
        detailViewModel.reviews.observe(this.requireActivity()) { reviewList ->
            reviewResultList.clear()
            reviewList?.let {
                if (reviewList.results.isEmpty()) {
                    binding.textView.isVisible = true
                    binding.reviewFragmentRecyclerView.isVisible = false
                } else {
                    reviewResultList.addAll(reviewList.results)
                    reviewsAdapter.reviewResult = reviewResultList
                    reviewsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

}