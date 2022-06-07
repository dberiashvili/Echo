package com.echo.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.echo.databinding.OnboardingScreen1Binding

class OnBoardingScreen : Fragment() {
    private lateinit var binding: OnboardingScreen1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val position = arguments?.getInt(POS_ARG, 0)
        binding = OnboardingScreen1Binding.inflate(layoutInflater)
        when (position) {
            0 -> {
                binding.txt.text = "1"
            }

            1 -> {
                binding.txt.text = "2"
            }

            2 -> {
                binding.txt.text = "3"
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