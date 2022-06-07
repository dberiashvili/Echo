package com.echo.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.echo.R
import com.echo.databinding.OnboardingViewPagerScreenBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingViewPagerScreen : Fragment() {
    private lateinit var binding: OnboardingViewPagerScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingViewPagerScreenBinding.inflate(layoutInflater)
        val pagerAdapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
        binding.introPager.adapter = pagerAdapter

        TabLayoutMediator(binding.introTabLayout, binding.introPager)
        { _, _ -> }.attach()
        binding.introPager.registerOnPageChangeCallback(PagerSwipeAction())
        return binding.root
    }

    private inner class PagerSwipeAction : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                0 -> {
                    binding.nextButtonText.text = context?.getString(R.string.intro_next)
                }
                1 -> {
                    binding.nextButtonText.text = context?.getString(R.string.intro_next)
                }

                2 -> {
                    binding.nextButtonText.text = context?.getString(R.string.intro_start)
                }
            }
        }
    }
}