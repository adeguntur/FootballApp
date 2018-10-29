package com.example.muhammadlutfis.footballapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.muhammadlutfis.footballapp.R
import com.example.muhammadlutfis.footballapp.R.id.*
import com.example.muhammadlutfis.footballapp.fragment.FavoriteMatchFragment
import com.example.muhammadlutfis.footballapp.fragment.FavoriteTeamFragment
import com.example.muhammadlutfis.footballapp.fragment.MatchFragment
import com.example.muhammadlutfis.footballapp.fragment.TeamFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                match -> {
                    loadPastMatchFragment(savedInstanceState)
                }
                teams -> {
                    loadNextMatchFragment(savedInstanceState)
                }
                favoriteMatch -> {
                    loadFavoritesFragment(savedInstanceState)
                }
                favoritesTeam -> {
                    loadFavoritesTeamFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = match
    }

    private fun loadPastMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container, MatchFragment()
                    , MatchFragment::class.simpleName)
                .commit()
        }
    }

    private fun loadNextMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container, TeamFragment()
                    , TeamFragment::class.simpleName)
                .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container, FavoriteMatchFragment()
                    , FavoriteMatchFragment::class.simpleName)
                .commit()
        }
    }

    private fun loadFavoritesTeamFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container, FavoriteTeamFragment()
                    , FavoriteTeamFragment::class.simpleName)
                .commit()
        }
    }
}
