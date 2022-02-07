package com.eylulcan.moviefragment.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentOnboardViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardViewPagerFragment @Inject constructor(private val onboardAdapter: OnboardAdapter) :
    Fragment() {

    private lateinit var binding: FragmentOnboardViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboard_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnboardViewPagerBinding.bind(view)
        binding.onboardingViewPager.adapter = onboardAdapter
        binding.dotsIndicator.setViewPager2(binding.onboardingViewPager)
        binding.onboardButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_onBoardViewPagerFragment_to_loginFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.onBoardViewPagerFragment, true).build()
            )
        }
    }

}