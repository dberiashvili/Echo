package com.echo.map.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.echo.common.base.BaseFragment
import com.echo.databinding.MapScreenBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapScreen : BaseFragment<MapScreenBinding, MapViewModel>() {
    override val viewModel: MapViewModel by viewModels()
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MapScreenBinding {
        return MapScreenBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}