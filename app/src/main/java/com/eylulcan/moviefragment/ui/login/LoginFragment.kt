package com.eylulcan.moviefragment.ui.login

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.eylulcan.moviefragment.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.util.Log
import com.eylulcan.moviefragment.R
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

private const val TAG: String = "SignInGoogle"
private const val RC_SIGN_IN: Int = 7777

class LoginFragment : Fragment() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var binding: FragmentLoginBinding
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
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.signInButton.setOnClickListener { signIn() }
        binding.signUpButton.setOnClickListener { signUp() }
        binding.googleSignInButton.setOnClickListener { googleSignIn() }
    }

    private fun signIn() {
        email = binding.userEmail.text.toString()
        password = binding.userPassword.text.toString()
        if (email != "" && password != "") {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { navigateToMovieList() }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(context, R.string.empty_field, Toast.LENGTH_LONG).show()
        }
    }

    private fun signUp() {
        email = binding.userEmail.text.toString()
        password = binding.userPassword.text.toString()
        if (email != "" && password != "") {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val userMap = hashMapOf<String, Any>()
                    userMap[getString(R.string.user_email)] = email
                    firestore.collection(getString(R.string.users)).add(userMap)
                        .addOnSuccessListener { navigateToMovieList() }
                    Toast.makeText(context, R.string.user_created, Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
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
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, getString(R.string.login_log_message_sign_in_success))
                val googleAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
                googleAccount?.let {
                    val userMap = hashMapOf<String, Any>()
                    userMap[getString(R.string.user_email)] = googleAccount.email as String
                    firestore.collection(getString(R.string.users)).add(userMap)
                        .addOnSuccessListener { navigateToMovieList() }
                    Toast.makeText(context, R.string.user_created, Toast.LENGTH_LONG).show()
                }
            } else {
                Log.w(TAG, getString(R.string.login_log_message_sign_in_fail), task.exception)
            }
        }
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
