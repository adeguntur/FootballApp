package com.example.muhammadlutfis.footballapp.presenter

import com.example.muhammadlutfis.footballapp.CCProvider
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.api.TheSportDBApi
import com.example.muhammadlutfis.footballapp.model.MatchResponse
import com.example.muhammadlutfis.footballapp.view.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchDetailPresenter (private val anInterface: MatchView,
                            private val api: ApiRepository,
                            private val gson: Gson,
                            private val context: CCProvider = CCProvider()) {

    fun getDetailMatch(event: String?) {
        anInterface.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(api
                    .doRequest(TheSportDBApi.getDetailMatch(event)),
                    MatchResponse::class.java
                )
            }
            anInterface.showMatchEventList(data.await().events)
            anInterface.hideLoading()
        }
    }
}