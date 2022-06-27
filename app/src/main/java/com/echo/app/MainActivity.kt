package com.echo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.echo.common.base.utils.keyboard.HeightProvider
import com.echo.common.base.utils.keyboard.HeightProvider.HeightListener
import com.echo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}