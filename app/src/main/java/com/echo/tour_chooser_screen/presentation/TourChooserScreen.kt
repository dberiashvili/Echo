package com.echo.tour_chooser_screen.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.echo.R
import com.echo.common.base.BaseFragment
import com.echo.databinding.BottomSheetBinding
import com.echo.databinding.TourChooserScreenBinding
import com.echo.tour_chooser_screen.presentation.models.ActionSheetChooser
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TourChooserScreen : BaseFragment<TourChooserScreenBinding, TourChooserViewModel>() {
    override val viewModel: TourChooserViewModel by viewModels()
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): TourChooserScreenBinding {
        return TourChooserScreenBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.catholicContainer.setOnClickListener {
            viewModel.openActionSheet(1)
        }

        binding.hebrewContainer.setOnClickListener {
            viewModel.openActionSheet(2)
        }

        viewModel.actionSheetValue.observe(viewLifecycleOwner) {
            when (it) {
                is ActionSheetChooser.HebrewActionSheet -> showCatholicBottomSheet(
                    requireContext(),
                    R.string.tour_chooser_hebrew_tour,
                    R.string.tour_chooser_description,
                    R.drawable.ic_hebrew,
                    R.drawable.next_btn_bg_secondary
                )
                is ActionSheetChooser.CatholicActionSheet -> {
                    showCatholicBottomSheet(
                        requireContext(),
                        R.string.tour_chooser_catholic_tour,
                        R.string.tour_chooser_description,
                        R.drawable.ic_catholic,
                        R.drawable.next_btn_bg
                    )

                }
            }
        }
    }

    private fun showCatholicBottomSheet(
        context: Context,
        @StringRes title: Int,
        @StringRes description: Int,
        @DrawableRes icon: Int,
        @DrawableRes button: Int
    ) {
        val inflater = LayoutInflater.from(context)
        val binding = BottomSheetBinding.inflate(inflater)
        val bottomSheetDialog = BottomSheetDialog(context)
        binding.title.text = getString(title)
        binding.tourDescription.text = getString(description)
        binding.tourIcon.setBackgroundResource(icon)
        binding.startButton.setImageResource(button)
        binding.dismissButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.apply {
            setContentView(binding.root)
            show()
        }
        bottomSheetDialog.show()
    }
}