package com.example.muhammadlutfis.footballapp.presenter

import com.example.muhammadlutfis.footballapp.CCProvider
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.api.TheSportDBApi
import com.example.muhammadlutfis.footballapp.model.MatchResponse
import com.example.muhammadlutfis.footballapp.view.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class NextMatchPresenter (private val anInterface: MatchView,
                          private val API: ApiRepository,
                          private val gson: Gson,
                          private val context: CCProvider = CCProvider()) {

    fun getMatchList(match: String?) {
        anInterface.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(API
                    .doRequest(TheSportDBApi.getNextMatch(match)),
                    MatchResponse::class.java
                )
            }
            anInterface.showMatchEventList(data.await().events)
            anInterface.hideLoading()
        }
    }
}
