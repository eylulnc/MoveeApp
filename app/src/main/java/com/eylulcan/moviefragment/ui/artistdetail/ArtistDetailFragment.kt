package com.eylulcan.moviefragment.ui.artistdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.BottomSheetFragmentBinding
import com.eylulcan.moviefragment.databinding.FragmentArtistDetailBinding
import com.eylulcan.moviefragment.model.ArtistDetail
import com.eylulcan.moviefragment.model.ProfileImage
import com.eylulcan.moviefragment.util.Utils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import me.samlss.broccoli.Broccoli

private const val SPAN_COUNT = 3

class ArtistDetailFragment : Fragment(), ItemListener {

    private lateinit var binding: FragmentArtistDetailBinding
    private val artistDetailViewModel: ArtistDetailViewModel by activityViewModels()
    private val placeholderNeeded = arrayListOf<View>()
    private var broccoli = Broccoli()
    private lateinit var includeBinding: BottomSheetFragmentBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var artistMovieAdapter: ArtistMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_artist_detail, container, false)
        binding = FragmentArtistDetailBinding.bind(view)
        includeBinding = binding.bottomSheetFragment
        return view
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
        artistDetailViewModel.artistDetail.observe(viewLifecycleOwner, { detail ->
            setupUIBottomSheet(detail)
            removePlaceholders()
            binding.artistName.text = detail.name
            val knownForDepartment = detail.knownForDepartment ?: getString(R.string.unknown)
            binding.knownWithText.text = knownForDepartment
            binding.artistDetailCoverImage.let {
                Glide.with(this).load(setImageUrl(detail.profilePath)).circleCrop()
                    .placeholder(R.color.grey_light)
                    .into(it)
            }
        })

        artistDetailViewModel.artistAlbum.observe(viewLifecycleOwner, { album ->
            val albumSize = album.artistProfileImages?.size ?: 0
            includeBinding.albumPreviewRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            if (albumSize > 5) {
                val albumTemp: List<ProfileImage>? = album.artistProfileImages?.subList(0, 4)
                includeBinding.albumPreviewRecyclerView.adapter = albumTemp?.let {
                    AlbumRecyclerAdapter(it)
                }
            } else {
                includeBinding.albumPreviewRecyclerView.adapter =
                    album.artistProfileImages?.let { AlbumRecyclerAdapter(it) }
            }
            binding.bottomSheetFragment.photosTextView.setOnClickListener {
                val albumDataBundle = bundleOf((getString(R.string.photo_album) to album))
                this.parentFragment?.findNavController()
                    ?.navigate(R.id.action_artistDetailFragment_to_albumFragment, albumDataBundle)
            }
        })

        artistDetailViewModel.artistMovieCredits.observe(viewLifecycleOwner, { movieCredits ->
            if(Utils.isTablet(requireContext())){
                binding.artistMovieRecycler.layoutManager = GridLayoutManager(context, SPAN_COUNT)
            } else {
                binding.artistMovieRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            }

            artistMovieAdapter = ArtistMovieAdapter(movieCredits, artistMovieClickListener = this)
            binding.artistMovieRecycler.adapter = artistMovieAdapter

        })

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
    }

    private fun setupUIBottomSheet(detail: ArtistDetail) {
        includeBinding.expandTextView.text = detail.biography
        includeBinding.artistBirthdayText.text = detail.birthday
        if (detail.deathday.isNullOrEmpty()) {
            includeBinding.artistDeathDayText.isVisible = false
            includeBinding.deathText.isVisible = false
        } else {
            includeBinding.artistDeathDayText.text = detail.deathday.toString()
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

}


