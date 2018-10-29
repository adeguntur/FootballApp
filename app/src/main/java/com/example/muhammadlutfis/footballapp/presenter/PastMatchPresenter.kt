package com.example.muhammadlutfis.footballapp.presenter

import com.example.muhammadlutfis.footballapp.CCProvider
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.api.TheSportDBApi
import com.example.muhammadlutfis.footballapp.model.MatchResponse
import com.example.muhammadlutfis.footballapp.view.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PastMatchPresenter (private val anInterface: MatchView,
                          private val API: ApiRepository,
                          private val gson: Gson,
                          private val context: CCProvider = CCProvider()) {

    fun getMatchList(match: String?, matchSearch: String?) {
        anInterface.showLoading()

        async(context.main) {
            if (matchSearch == "empty_parameter") {
                val data = bg {
                    gson.fromJson(API
                        .doRequest(TheSportDBApi.getPastMatch(match)),
                        MatchResponse::class.java
                    )
                }
                anInterface.showMatchEventList(data.await().events)
                anInterface.hideLoading()
            } else if (match == "empty_parameter") {
                val dataSearch = bg {
                    gson.fromJson(API
                        .doRequest(TheSportDBApi.getEventSearch(matchSearch)),
                        MatchResponse::class.java
                    )
                }
                anInterface.showMatchEventList(dataSearch.await().event)
                anInterface.hideLoading()
            }
        }
    }
}
