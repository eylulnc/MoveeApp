package com.eylulcan.moviefragment

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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {

    companion object {
        private const val TAG:String = "GoogleActivity"
        private const val RC_SIGN_IN:Int = 7777
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: FragmentLoginBinding
    private var email:String? = null
    private var password:String? = null
    private lateinit var googleSignInClient:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.signInButton.setOnClickListener { signIn() }
        binding.signUpButton.setOnClickListener { signUp() }
        binding.googleSignInButton.setOnClickListener{ googleSignIn() }
    }

    private fun signIn(){
        email = binding.userEmail.text.toString()
        password = binding.userPassword.text.toString()
        if(!email.equals("") && !password.equals("")) {
            auth.signInWithEmailAndPassword(email!!, password!!)
                .addOnSuccessListener {
                    findNavController().navigate(
                        R.id.action_loginFragment_to_movieListFragment,
                        null, NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(context,"No empty field allowed please fill all the areas" , Toast.LENGTH_LONG).show()
        }
    }

    private fun signUp(){
        email = binding.userEmail.text.toString()
        password = binding.userPassword.text.toString()
        if(!email.equals("") && !password.equals("")) {
            auth.createUserWithEmailAndPassword(email!!, password!!)
                .addOnSuccessListener {
                    val userMap = hashMapOf<String,Any>()
                    userMap["User Email"] = email!!
                    firestore.collection("Users").add(userMap).addOnSuccessListener {
                        findNavController().navigate(
                            R.id.action_loginFragment_to_movieListFragment,
                            null, NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())
                    }
                    Toast.makeText(context,"User Created You Automatically Logged In" , Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(context,"No empty field allowed please fill all the areas" , Toast.LENGTH_LONG).show()
        }
    }

    private fun googleSignIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("SignInGoogle", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("SignInGoogle", "Google sign in failed", task.exception)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d( "SignInGoogle", "signInWithCredential:success")
                findNavController().navigate(
                    R.id.action_loginFragment_to_movieListFragment,
                    null, NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())
            } else {
                // If sign in fails, display a message to the user.
                Log.w("SignInGoogle", "signInWithCredential:failure", task.exception)
            }
        }
    }
}
