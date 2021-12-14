package com.eylulcan.moviefragment.ui.moviedetail.reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentReviewsBinding
import com.eylulcan.moviefragment.ui.moviedetail.DetailViewModel

class ReviewsFragment : Fragment() {

    private lateinit var binding: FragmentReviewsBinding
    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var reviewsAdapter: ReviewsAdapter

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
        observeViewModel()

    }

    private fun observeViewModel() {
        detailViewModel.reviews.observe(viewLifecycleOwner, { reviewList ->
            reviewsAdapter = ReviewsAdapter(reviewList)
            binding.reviewFragmentRecyclerView.adapter = reviewsAdapter
        })
    }

}