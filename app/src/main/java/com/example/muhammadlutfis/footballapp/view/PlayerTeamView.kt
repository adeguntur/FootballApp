package com.example.muhammadlutfis.footballapp.view

import com.example.muhammadlutfis.footballapp.model.PlayerTeamModel

interface PlayerTeamView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<PlayerTeamModel>)
}