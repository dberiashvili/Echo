package com.echo.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.echo.R
import com.echo.databinding.OnboardingViewPagerScreenBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingViewPagerScreen: Fragment(){
    lateinit var binding: OnboardingViewPagerScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingViewPagerScreenBinding.inflate(layoutInflater)
        val pagerAdapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
        TabLayoutMediator(binding.intoTabLayout, binding.introPager)
        { tab, position ->}.attach()
        return binding.root
    }
}