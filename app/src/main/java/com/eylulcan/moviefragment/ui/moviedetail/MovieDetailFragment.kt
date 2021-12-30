package com.eylulcan.moviefragment.ui.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding
import com.eylulcan.moviefragment.model.MovieDetail
import com.eylulcan.moviefragment.ui.moviedetail.popup.CustomPopUpDialogFragment
import com.eylulcan.moviefragment.util.Utils
import com.google.android.material.tabs.TabLayoutMediator

private const val YOUTUBE_LINK = "https://www.youtube.com/watch?v="
private const val VIMEO_LINK = "https://vimeo.com/"
private const val MINUTES_IN_HOUR = 60

class MovieDetailFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentMovieDetailBinding
    private val tabNames = arrayOf("Cast", "Reviews", "More")
    private val movieDetailViewModel: DetailViewModel by activityViewModels()
    private var genreNames: String = ""
    private var movieLanguages: String = ""

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
        val selectedMovieDataArgument =
            arguments?.get(getString(R.string.movieId)) as Int
        observeViewModel()
        selectedMovieDataArgument.let { id ->
            movieDetailViewModel.getMovieCast(id)
            movieDetailViewModel.getMovieMore(id)
            movieDetailViewModel.getReviews(id)
            movieDetailViewModel.getMovieDetail(id)
            movieDetailViewModel.getVideoClips(id)
        }
        tabAdapterSetup()
    }

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_185.plus(poster_path)

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

    private fun observeViewModel() {
        movieDetailViewModel.videos.observe(viewLifecycleOwner, { videoList ->
            if (videoList.results?.isNotEmpty() == true) {
                fragmentBinding.watchButton.visibility = View.VISIBLE
                fragmentBinding.watchButton.setOnClickListener {
                    videoList?.results.first().site?.let { videoSite ->
                        videoList.results.first().key?.let { key ->
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(setVideoUri(videoSite, key))
                            startActivity(intent)
                        }
                    }
                }
            }
        })
        movieDetailViewModel.detail.observe(viewLifecycleOwner, { movie ->
            setupUI(movie)
        })
    }

    private fun setVideoUri(videoSite: String, key: String): String {
        var uri = ""
        if (videoSite == getString(R.string.youtube)) {
            uri = YOUTUBE_LINK.plus(key)
        } else if (videoSite == getString(R.string.vimeo)) {
            uri = VIMEO_LINK.plus(key)
        }
        return uri
    }

    private fun calculateDuration(duration: Int): String {
        val durationHour = duration.div(MINUTES_IN_HOUR)
        val durationMinute = duration.mod(MINUTES_IN_HOUR)
        return getString(R.string.duration, durationHour, durationMinute)
    }

    private fun setupUI(movieDetails: MovieDetail?) {
        movieDetails?.let { selectedMovie ->

            Glide.with(this).load(setImageUrl(selectedMovie.posterPath))
                .into(fragmentBinding.detailImagePoster)
            fragmentBinding.detailMovieNameText.text = selectedMovie.title
            fragmentBinding.expandTextView.text = selectedMovie.overview
            fragmentBinding.detailReleaseDateText.text = selectedMovie.releaseDate
            fragmentBinding.detailRatingBar.rating =
                (selectedMovie.voteAverage?.toFloat()?.div(2) ?: 0f)
            selectedMovie.spokenLanguages?.forEach { language ->
                movieLanguages = movieLanguages.plus(language.name).plus(" | ")
            }
            fragmentBinding.languageText.text = getString(R.string.language, movieLanguages)
            genreNames = ""
            selectedMovie.genres?.forEach { genre ->
                genreNames = genreNames.plus(genre.name).plus(" | ")
            }
            fragmentBinding.detailGenreNameText.text = genreNames
            selectedMovie.runtime?.let { runtime ->
                fragmentBinding.detailDurationText.text = calculateDuration(runtime)
            }
            fragmentBinding.rateButton.setOnClickListener {
                val popUpDialog = CustomPopUpDialogFragment()
                selectedMovie.id?.let { id ->
                    popUpDialog.setMovieID(id)
                }
                fragmentManager?.let { manager ->
                    popUpDialog.show(manager, "customDialog")
                }
            }
        }
    }
}