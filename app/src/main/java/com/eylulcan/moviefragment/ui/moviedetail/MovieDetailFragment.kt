package com.eylulcan.moviefragment.ui.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding
import com.eylulcan.moviefragment.domain.entity.MovieDetailEntity
import com.eylulcan.moviefragment.ui.moviedetail.popup.CustomPopUpDialogFragment
import com.eylulcan.moviefragment.domain.util.Utils
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import me.samlss.broccoli.Broccoli
import javax.inject.Inject

private const val YOUTUBE_LINK = "https://www.youtube.com/watch?v="
private const val VIMEO_LINK = "https://vimeo.com/"
private const val MINUTES_IN_HOUR = 60

@AndroidEntryPoint
class MovieDetailFragment @Inject constructor(): Fragment() {

    @Inject
    lateinit var glide: RequestManager
    private lateinit var fragmentBinding: FragmentMovieDetailBinding
    private val tabNames = arrayOf("Cast", "Reviews", "More")
    private val movieDetailViewModel: DetailViewModel by activityViewModels()
    private var placeholderNeeded = arrayListOf<View>()
    private var broccoli = Broccoli()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentMovieDetailBinding.bind(view)
        setPlaceholders()
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
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(poster_path)

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
        movieDetailViewModel.videos.observe(viewLifecycleOwner) { videoList ->
            if (videoList.results?.isNotEmpty() == true) {
                fragmentBinding.watchButton.visibility = View.VISIBLE
                fragmentBinding.watchButton.setOnClickListener {
                    videoList?.results?.first()?.site?.let { videoSite ->
                        videoList.results.first().key?.let { key ->
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(setVideoUri(videoSite, key))
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        movieDetailViewModel.detailEntity.observe(viewLifecycleOwner) { movie ->
            removePlaceholders()
            setupUI(movie)
        }
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

    private fun calculateDuration(duration: Long): String {
        val durationHour = duration.div(MINUTES_IN_HOUR)
        val durationMinute = duration.mod(MINUTES_IN_HOUR)
        return getString(R.string.duration, durationHour, durationMinute)
    }

    private fun setupUI(movieDetailsEntity: MovieDetailEntity?) {
        movieDetailsEntity?.let { selectedMovie ->
            glide.load(setImageUrl(selectedMovie.backdropPath))
                .into(fragmentBinding.detailImagePoster)
            fragmentBinding.detailMovieNameText.text = selectedMovie.title
            fragmentBinding.expandTextView.text = selectedMovie.overview
            fragmentBinding.detailReleaseDateText.text = selectedMovie.releaseDate
            fragmentBinding.movieRateText.text = selectedMovie.voteAverage?.toString()
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

    private fun setPlaceholders() {
        arrayListOf(
            fragmentBinding.templateConstraintLayout,
            fragmentBinding.templateViewPagerView,
            fragmentBinding.templateCardView)
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