package com.echo.tour_chooser_screen.presentation

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.echo.R
import com.echo.common.base.BaseFragment
import com.echo.common.base.utils.permissions.PermissionManager
import com.echo.common.base.utils.showDialog
import com.echo.common.base.utils.showGrantedToast
import com.echo.common.base.utils.showPermanentlyDeniedDialog
import com.echo.common.base.utils.showRationaleDialog
import com.echo.databinding.BottomSheetBinding
import com.echo.databinding.TourChooserScreenBinding
import com.echo.tour_chooser_screen.presentation.models.ActionSheetChooser
import com.fondesa.kpermissions.PermissionStatus
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.anyPermanentlyDenied
import com.fondesa.kpermissions.anyShouldShowRationale
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TourChooserScreen : BaseFragment<TourChooserScreenBinding, TourChooserViewModel>(),
    PermissionRequest.Listener {
    @Inject
    lateinit var permissionManager: PermissionManager
    override val viewModel: TourChooserViewModel by viewModels()
    private val request by lazy {
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).build()
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): TourChooserScreenBinding {
        return TourChooserScreenBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        request.addListener(this)
        request.send()




        binding.catholicContainer.setOnClickListener {
            viewModel.openActionSheet(1)
        }

        binding.hebrewContainer.setOnClickListener {
            viewModel.openActionSheet(2)
        }


        viewModel.actionSheetValue.observe(viewLifecycleOwner) {
            when (it) {
                is ActionSheetChooser.HebrewActionSheet -> showBottomSheet(
                    requireContext(),
                    R.string.tour_chooser_hebrew_tour,
                    R.string.bottom_sheet_hebrew_description,
                    R.drawable.ic_hebrew,
                    R.drawable.next_btn_bg_secondary
                )
                is ActionSheetChooser.CatholicActionSheet -> {
                    showBottomSheet(
                        requireContext(),
                        R.string.tour_chooser_catholic_tour,
                        R.string.bottom_sheet_catholic_description,
                        R.drawable.ic_catholic,
                        R.drawable.next_btn_bg
                    )

                }
            }
        }
    }

    private fun showBottomSheet(
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
        binding.startButton.setOnClickListener {
            request.send()
            if (permissionManager.checkPermission()){
                viewModel.navigateToMapScreen()
            }
            bottomSheetDialog.dismiss()


        }
        binding.dismissButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.apply {
            setContentView(binding.root)
            show()
        }
    }

    override fun onPermissionsResult(result: List<PermissionStatus>) {
        val context = requireContext()
        when {
            result.anyPermanentlyDenied() -> context.showPermanentlyDeniedDialog(result)
            result.anyShouldShowRationale() -> context.showRationaleDialog(result, request)
            result.allGranted() -> {}
        }
    }


}
