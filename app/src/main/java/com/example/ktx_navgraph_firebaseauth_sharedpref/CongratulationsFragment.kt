package com.example.ktx_navgraph_firebaseauth_sharedpref

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class CongratulationsFragment : Fragment(R.layout.fragment_congratulations) {

    private val navView: NavigationView by lazy { requireActivity().findViewById(R.id.navView) }
    private val drawer: DrawerLayout by lazy { requireActivity().findViewById(R.id.drawer) }
    private val wvWeb: WebView by lazy { requireActivity().findViewById(R.id.wvWeb) }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawer.openDrawer(GravityCompat.START)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.google -> {
                    webViewStart(GOOGLE_URL)
                    toastWebView("Google")
                }
                R.id.youtube -> {
                    webViewStart(YouTube_URL)
                    toastWebView("YouTube")
                }
                R.id.stackoverflow -> {
                    webViewStart(StackOverFlow_URL)
                    toastWebView("StackOverFlow")
                }
                R.id.github -> {
                    webViewStart(GITHUB_URL)
                    toastWebView("Github")
                }
                R.id.logout -> {
                    logout()
                }
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (wvWeb.canGoBack()) wvWeb.goBack() else {
                findNavController().popBackStack(R.id.loginFragment, false)
            }
        }

    }

    fun toastWebView(text: String) {
        Toast.makeText(
            this@CongratulationsFragment as Activity,
            "Загружается сайт $text",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun webViewStart(url: String) {
        wvWeb.webViewClient = WebViewClient()

        wvWeb.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
        }
    }

    fun logout() {
        auth.signOut()
        Toast.makeText(
            this@CongratulationsFragment as Activity,
            "Logout is success",
            Toast.LENGTH_LONG
        )
            .show()
        findNavController().navigate(
            CongratulationsFragmentDirections
                .actionCongratulationsFragmentToLoginFragment()
        )
        findNavController().popBackStack()
    }


}