package com.example.muhammadlutfis.footballapp.view

import com.example.muhammadlutfis.footballapp.model.TeamModel

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<TeamModel>)
}