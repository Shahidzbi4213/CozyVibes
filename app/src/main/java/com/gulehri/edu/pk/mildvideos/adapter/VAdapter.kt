package com.gulehri.edu.pk.mildvideos.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gulehri.edu.pk.mildvideos.fragments.CategoryFragment
import com.gulehri.edu.pk.mildvideos.fragments.PopularFragment

/**
 * Created by Shahid Iqbal on 10,November,2021
 */
class VAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> CategoryFragment()
            0 -> PopularFragment()
            else -> PopularFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}