package com.echo.tour_chooser_screen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.echo.common.base.BaseViewModel
import com.echo.tour_chooser_screen.presentation.models.ActionSheetChooser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TourChooserViewModel @Inject constructor() : BaseViewModel() {
    private val _actionSheetValue: MutableLiveData<ActionSheetChooser> = MutableLiveData()
    val actionSheetValue: LiveData<ActionSheetChooser> = _actionSheetValue
    fun openActionSheet(id: Int) {
        when (id) {
            1 -> _actionSheetValue.value = ActionSheetChooser.CatholicActionSheet

            2 -> _actionSheetValue.value = ActionSheetChooser.HebrewActionSheet
        }
    }

}