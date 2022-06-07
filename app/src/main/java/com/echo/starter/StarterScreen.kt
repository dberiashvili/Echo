package com.echo.starter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.echo.R
import com.echo.common.Constants
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
            findNavController().navigate(StarterScreenDirections.actionStarterScreenToOnboardingViewPagerScreen())
        }
        return binding.root
    }
}