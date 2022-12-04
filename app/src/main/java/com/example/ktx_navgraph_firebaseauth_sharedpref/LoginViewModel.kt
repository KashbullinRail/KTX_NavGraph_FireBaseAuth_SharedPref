package com.example.ktx_navgraph_firebaseauth_sharedpref

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class LoginViewModel: ViewModel() {


    enum class AuthenticationState {
        INVALID_AUTHENTICATION, AUTHENTICATED, UNAUTHENTICATED
    }

    val authenticationState = FireBaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

}