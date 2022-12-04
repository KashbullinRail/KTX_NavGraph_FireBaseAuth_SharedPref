package com.example.ktx_navgraph_firebaseauth_sharedpref

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class CongratulationsFragment : Fragment(R.layout.fragment_congratulations) {

    private val navView: NavigationView by lazy { requireActivity().findViewById(R.id.navView) }
    private val drawer: DrawerLayout by lazy { requireActivity().findViewById(R.id.drawer) }
    private val wvWeb: WebView by lazy { requireActivity().findViewById(R.id.wvWeb) }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawer.openDrawer(GravityCompat.START)

        fun snakbar(id: Int) {
            Snackbar.make(view, requireActivity().getString(id),
                Snackbar.LENGTH_LONG).show()
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.google -> {
                    webViewStart(GOOGLE_URL)
                    snakbar(R.string.google)
                }
                R.id.youtube -> {
                    webViewStart(YouTube_URL)
                    snakbar(R.string.youtube)
                }
                R.id.stackoverflow -> {
                    webViewStart(StackOverFlow_URL)
                    snakbar(R.string.stackoverflow)
                }
                R.id.github -> {
                    webViewStart(GITHUB_URL)
                    snakbar(R.string.github)
                }
                R.id.logout -> {
                    snakbar(R.string.logout_success)
                    logout()
                }
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (wvWeb.canGoBack()) wvWeb.goBack() else {
                findNavController().popBackStack()
            }
        }

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
        AuthUI.getInstance().signOut(requireContext())
        findNavController().popBackStack()
    }

}