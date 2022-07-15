package com.echo.map.utils

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import java.text.DecimalFormat

object MapUtil {

    fun setCameraPosition(location: LatLng): CameraPosition {
        return CameraPosition.Builder().target(location).zoom(18f)
            .build()
    }

    fun calculateDistance(from: LatLng, to: LatLng): String {

        val distance = SphericalUtil.computeDistanceBetween(from, to)
        return DecimalFormat("#.##").format(distance / 1000)


    }




}