package com.echo.presentation.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.echo.R
import com.echo.common.Constants.OnBoarding_LAST_FRAGMENT_INDEX
import com.echo.common.Constants.ONBOARDING_PREF
import com.echo.common.Constants.PREF_NAME
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
        binding.nextButton.setOnClickListener {
            changeFragment()
        }

        binding.skipTV.setOnClickListener {
            findNavController().navigate(OnBoardingViewPagerScreenDirections.actionOnboardingViewPagerScreenToLoginScreen())
            finishOnBoarding()
        }
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

    private fun changeFragment() {
        if (binding.introPager.currentItem < OnBoarding_LAST_FRAGMENT_INDEX) {
            binding.introPager.currentItem++
        } else {
            findNavController().navigate(OnBoardingViewPagerScreenDirections.actionOnboardingViewPagerScreenToLoginScreen())
            finishOnBoarding()

        }
    }

    private fun finishOnBoarding() {
        val sharedPref = activity?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putBoolean(ONBOARDING_PREF, true)
        editor?.apply()
    }
}