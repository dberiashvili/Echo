package com.echo.map.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Route
import com.akexorcist.googledirection.util.DirectionConverter
import com.akexorcist.googledirection.util.execute
import com.echo.R
import com.echo.common.Constants.ACTION_START_FOREGROUND_SERVICE
import com.echo.common.Constants.ACTION_STOP_FOREGROUND_SERVICE
import com.echo.common.Constants.GOOGLE_API_KEY
import com.echo.common.base.BaseFragment
import com.echo.common.base.utils.permissions.PermissionManager
import com.echo.common.base.utils.showPermanentlyDeniedDialog
import com.echo.common.base.utils.showRationaleDialog
import com.echo.databinding.MapScreenBinding
import com.echo.map.service.LocationService
import com.echo.map.service.LocationService.Companion.locationList
import com.echo.map.utils.MapUtil.setCameraPosition
import com.fondesa.kpermissions.PermissionStatus
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.anyPermanentlyDenied
import com.fondesa.kpermissions.anyShouldShowRationale
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapScreen : BaseFragment<MapScreenBinding, MapViewModel>(), PermissionRequest.Listener,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMarkerClickListener {
    private var locations = mutableListOf<LatLng>()

    @Inject
    lateinit var permissionManager: PermissionManager
    private lateinit var map: GoogleMap
    private var markerList = mutableListOf<Marker>()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val request by lazy {
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).build()
    }
    override val viewModel: MapViewModel by viewModels()

    @SuppressLint("MissingPermission", "PotentialBehaviorOverride")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        if (permissionManager.checkPermission()) {
            map.isMyLocationEnabled = true
        } else {
            request.send()
        }

        viewModel.distance.observe(viewLifecycleOwner) {
            binding.distanceLeftValue.text = "$it კმ"
        }

        map.setOnMyLocationButtonClickListener(this)
        map.setOnMarkerClickListener(this)
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        it.result.latitude,
                        it.result.longitude
                    ), 15F
                )
            )
        }
        observeLocations()

    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MapScreenBinding {
        return MapScreenBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendActionCommandToService(ACTION_START_FOREGROUND_SERVICE)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

    }

    private fun observeLocations() {
        locationList.observe(viewLifecycleOwner) {
            locations = it
            drawPolyline()

        }



    }


    private fun followPolyline() {
        if (locations.isNotEmpty()) {
            map.animateCamera(
                CameraUpdateFactory.newCameraPosition(
                    setCameraPosition(locations.last())
                ), 1000, null
            )
        }
    }

    private fun drawPolyline() {
        if (locations.isNotEmpty()){
            GoogleDirection.withServerKey(GOOGLE_API_KEY)
                .from(locations.last())
                .to(LatLng(41.57213517, 45.87276793))
                .transportMode(TransportMode.WALKING)
                .execute(
                    onDirectionSuccess = { direction -> onDirectionSuccess(direction, context!!) },
                    onDirectionFailure = { t -> onDirectionFailure(t) }
                )
        }

    }

    private fun addMarker(position: LatLng){
        val marker = map.addMarker(MarkerOptions().position(position))
        markerList.add(marker!!)
    }


    private fun onDirectionSuccess(direction: Direction?, context: Context) {
        direction?.let {
            if (direction.isOK) {
                val route = direction.routeList[0]
                val directionPositionList = route.legList[0].directionPoint
                map.addPolyline(
                    DirectionConverter.createPolyline(
                        context,
                        directionPositionList,
                        5,
                        Color.BLUE
                    )
                )
                setCameraWithCoordinationBounds(route)
            }
        }
        viewModel.calculateDistance(locations.first(),(LatLng(41.57213517, 45.87276793)))
    }

    private fun onDirectionFailure(t: Throwable) {

    }

    private fun setCameraWithCoordinationBounds(route: Route) {
        val southwest = route.bound.southwestCoordination.coordination
        val northeast = route.bound.northeastCoordination.coordination
        val bounds = LatLngBounds(southwest, northeast)
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }

    private fun sendActionCommandToService(action: String) {

        Intent(requireContext(), LocationService::class.java).apply {
            this.action = action
            requireContext().startService(this)
        }
    }

    override fun onPermissionsResult(result: List<PermissionStatus>) {
        val context = requireContext()
        when {
            result.anyPermanentlyDenied() -> context.showPermanentlyDeniedDialog(result)
            result.anyShouldShowRationale() -> context.showRationaleDialog(result, request)
            result.allGranted() -> {
                sendActionCommandToService(ACTION_START_FOREGROUND_SERVICE)
            }
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        map.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                setCameraPosition(locations.last())
            ), 1000, null
        )
        return true
    }


    override fun onMarkerClick(p0: Marker) = true

    override fun onDestroy() {
        super.onDestroy()
        sendActionCommandToService(ACTION_STOP_FOREGROUND_SERVICE)

    }
}
