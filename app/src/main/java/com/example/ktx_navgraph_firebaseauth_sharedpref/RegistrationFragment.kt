package com.example.ktx_navgraph_firebaseauth_sharedpref

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val etEmailReg: EditText by lazy { requireActivity().findViewById(R.id.etEmailReg) }
    private val etPasswordReg: EditText by lazy { requireActivity().findViewById(R.id.etPasswordReg) }
    private val btnRegistration: Button by lazy { requireActivity().findViewById(R.id.btnRegistration) }

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        btnRegistration.setOnClickListener { authFireBaseRegistration() }

    }

    fun authFireBaseRegistration() {

        if (etEmailReg.text.toString().isEmpty() || etPasswordReg.text.toString().isEmpty()) {
            toastRegistration("email or password field is empty")
        }
        else {
            Log.d("Debug", "click btnRegistration -> else ->")
            auth.createUserWithEmailAndPassword(
                etEmailReg.text.toString(),
                etPasswordReg.text.toString()
            ).addOnCompleteListener(this@RegistrationFragment as Activity) { task ->
                Log.d("Debug", "click btnRegistration -> else -> task ->")
                if (task.isSuccessful) {
                    Log.d("Debug", "click btnRegistration -> else -> task -> success")
                    toastRegistration("Registration success")
                    // -> Congratul
                } else {
                    toastRegistration("Registration failed")
                }
            }
        }

    }

    fun toastRegistration(text: String) {
        Toast.makeText(this@RegistrationFragment as Activity, "$text", Toast.LENGTH_LONG).show()
    }


}