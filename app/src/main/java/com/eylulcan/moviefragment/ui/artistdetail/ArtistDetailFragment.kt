package com.eylulcan.moviefragment.ui.artistdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentArtistDetailBinding
import com.eylulcan.moviefragment.model.ArtistAlbum
import com.eylulcan.moviefragment.util.Utils
import com.google.android.material.tabs.TabLayoutMediator

class ArtistDetailFragment : Fragment() {

    private lateinit var binding: FragmentArtistDetailBinding
    private val artistDetailViewModel: ArtistDetailViewModel by activityViewModels()
    private val tabNames = arrayOf("Summary","Movies", "More")
    private var photoAlbum: ArtistAlbum? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_artist_detail, container, false)
        binding = FragmentArtistDetailBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedPopularPersonID = arguments?.get(getString(R.string.artistId)) as Int
        tabAdapterSetup()
        observeViewModel()
        artistDetailViewModel.getArtistAlbum(selectedPopularPersonID)
        artistDetailViewModel.getArtistDetail(selectedPopularPersonID)
        artistDetailViewModel.getArtistMovieCredits(selectedPopularPersonID)
        binding.albumCoverLayout.setOnClickListener {
            photoAlbum?.let { album ->
                val albumDataBundle = bundleOf((getString(R.string.photo_album) to album))
                findNavController().navigate(
                    R.id.action_artistDetailFragment_to_albumFragment,
                    albumDataBundle
                )
            }
        }
    }

    private fun observeViewModel() {
        artistDetailViewModel.artistDetail.observe(viewLifecycleOwner, { detail ->
            binding.artistName.text = detail.name
            val knownForDepartment = detail.knownForDepartment ?: getString(R.string.unknown)
            val birthDate = detail.birthday ?: getString(R.string.unknown)
            binding.artistShortInfo.text = knownForDepartment.plus(" | ").plus(birthDate)
            Glide.with(this).load(setImageUrl(detail.profilePath)).placeholder(R.color.greylight)
                .into(binding.artistDetailCoverImage)
        })

        artistDetailViewModel.artistAlbum.observe(viewLifecycleOwner, { album ->
            val albumSize = album.artistProfileImages?.size
            photoAlbum = album
            val profileImage = album.artistProfileImages

            binding.albumSizeText.text = albumSize.toString()
            albumSize?.let { size ->
                if (size > 0) {
                    for (i in 0..5) {
                        val imagePath = profileImage?.getOrNull(i)?.filePath
                        when (i) {
                            0 -> Glide.with(this).load(setImageUrl(imagePath))
                                .into(binding.albumPreviewElement1)
                            1 -> Glide.with(this).load(setImageUrl(imagePath))
                                .into(binding.albumPreviewElement2)
                            2 -> Glide.with(this).load(setImageUrl(imagePath))
                                .into(binding.albumPreviewElement3)
                            3 -> Glide.with(this).load(setImageUrl(imagePath))
                                .into(binding.albumPreviewElement4)
                            4 -> Glide.with(this).load(setImageUrl(imagePath))
                                .into(binding.albumPreviewElement5)
                        }
                    }
                }
            }
        })
    }

    private fun setImageUrl(profile_path: String?): String =
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(profile_path)

    private fun tabAdapterSetup() {
        val adapter = TabAdapter(childFragmentManager, lifecycle)
        binding.artistsFragmentViewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.artistsFragmentViewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

}

