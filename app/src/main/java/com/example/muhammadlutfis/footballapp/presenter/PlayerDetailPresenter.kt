package com.example.muhammadlutfis.footballapp.presenter

import com.example.muhammadlutfis.footballapp.CCProvider
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.api.TheSportDBApi
import com.example.muhammadlutfis.footballapp.model.PlayerDetailResponse
import com.example.muhammadlutfis.footballapp.view.PlayerDetailView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerDetailPresenter (private val anInterface: PlayerDetailView,
                             private val apiRepository: ApiRepository,
                             private val gson: Gson,
                             private val context: CCProvider = CCProvider()) {

    fun getPlayerList(idTeam: String?) {
        anInterface.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPlayerDetail(idTeam)),
                    PlayerDetailResponse::class.java
                )
            }
            anInterface.showPlayerDetail(data.await().playerDetails)
            anInterface.hideLoading()
        }
    }
}