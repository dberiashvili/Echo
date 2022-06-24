package com.echo.presentation.starter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.echo.common.Constants
import com.echo.common.Constants.ONBOARDING_PREF
import com.echo.common.base.BaseFragment
import com.echo.databinding.StarterScreenBinding
import kotlinx.coroutines.delay

class StarterScreen : BaseFragment<StarterScreenBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()

    private fun getPrefs(): Boolean? {
        val sharedPref = activity?.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref?.getBoolean(ONBOARDING_PREF, false)
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): StarterScreenBinding {
        return StarterScreenBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            delay(Constants.SPLASH_DELAY)
            if (getPrefs() == true) {
                findNavController().navigate(StarterScreenDirections.actionStarterScreenToLoginScreen())
            } else {
                findNavController().navigate(StarterScreenDirections.actionStarterScreenToOnboardingViewPagerScreen())
            }

        }
    }
}