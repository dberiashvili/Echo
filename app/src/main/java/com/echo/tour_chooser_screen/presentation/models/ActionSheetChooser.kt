package com.echo.tour_chooser_screen.presentation.models

sealed class ActionSheetChooser {
    object CatholicActionSheet: ActionSheetChooser()
    object HebrewActionSheet: ActionSheetChooser()
}
