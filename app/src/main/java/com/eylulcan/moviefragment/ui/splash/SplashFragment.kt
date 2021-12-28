package com.eylulcan.moviefragment.ui.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashFragment : Fragment() {

    private val auth: FirebaseAuth = Firebase.auth
    private lateinit var binding: FragmentSplashBinding
    lateinit var sharedPreferences: SharedPreferences
    private var firstTimeOpened: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.app_package_name),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        val currentUser = auth.currentUser
        currentUser?.let {
            navigateToMovieList()
        } ?: run {
            firstTimeOpened = sharedPreferences.getBoolean(getString(R.string.isFirst), true)
            if (firstTimeOpened == true) {
                sharedPreferences.edit().putBoolean(getString(R.string.isFirst), false).apply()
                navigateToOnboard()
            } else {
                navigateToLogin()
            }
        }
    }

    private fun navigateToMovieList() {
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController()
                .navigate(
                    R.id.action_splashFragment_to_dashboardFragment, null,
                    NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build()
                )
        }, 1500)
    }

    private fun navigateToLogin() {
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController()
                .navigate(
                    R.id.action_splashFragment_to_loginFragment, null,
                    NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build(), null
                )
        }, 1500)
    }

    private fun navigateToOnboard() {
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController()
                .navigate(
                    R.id.action_splashFragment_to_onBoardViewPagerFragment, null,
                    NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build()
                )
        }, 1500)
    }
}