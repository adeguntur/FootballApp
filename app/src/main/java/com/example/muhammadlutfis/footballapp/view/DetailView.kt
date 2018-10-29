package com.example.muhammadlutfis.footballapp.view

import com.example.muhammadlutfis.footballapp.model.TeamModel

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamsList(dataA: List<TeamModel>?, dataB: List<TeamModel>?)
}