package com.eylulcan.moviefragment.ui.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding
import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.entity.MovieDetailEntity
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.eylulcan.moviefragment.domain.util.Utils
import com.eylulcan.moviefragment.ui.moviedetail.popup.CustomPopUpDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import me.samlss.broccoli.Broccoli
import javax.inject.Inject

private const val YOUTUBE_LINK = "https://www.youtube.com/watch?v="
private const val VIMEO_LINK = "https://vimeo.com/"
private const val MINUTES_IN_HOUR = 60
private const val IF_ID_NULL = 0
private const val EMPTY_STR = ""
private const val TAG = "customDialog"

@AndroidEntryPoint
class MovieDetailFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var glide: RequestManager
    private var _binding: FragmentMovieDetailBinding? = null
    private val fragmentBinding get() = _binding!!
    private val tabNames = arrayOf("Cast", "Reviews", "More")
    private val movieDetailViewModel: DetailViewModel by activityViewModels()
    private var movieId:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedMovieDataArgument =
            arguments?.get(getString(R.string.movieId)) as Int
        if (selectedMovieDataArgument == IF_ID_NULL) {
            Toast.makeText(context, getString(R.string.notFoundMovie), Toast.LENGTH_LONG).show()
            this.parentFragment?.findNavController()?.navigate(
                R.id.action_movieDetailFragment_to_dashboardFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.movieDetailFragment, true).build()
            )
        }
        observeViewModel()
        selectedMovieDataArgument.let { id ->
            movieId = id
            getData()
            tabAdapterSetup(id)
        }

    }

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(poster_path)

    private fun tabAdapterSetup(id:Int) {
        val adapter = DetailTabAdapter(childFragmentManager, lifecycle)
        fragmentBinding.movieDetailViewPager.adapter = adapter
        fragmentBinding.detailTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position) {
                    0 -> movieDetailViewModel.getMovieCast(id)
                    1 -> movieDetailViewModel.getReviews(id)
                    2 -> movieDetailViewModel.getMovieMore(id)
                    else -> {}
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        fragmentBinding.movieDetailViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position > 0 && positionOffset == 0.0f && positionOffsetPixels == 0) {
                    fragmentBinding.movieDetailViewPager.layoutParams.height =
                        fragmentBinding.movieDetailViewPager.getChildAt(0).height
                }
            }
        })
        TabLayoutMediator(
            fragmentBinding.detailTabLayout,
            fragmentBinding.movieDetailViewPager
        ) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    private fun observeViewModel() {
        movieDetailViewModel.videos.observe(viewLifecycleOwner) { videoList ->
            if (videoList.results.isNotEmpty()) {
                fragmentBinding.watchButton.visibility = View.VISIBLE
                fragmentBinding.watchButton.setOnClickListener {
                    videoList?.results?.first()?.site?.let { videoSite ->
                        videoList.results.first().key.let { key ->
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(setVideoUri(videoSite, key))
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        movieDetailViewModel.detailEntity.observe(viewLifecycleOwner) { movie ->
            setupUI(movie)
        }
        movieDetailViewModel.dbUpdated.observe(this.requireActivity()) {
            when (it) {
                is ResultData.Success -> {}
                else -> {}
            }
        }

    }

    private fun setVideoUri(videoSite: String, key: String): String {
        var uri = EMPTY_STR
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
            sendDataToDatabase(
                MovieDao(
                    selectedMovie.id.toString(),
                    selectedMovie.title,
                    selectedMovie.posterPath
                )
            )
            glide.load(setImageUrl(selectedMovie.backdropPath))
                .into(fragmentBinding.detailImagePoster)
            fragmentBinding.detailMovieNameText.text = selectedMovie.title
            fragmentBinding.expandTextView.text = selectedMovie.overview
            fragmentBinding.detailReleaseDateText.text = selectedMovie.releaseDate
            fragmentBinding.movieRateText.text = selectedMovie.voteAverage.toString()
            selectedMovie.runtime.let { runtime ->
                fragmentBinding.detailDurationText.text = calculateDuration(runtime)
            }
            fragmentBinding.rateButton.setOnClickListener {
                val popUpDialog = CustomPopUpDialogFragment()
                selectedMovie.id.let { id ->
                    popUpDialog.setMovieID(id)
                }
                fragmentManager?.let { manager ->
                    popUpDialog.show(manager, TAG)
                }
            }
        }
    }

    private fun sendDataToDatabase(movie: MovieDao) {
        val movieMap = hashMapOf<String, MovieDao>()
        movieMap[movie.id] = movie
        movieDetailViewModel.updatedFirestore(movieMap)
    }

    private fun getData() {
        movieDetailViewModel.getMovieCast(movieId)
        movieDetailViewModel.getMovieMore(movieId)
        movieDetailViewModel.getReviews(movieId)
        movieDetailViewModel.getMovieDetail(movieId)
        movieDetailViewModel.getVideoClips(movieId)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieDetailViewModel.setListsToDefault()
        _binding = null
    }

}