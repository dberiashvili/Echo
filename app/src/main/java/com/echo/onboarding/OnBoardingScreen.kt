package com.echo.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.echo.R
import com.echo.databinding.OnboardingScreenBinding

class OnBoardingScreen : Fragment() {
    private lateinit var binding: OnboardingScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val position = arguments?.getInt(POS_ARG, 0)
        binding = OnboardingScreenBinding.inflate(layoutInflater)
        when (position) {
            0 -> {
                binding.onboardingIcon.setImageResource(R.drawable.ic_onboarding_flag)
                binding.onboardingTitle.text = context?.getText(R.string.onboarding_purpose_title)
                binding.onboardingDescription.text = context?.getText(R.string.onboarding_first_desc_text)
            }

            1 -> {
                binding.onboardingIcon.setImageResource(R.drawable.ic_onboarding_column)
                binding.onboardingTitle.text = context?.getText(R.string.onboarding_second_title_text)
                binding.onboardingDescription.text = context?.getText(R.string.onboarding_second_desc_text)
            }

            2 -> {
                binding.onboardingIcon.setImageResource(R.drawable.ic_onboarding_column)
                binding.onboardingTitle.text = context?.getText(R.string.onboarding_third_title_text)
                binding.onboardingDescription.text = context?.getText(R.string.onboarding_third_desc_text)
            }
        }

        return binding.root

    }

    companion object {
        private const val POS_ARG = "pos_arg"

        @JvmStatic
        fun newInstance(position: Int) = OnBoardingScreen().apply {
            arguments = Bundle().apply {
                putInt(POS_ARG, position)
            }
        }
    }
}