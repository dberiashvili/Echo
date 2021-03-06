package com.echo.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.echo.R
import com.echo.auth.domain.RegisterModel
import com.echo.common.base.BaseFragment
import com.echo.common.base.utils.hide
import com.echo.common.base.utils.show
import com.echo.common.model.Resource
import com.echo.databinding.RegisterScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterScreen : BaseFragment<RegisterScreenBinding, RegisterViewModel>() {
    override val viewModel: RegisterViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkFields(
            listOf(
                binding.nameET,
                binding.lastNameET,
                binding.phoneET,
                binding.emailET,
                binding.schoolET,
                binding.gradeET
            ),
        )

        binding.emailET.doAfterTextChanged {
            viewModel.checkEmail(it.toString())
        }

        viewModel.isEmailValid.observe(viewLifecycleOwner) {
            if (!it) {
                binding.emailET.error = getString(R.string.email_error)
            }
        }


        viewModel.validateFields.observe(viewLifecycleOwner) {
            if (it) {
                binding.registerButton.isEnabled = true
                binding.registerButton.setBackgroundResource(R.drawable.next_btn_bg)
            } else {
                binding.registerButton.isEnabled = false
                binding.registerButton.setBackgroundResource(R.drawable.btn_disabled)
            }

        }

        binding.registerButton.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                viewModel.registerUser(
                    RegisterModel(
                        binding.nameET.text.toString(),
                        binding.lastNameET.text.toString(),
                        binding.phoneET.text.toString(),
                        binding.emailET.text.toString(),
                        binding.schoolET.text.toString(),
                        binding.gradeET.text.toString()
                    )
                )
            }


        }

        viewModel.authResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> viewModel.navigateToChooser()
                else -> {}
            }
        }

    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): RegisterScreenBinding {
        return RegisterScreenBinding.inflate(inflater, container, false)
    }


    override fun showLoading() {
        binding.progressBar.show()
    }

    override fun hideLoading() {
        binding.progressBar.hide()
    }


}