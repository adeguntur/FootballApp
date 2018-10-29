package com.example.muhammadlutfis.footballapp.presenter

import com.example.muhammadlutfis.footballapp.CCProvider
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.api.TheSportDBApi
import com.example.muhammadlutfis.footballapp.model.TeamResponse
import com.example.muhammadlutfis.footballapp.view.TeamDetailView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamDetailPresenter (private val anInterface: TeamDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CCProvider = CCProvider()) {

    fun getTeamDetail(teamId: String) {
        anInterface.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getDetailTeam(teamId)),
                    TeamResponse::class.java
                )
            }

            anInterface.showTeamDetail(data.await().teams)
            anInterface.hideLoading()
        }
    }
}