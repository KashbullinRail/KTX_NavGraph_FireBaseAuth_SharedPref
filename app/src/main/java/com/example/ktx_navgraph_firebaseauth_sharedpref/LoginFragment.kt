package com.example.ktx_navgraph_firebaseauth_sharedpref

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth

    private val etEmail: EditText by lazy { requireActivity().findViewById(R.id.etEmail) }
    private val etPassword: EditText by lazy { requireActivity().findViewById(R.id.etPassword) }
    private val btnLogin: Button by lazy { requireActivity().findViewById(R.id.btnLogin) }
    private val btnCreateAccount: Button by lazy { requireActivity().findViewById(R.id.btnCreateAccount) }
    private val btnNoLogin: Button by lazy { requireActivity().findViewById(R.id.btnNoLogin) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        btnCreateAccount.setOnClickListener {  }

        btnNoLogin.setOnClickListener {  }

        btnLogin.setOnClickListener { authFireBase() }

    }


    fun authFireBase() {

        if (etEmail.text.toString().isEmpty() || etPassword.text.toString().isEmpty()) {
            toastLogin("email or password field is empty")
        }
        else {
            Log.d("Debug", "click btnLogin -> else ->")
            auth.signInWithEmailAndPassword(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
                .addOnCompleteListener(this@LoginFragment as Activity) { task ->
                    Log.d("Debug", "click btnLogin -> else -> task ->")
                    if (task.isSuccessful) {
                        toastLogin("Authentication success")
                        //GOTO CongratulationsFrag
                    } else {
                        toastLogin("Authentication failed")
                    }
                }
        }

    }

    fun toastLogin(text: String) {
        Toast.makeText(this@LoginFragment as Activity, "$text", Toast.LENGTH_LONG).show()
    }


}