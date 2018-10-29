package com.example.muhammadlutfis.footballapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.muhammadlutfis.footballapp.fragment.DescTeamFragment
import com.example.muhammadlutfis.footballapp.fragment.PlayerTeamFragment

class TeamPageAdapter (private val idTeam: String, fm: FragmentManager, private var tabCount: Int)
    : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                newInstance(idTeam)
            }
            1 -> {
                newInstancePlayer(idTeam)
            }
            else -> {
                return PlayerTeamFragment()
            }
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Deskripsi"
            else -> {
                return "Pemain"
            }
        }
    }

    companion object {
        const val KEY_TEAM_A = "KEY_TEAM_A"
        const val KEY_TEAM_B = "KEY_TEAM_B"
        fun newInstance(id: String): DescTeamFragment {
            val bindData = Bundle()
            bindData.putString(KEY_TEAM_A, id)

            val desciptionTeamFragment = DescTeamFragment()
            desciptionTeamFragment.arguments = bindData
            return desciptionTeamFragment
        }

        fun newInstancePlayer(id: String): PlayerTeamFragment {
            val bindData = Bundle()
            bindData.putString(KEY_TEAM_B, id)

            val playerTeamFragment = PlayerTeamFragment()
            playerTeamFragment.arguments = bindData
            return playerTeamFragment
        }
    }
}