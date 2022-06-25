package com.echo.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.echo.R
import com.echo.common.base.utils.navigation.NavigationCommand
import com.echo.common.base.utils.navigation.observeNonNull
import com.echo.common.base.utils.observeEvent
import com.echo.common.base.utils.showDialog


abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel?> : Fragment() {
    protected abstract val viewModel: VM

    private var _binding: VB? = null

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation()
        viewModel?.loading?.observe(viewLifecycleOwner) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        viewModel?.dialog?.observeEvent(lifecycleOwner = viewLifecycleOwner) {
            if (!it.message.isNullOrEmpty()) {
                showDialog(it.title, it.message)
                return@observeEvent
            }
            if (it.messageRes != null) {
                showDialog(it.title, resources.getString(it.messageRes))
            } else {
                showDialog(it.title, resources.getString(R.string.common_unknown_error))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    open fun showLoading() {}

    open fun hideLoading() {}

    private fun observeNavigation() {
        viewModel?.navigation?.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigationCommand ->
                handleNavigation(navigationCommand)
            }
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions)
            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }
}
