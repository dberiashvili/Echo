package com.echo.map.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.echo.common.base.BaseViewModel
import com.echo.map.utils.MapUtil
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(): BaseViewModel() {
    private val _distance: MutableLiveData<String> = MutableLiveData()
    val distance: LiveData<String> = _distance
    fun calculateDistance(
        from: LatLng, to: LatLng
    ) {
        _distance.postValue(MapUtil.calculateDistance(from, to))
    }

}