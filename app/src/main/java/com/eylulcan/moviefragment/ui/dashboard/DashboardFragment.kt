package com.eylulcan.moviefragment.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding = FragmentDashboardBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController(requireActivity(), R.id.fragmentDashboard)
        AppBarConfiguration(
            setOf(
                R.id.discoverFragment,
                R.id.genresFragment,
                R.id.artistsFragment,
                R.id.searchFragment
            )
        )
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}