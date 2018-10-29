package com.example.muhammadlutfis.footballapp.view

import com.example.muhammadlutfis.footballapp.model.TeamModel

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<TeamModel>)
}