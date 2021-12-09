package com.eylulcan.moviefragment.ui.moviedetail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.Genres
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.ui.artistdetail.TabAdapter
import com.eylulcan.moviefragment.util.Utils
import com.google.android.material.tabs.TabLayoutMediator
import com.ms.square.android.expandabletextview.ExpandableTextView

class MovieDetailFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentMovieDetailBinding
    private val tabNames = arrayOf("Cast", "Reviews", "More")
    private val movieDetailViewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentMovieDetailBinding.bind(view)
        val selectedResultMovieDataArgument =
            arguments?.get(getString(R.string.movie)) as ResultMovie
        selectedResultMovieDataArgument.id?.let { id ->
            movieDetailViewModel.getMovieCast(id)
            movieDetailViewModel.getMovieMore(id)
            movieDetailViewModel.getReviews(id)
        }
        tabAdapterSetup()
        setupUI(selectedResultMovieDataArgument)
        if (selectedResultMovieDataArgument.video == true) {
            //TODO add get request to api in order to get video observe
        } else {
            fragmentBinding.videoView.isGone = true
        }
    }

    private fun setImageUrl(poster_path: String?): String {
        return Utils.BASE_IMAGE_URL_185.plus(poster_path)
    }

    private fun tabAdapterSetup() {
        val adapter = DetailTabAdapter(childFragmentManager, lifecycle)
        fragmentBinding.movieDetailViewPager.adapter = adapter
        TabLayoutMediator(
            fragmentBinding.detailTabLayout,
            fragmentBinding.movieDetailViewPager
        ) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    private fun setupUI(selectedResultMovieDataArgument: ResultMovie?) {
        selectedResultMovieDataArgument?.let { selectedMovie ->
            Glide.with(this).load(setImageUrl(selectedMovie.posterPath))
                .into(fragmentBinding.detailImagePoster)
            fragmentBinding.detailMovieNameText.text = selectedMovie.title
            fragmentBinding.expandTextView.text = selectedMovie.overview
            fragmentBinding.detailReleaseDateText.text = selectedMovie.releaseDate
            fragmentBinding.detailRatingBar.rating =
                (selectedMovie.voteAverage?.toFloat()?.div(2) ?: 0) as Float
            fragmentBinding.detailLanguageText.text = selectedMovie.originalLanguage
            var genresString = ""
            selectedMovie.genreIds?.forEach { genreId ->
                genresString = genresString.plus(genreId?.let { Genres.valueOfInt(it) }).plus(" ")
            }
            fragmentBinding.detailGenreNameText.text = genresString
        }
    }
}