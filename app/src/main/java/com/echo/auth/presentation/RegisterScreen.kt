package com.echo.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.echo.databinding.RegisterScreenBinding


class RegisterScreen : Fragment() {
    private lateinit var binding: RegisterScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegisterScreenBinding.inflate(layoutInflater)
        return binding.root
    }
}