package com.example.muhammadlutfis.footballapp.presenter

import com.example.muhammadlutfis.footballapp.CCProvider
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.api.TheSportDBApi
import com.example.muhammadlutfis.footballapp.model.TeamResponse
import com.example.muhammadlutfis.footballapp.view.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter (private val anInterface: TeamView,
                     private val API: ApiRepository,
                     private val gson: Gson,
                     private val context: CCProvider = CCProvider()) {

    fun getTeamList(league: String?, leagueSearch: String?) {
        anInterface.showLoading()
        if (leagueSearch == "empty_parameter") {
            async(context.main) {
                val data = bg {
                    gson.fromJson(API
                        .doRequest(TheSportDBApi.getTeams(league)),
                        TeamResponse::class.java
                    )
                }
                anInterface.showTeamList(data.await().teams)
                anInterface.hideLoading()
            }

        } else if (league == "empty_parameter") {
            async(context.main) {
                val data = bg {
                    gson.fromJson(API
                        .doRequest(TheSportDBApi.getTeamSearch(leagueSearch)),
                        TeamResponse::class.java
                    )
                }
                anInterface.showTeamList(data.await().teams)
                anInterface.hideLoading()
            }
        }
    }
}