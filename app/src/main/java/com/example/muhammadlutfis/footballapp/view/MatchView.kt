package com.example.muhammadlutfis.footballapp.view

import com.example.muhammadlutfis.footballapp.model.MatchModel

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchEventList(match: List<MatchModel>?)
}