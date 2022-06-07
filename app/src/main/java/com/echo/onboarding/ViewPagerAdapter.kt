package com.echo.onboarding

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int) = OnBoardingScreen.newInstance(position)
}
