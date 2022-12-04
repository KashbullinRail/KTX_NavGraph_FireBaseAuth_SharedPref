package com.example.ktx_navgraph_firebaseauth_sharedpref

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FireBaseUserLiveData: LiveData<FirebaseUser?>() {


    private val fireBaseAuth = FirebaseAuth.getInstance()

    private val authStateListener  = FirebaseAuth.AuthStateListener {
        firebaseAuth -> value = firebaseAuth.currentUser
    }

    override fun onActive() {
        fireBaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onInactive() {
        fireBaseAuth.removeAuthStateListener(authStateListener)
    }
}