package com.eylulcan.moviefragment.ui.moviedetail.popup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.MovieDetailPopupRatingScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomPopUpDialogFragment : DialogFragment() {

    private var movieID: Int = -1
    private var sessionID: String = ""
    private lateinit var binding: MovieDetailPopupRatingScreenBinding
    private val popUpViewModel: PopUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View =
            inflater.inflate(R.layout.movie_detail_popup_rating_screen, container, false)
        observeViewModel()
        binding = MovieDetailPopupRatingScreenBinding.bind(rootView)
        val sharedPref =
            activity?.getSharedPreferences(
                getString(R.string.app_package_name),
                Context.MODE_PRIVATE
            )
        sessionID = sharedPref?.getString(getString(R.string.sessionId), null).toString()

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.rateButton.setOnClickListener {
            val rate = binding.popUpRatingBar.rating.times(2).toDouble()
            popUpViewModel.postMovieRating(movieID = movieID, rate = rate, sessionId = sessionID)
            dismiss()
        }
        return rootView
    }

    fun setMovieID(id: Int) {
        this.movieID = id
    }

    private fun observeViewModel() {
        popUpViewModel.responseRating.observe(viewLifecycleOwner) {
            // TODO ask
        }
    }
}