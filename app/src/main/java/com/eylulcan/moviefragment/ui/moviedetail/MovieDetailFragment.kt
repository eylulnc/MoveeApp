package com.eylulcan.moviefragment.ui.moviedetail

import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding
import com.eylulcan.moviefragment.model.MovieDetail
import com.eylulcan.moviefragment.util.Utils
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.material.tabs.TabLayoutMediator


class MovieDetailFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentMovieDetailBinding
    private val tabNames = arrayOf("Cast", "Reviews", "More")
    private val movieDetailViewModel: DetailViewModel by activityViewModels()
    private val youtubeLink: String = "https://www.youtube.com/watch?v="
    private val vimeoLink: String = "https://vimeo.com/"
    private lateinit var mediaItem: MediaItem

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

    private fun observeViewModel() {
        movieDetailViewModel.videos.observe(viewLifecycleOwner, { videoList ->
            if (videoList.results?.isNotEmpty() == true) {
                fragmentBinding.playerView.isVisible = true
                videoList?.results.first().site?.let { videoSite ->
                    videoList.results.first().key?.let { key ->
                        val mediaUri = Uri.parse(setVideoUri(videoSite, key))
                        mediaItem = MediaItem.fromUri(mediaUri)
                        val player: ExoPlayer = ExoPlayer.Builder(requireContext()).build()
                        fragmentBinding.playerView.player = player
                        player.addMediaItem(mediaItem)
                        player.prepare()
                        player.playWhenReady = true
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
            uri = youtubeLink.plus(key)
        } else if (videoSite == getString(R.string.vimeo)) {
            uri = vimeoLink.plus(key)
        }
        return uri
    }

    private fun calculateDuration(duration: Int): String {
        val durationHour = duration / 60
        val durationMinute = duration % 60
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
                (selectedMovie.voteAverage?.toFloat()?.div(2) ?: 0) as Float
            var movieLanguage = ""
            selectedMovie.spokenLanguages?.forEach { language ->
                movieLanguage = movieLanguage.plus(language.name).plus(" | ")
            }
            movieLanguage = movieLanguage.subSequence(0, movieLanguage.length - 2) as String
            fragmentBinding.languageText.text = getString(R.string.language, movieLanguage)
            var genresString = ""
            selectedMovie.genres?.forEach { genre ->
                genresString = genresString.plus(genre.name).plus(" | ")
            }
            fragmentBinding.detailGenreNameText.text =
                genresString.subSequence(0, genresString.length - 2)
            selectedMovie.runtime?.let { runtime ->
                fragmentBinding.detailDurationText.text = calculateDuration(runtime)
            }
        }
    }
}