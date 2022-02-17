package com.eylulcan.moviefragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.eylulcan.moviefragment.databinding.ActivityMainBinding
import com.eylulcan.moviefragment.ui.MovieFragmentFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth: FirebaseAuth = Firebase.auth
    lateinit var sharedPreferences: SharedPreferences
    private var firstTimeOpened: Boolean? = null
    private lateinit var navGraph: NavGraph
    private lateinit var navController: NavController

    @Inject
    lateinit var fragmentFactory: MovieFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashScreen)
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.app_package_name),
            Context.MODE_PRIVATE
        )
        setUpUI()
    }

    private fun setUpUI() {
        val navHostFragment =
            binding.fragmentContainerView.getFragment<Fragment>() as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        navGraph = graphInflater.inflate(R.navigation.navigation_graph)
        navController = navHostFragment.navController
        val currentUser = auth.currentUser
        currentUser?.let {
            navGraph.setStartDestination(R.id.dashboardFragment)
        } ?: run {
            firstTimeOpened = sharedPreferences.getBoolean(getString(R.string.isFirst), true)
            if (firstTimeOpened == true) {
                sharedPreferences.edit().putBoolean(getString(R.string.isFirst), false).apply()
                navGraph.setStartDestination(R.id.onBoardViewPagerFragment)
            } else {
                navGraph.setStartDestination(R.id.loginFragment)
            }
            navController.graph = navGraph
        }
    }

}