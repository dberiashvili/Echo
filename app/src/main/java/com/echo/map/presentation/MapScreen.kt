package com.echo.map.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import com.echo.common.base.BaseFragment
import com.echo.databinding.MapScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MinimapOverlay
import org.osmdroid.views.overlay.TilesOverlay


@AndroidEntryPoint
class MapScreen : BaseFragment<MapScreenBinding, MapViewModel>() {
    lateinit var mapView: MapView

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