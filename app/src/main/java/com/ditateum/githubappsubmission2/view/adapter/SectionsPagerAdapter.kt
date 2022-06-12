package com.ditateum.githubappsubmission2.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ditateum.githubappsubmission2.view.detailuser.DetailUserActivity
import com.ditateum.githubappsubmission2.view.detailuser.FollowersFragment
import com.ditateum.githubappsubmission2.view.detailuser.FollowingFragment

class SectionsPagerAdapter(activity: DetailUserActivity, data: Bundle) : FragmentStateAdapter(activity) {

    private var fragmentBundle : Bundle = data

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        if (fragment != null) {
            fragment.arguments = this.fragmentBundle
        }
        return fragment as Fragment
    }

}