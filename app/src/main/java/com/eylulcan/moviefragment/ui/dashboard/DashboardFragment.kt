package com.eylulcan.moviefragment.ui.dashboard

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
                R.id.artistsFragment
            )
        )
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            auth.signOut()
            Toast.makeText(context, R.string.logged_out_movie_list, Toast.LENGTH_LONG).show()
            findNavController().navigate(
                R.id.action_dashboardFragment_to_loginFragment, null,
                NavOptions.Builder().setPopUpTo(R.id.dashboardFragment, true).build()
            )
        }
        return super.onOptionsItemSelected(item)
    }

}