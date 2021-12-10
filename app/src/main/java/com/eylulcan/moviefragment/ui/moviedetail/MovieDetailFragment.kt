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
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.material.tabs.TabLayoutMediator
import com.ms.square.android.expandabletextview.ExpandableTextView

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
        val selectedResultMovieDataArgument =
            arguments?.get(getString(R.string.movie)) as ResultMovie
        if (selectedResultMovieDataArgument.video == true) {
            observeViewModel()
        } else {
            fragmentBinding.playerView.isGone = true
        }
        selectedResultMovieDataArgument.id?.let { id ->
            movieDetailViewModel.getMovieCast(id)
            movieDetailViewModel.getMovieMore(id)
            movieDetailViewModel.getReviews(id)
            movieDetailViewModel.getVideoClips(id)
        }
        tabAdapterSetup()
        setupUI(selectedResultMovieDataArgument)

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

    private fun observeViewModel() {
        movieDetailViewModel.videos.observe(viewLifecycleOwner, { videoList ->
                videoList?.results?.get(0)?.site?.let { videoSite ->
                    videoList.results[0].key?.let { key ->
                        mediaItem = MediaItem.fromUri(setVideoUri(videoSite, key))
                        val player: ExoPlayer = ExoPlayer.Builder(requireContext()).build()
                        player.setMediaItem(mediaItem)
                        player.prepare()
                        fragmentBinding.playerView.player = player
                    }
                }
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
}