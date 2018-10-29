package com.example.muhammadlutfis.footballapp.view

import com.example.muhammadlutfis.footballapp.model.PlayerDetailModel

interface PlayerDetailView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerDetail(data: List<PlayerDetailModel>)
}