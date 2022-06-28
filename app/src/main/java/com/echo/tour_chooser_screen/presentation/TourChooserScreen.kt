package com.echo.tour_chooser_screen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.echo.common.base.BaseFragment
import com.echo.databinding.TourChooserScreenBinding
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
    }
}