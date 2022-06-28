package com.echo.app

import com.echo.common.base.utils.keyboard.HeightProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.echo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var keyboardHeight: Int
        HeightProvider(this).init().setHeightListener(object : HeightProvider.HeightListener {
            override fun onHeightChanged(height: Int) {
                keyboardHeight = height
                if (height> 0 ){
                    binding.root.animate().translationY(-keyboardHeight.toFloat())
                } else {
                    binding.root.animate().translationY(keyboardHeight.toFloat())
                }

            }
        })



    }
}