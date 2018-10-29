package com.example.muhammadlutfis.footballapp.presenter

import com.example.muhammadlutfis.footballapp.CCProvider
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.api.TheSportDBApi
import com.example.muhammadlutfis.footballapp.model.PlayerTeamResponse
import com.example.muhammadlutfis.footballapp.view.PlayerTeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerTeamPresenter (private val anInterface: PlayerTeamView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CCProvider = CCProvider()) {

    fun getPlayerList(idTeam: String?) {
        anInterface.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPlayerTeam(idTeam)),
                    PlayerTeamResponse::class.java
                )
            }
            anInterface.showPlayerList(data.await().playerTeams)
            anInterface.hideLoading()
        }
    }
}