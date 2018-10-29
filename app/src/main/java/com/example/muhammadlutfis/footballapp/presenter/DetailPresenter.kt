package com.example.muhammadlutfis.footballapp.presenter

import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.api.TheSportDBApi
import com.example.muhammadlutfis.footballapp.model.MatchResponse
import com.example.muhammadlutfis.footballapp.view.DetailView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPresenter (private val anInterface: DetailView,
                       private val API: ApiRepository,
                       private val gson: Gson
) {

    fun geDetailTeamList(teamA: String?, teamB: String?) {
        anInterface.showLoading()

        async(UI) {
            val dataA = bg {
                gson.fromJson(API
                    .doRequest(TheSportDBApi.getDetailTeam(teamA)),
                    MatchResponse::class.java
                )
            }
            val dataB = bg {
                gson.fromJson(API
                    .doRequest(TheSportDBApi.getDetailTeam(teamB)),
                    MatchResponse::class.java
                )
            }
            anInterface.showTeamsList(dataA.await().teams, dataB.await().teams)
            anInterface.hideLoading()
        }
    }
}
