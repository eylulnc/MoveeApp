package com.eylulcan.moviefragment.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentLoginBinding
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

private const val TAG: String = "SignInGoogle"
private const val RC_SIGN_IN: Int = 7777
private const val EMPTY_STR = ""

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleClientGetter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(view, 1F)
        observeViewModel()
        binding.signInButton.setOnClickListener { signIn() }
        binding.signUpButton.setOnClickListener { signUp() }
        binding.googleSignInButton.setOnClickListener { googleSignIn() }
    }

    private fun observeViewModel() {
        loginViewModel.signInResults.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Success -> {
                    navigateToMovieList()
                }
                is ResultData.Failed -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
        loginViewModel.signUpResults.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Success -> {
                    Toast.makeText(context, R.string.user_created, Toast.LENGTH_LONG).show()
                    navigateToMovieList()
                }
                is ResultData.Failed -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
        loginViewModel.googleSignInResults.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Success -> {
                    Log.d(TAG, getString(R.string.login_log_message_sign_in_success))
                    val googleAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
                    Toast.makeText(context, R.string.user_created, Toast.LENGTH_LONG).show()
                    navigateToMovieList()
                }
                is ResultData.Failed -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }

    private fun signIn() {
        email = binding.userEmail.text.toString()
        password = binding.userPassword.text.toString()
        if (email != EMPTY_STR && password != EMPTY_STR) {
            loginViewModel.signIn(email, password)
        } else {
            Toast.makeText(context, R.string.empty_field, Toast.LENGTH_LONG).show()
        }
    }

    private fun signUp() {
        email = binding.userEmail.text.toString()
        password = binding.userPassword.text.toString()
        if (email != EMPTY_STR && password != EMPTY_STR) {
            loginViewModel.signUp(email, password)
        } else {
            Toast.makeText(context, R.string.empty_field, Toast.LENGTH_LONG).show()
        }
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, getString(R.string.login_log_message_firebase_auth) + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, getString(R.string.login_log_message_failed), task.exception)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        loginViewModel.signInGoogle(credential)
    }

    private fun navigateToMovieList() {
        findNavController().navigate(
            R.id.action_loginFragment_to_dashboardFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
        )
    }

    private fun googleClientGetter() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

}
