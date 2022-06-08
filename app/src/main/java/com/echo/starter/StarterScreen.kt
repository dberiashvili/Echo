package com.echo.starter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.echo.common.Constants
import com.echo.common.Constants.ONBOARDING_PREF
import com.echo.databinding.StarterScreenBinding
import kotlinx.coroutines.delay

class StarterScreen : Fragment() {
    lateinit var binding: StarterScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StarterScreenBinding.inflate(layoutInflater)
        lifecycleScope.launchWhenStarted {
            delay(Constants.SPLASH_DELAY)
            if (getPrefs() == true) {
                findNavController().navigate(StarterScreenDirections.actionStarterScreenToLoginScreen())
            } else {
                findNavController().navigate(StarterScreenDirections.actionStarterScreenToOnboardingViewPagerScreen())
            }

        }
        return binding.root
    }

    private fun getPrefs(): Boolean? {
        val sharedPref = activity?.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref?.getBoolean(ONBOARDING_PREF, false)
    }
}