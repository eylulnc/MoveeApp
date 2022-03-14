package com.eylulcan.moviefragment.ui.artistdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.BottomSheetFragmentBinding
import com.eylulcan.moviefragment.databinding.FragmentArtistDetailBinding
import com.eylulcan.moviefragment.domain.entity.ArtistDetailEntity
import com.eylulcan.moviefragment.domain.entity.ProfileImageEntity
import com.eylulcan.moviefragment.domain.util.Utils
import com.eylulcan.moviefragment.ui.ItemListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import me.samlss.broccoli.Broccoli
import javax.inject.Inject

private const val SPAN_COUNT = 3
private const val SCREEN_BOTTOM_RATIO = 0.5
private const val TAG = "ArtistDetail"

@AndroidEntryPoint
class ArtistDetailFragment @Inject constructor() : Fragment(), ItemListener, ArtistDetailListener {

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var artistMovieAdapter: ArtistMovieAdapter

    @Inject
    lateinit var albumRecyclerAdapter: AlbumRecyclerAdapter
    private var _binding: FragmentArtistDetailBinding? = null
    private val binding get() = _binding!!
    private val artistDetailViewModel: ArtistDetailViewModel by activityViewModels()
    private val placeholderNeeded = arrayListOf<View>()
    private var broccoli = Broccoli()
    private var _includeBinding: BottomSheetFragmentBinding? = null
    private val includeBinding get() = _includeBinding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private var screenBottomRatio = 0.40

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_artist_detail, container, false
        )
        _includeBinding = binding.bottomSheetFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPlaceholders()
        val bottomSheet: LinearLayout = binding.linearLayout
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val selectedPopularPersonID = arguments?.get(getString(R.string.artistId)) as Int
        observeViewModel()
        artistDetailViewModel.getArtistAlbum(selectedPopularPersonID)
        artistDetailViewModel.getArtistDetail(selectedPopularPersonID)
        artistDetailViewModel.getArtistMovieCredits(selectedPopularPersonID)
    }

    private fun observeViewModel() {
        artistDetailViewModel.artistDetail.observe(viewLifecycleOwner) { detail ->
            setupUIBottomSheet(detail)
            removePlaceholders()
            val detailArtist: ArtistDetailEntity = detail
            binding.artistName.text = detail.name
            binding.detail = detailArtist
            binding.listener = this
            binding.artistDetailCoverImage.let {
                glide.load(setImageUrl(detail.profilePath)).circleCrop().into(it)
            }
        }

        artistDetailViewModel.artistAlbum.observe(viewLifecycleOwner) { album ->
            val albumSize = album.artistProfileImages.size
            includeBinding.albumPreviewRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            if (albumSize > 5) {
                val albumTemp: List<ProfileImageEntity> =
                    album.artistProfileImages.subList(0, 4)
                includeBinding.albumPreviewRecyclerView.adapter = albumRecyclerAdapter
                albumRecyclerAdapter.artistAlbum = albumTemp
            } else {
                includeBinding.albumPreviewRecyclerView.adapter = albumRecyclerAdapter
                albumRecyclerAdapter.artistAlbum = album.artistProfileImages
            }
            binding.bottomSheetFragment.photosTextView.setOnClickListener {
                val albumDataBundle = bundleOf((getString(R.string.photo_album) to album))
                this.parentFragment?.findNavController()
                    ?.navigate(R.id.action_artistDetailFragment_to_albumFragment, albumDataBundle)
            }
        }

        artistDetailViewModel.artistMovieCredits.observe(viewLifecycleOwner) { movieCredits ->
            setupUI()
            if (movieCredits.cast.isEmpty()) {
                binding.knownForText.isVisible = false
                val displayMetrics = requireActivity().resources.displayMetrics
                val height = displayMetrics.heightPixels
                val maxHeight = height.times(screenBottomRatio).toInt()
                BottomSheetBehavior.from(binding.linearLayout).peekHeight = maxHeight
            } else {
                binding.artistMovieRecycler.adapter = artistMovieAdapter
                artistMovieAdapter.setOnItemClickListener { id -> onItemClicked(id) }
                artistMovieAdapter.artistMovieCredits = movieCredits.cast
            }
        }

    }

    private fun setupUI() {
        if (Utils.isTablet(requireContext())) {
            binding.artistMovieRecycler.layoutManager = GridLayoutManager(requireContext().applicationContext, SPAN_COUNT)
            screenBottomRatio = SCREEN_BOTTOM_RATIO
        } else {
            binding.artistMovieRecycler.layoutManager =
                LinearLayoutManager(requireContext().applicationContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setImageUrl(profile_path: String?): String =
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(profile_path)

    private fun setPlaceholders() {
        placeholderNeeded.addAll(
            arrayListOf(
                binding.templateArtistCoverView
            )
        )
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

    override fun onDestroyView() {
        super.onDestroyView()
        artistDetailViewModel.setListsToDefault()
        _includeBinding = null
        _binding = null
    }

    private fun setupUIBottomSheet(detail: ArtistDetailEntity) {
        includeBinding.expandTextView.text = detail.biography
        includeBinding.artistBirthdayText.text = detail.birthday
        includeBinding.artistDeathDayText.text = detail.deathday
        if (detail.biography.isEmpty()) {
            includeBinding.expandableText.isVisible = false
            includeBinding.biographyText.isVisible = false
        }
        if (detail.birthday.isEmpty()) {
            includeBinding.bornText.isVisible = false
            includeBinding.artistBirthdayText.isVisible = false
        }
        if (detail.deathday.isEmpty()) {
            includeBinding.artistDeathDayText.isVisible = false
            includeBinding.deathText.isVisible = false
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.bottomSheetFragment.arrowImage.setImageResource(R.drawable.down_arrow)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.bottomSheetFragment.arrowImage.setImageResource(R.drawable.up_arrow)
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                    else -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    override fun onItemClicked(id: Int) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to id)
        findNavController().navigate(
            R.id.action_artistDetailFragment_to_movieDetailFragment,
            movieDataBundle, null, null
        )
    }

    override fun onArtistImageClick(v: View) {
        Log.v(TAG, getString(R.string.imageClicked))
    }

}


