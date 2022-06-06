package com.echo.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.echo.databinding.OnboardingScreen1Binding

class OnboardingScreenOne : Fragment() {
    lateinit var binding: OnboardingScreen1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingScreen1Binding.inflate(layoutInflater)

        return binding.root

    }
}