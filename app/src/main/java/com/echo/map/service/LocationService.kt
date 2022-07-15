package com.echo.map.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.echo.common.Constants.ACTION_START_FOREGROUND_SERVICE
import com.echo.common.Constants.ACTION_STOP_FOREGROUND_SERVICE
import com.echo.common.Constants.LOCATION_FASTEST_UPDATE_INTERVAL
import com.echo.common.Constants.LOCATION_UPDATE_INTERVAL
import com.echo.common.Constants.NOTIFICATION_CHANNEL_ID
import com.echo.common.Constants.NOTIFICATION_CHANNEL_NAME
import com.echo.common.Constants.NOTIFICATION_ID
import com.echo.map.utils.MapUtil.calculateDistance
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import com.google.maps.android.SphericalUtil
import javax.inject.Inject

@AndroidEntryPoint

class LocationService:LifecycleService() {

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var notification: NotificationCompat.Builder

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {

        val locationList = MutableLiveData<MutableList<LatLng>>()
    }


    private fun initValues() {
        locationList.postValue(mutableListOf())
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            val locations = result.locations
            for (location in locations) {
                updateLocationList(location)
                updateNotificationPeriodically()

            }
        }
    }


    private fun updateLocationList(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        locationList.value?.apply {
            add(latLng)
            locationList.postValue(this)
        }

    }

    private fun updateNotificationPeriodically() {
        notification.setContentTitle("Distance Travelled")
            .setContentText("km")
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

    override fun onCreate() {
        super.onCreate()
        initValues()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                ACTION_START_FOREGROUND_SERVICE -> {
                    startForegroundService()
                    startLocationUpdates()
                }
                ACTION_STOP_FOREGROUND_SERVICE -> {
                    stopForegroundService()

                }
                else -> {
                }
            }

        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun startForegroundService() {

        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notification.build())

    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {

        val locationRequest = LocationRequest.create().apply {
            interval = LOCATION_UPDATE_INTERVAL
            fastestInterval = LOCATION_FASTEST_UPDATE_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()

        )
    }

    private fun stopForegroundService() {
        removeLocationUpdates()
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(
            NOTIFICATION_ID
        )
        stopForeground(true)
        stopSelf()
    }

    private fun removeLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}

