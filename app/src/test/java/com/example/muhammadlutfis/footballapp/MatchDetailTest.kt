package com.example.muhammadlutfis.footballapp

import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.api.TheSportDBApi
import com.example.muhammadlutfis.footballapp.model.MatchModel
import com.example.muhammadlutfis.footballapp.model.MatchResponse
import com.example.muhammadlutfis.footballapp.presenter.MatchDetailPresenter
import com.example.muhammadlutfis.footballapp.view.MatchView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchDetailTest {
    @Test
    fun testDetailMatch() {
        val Team: MutableList<MatchModel> = mutableListOf()
        val Response = MatchResponse(Team)
        val League = "Spanish La Liga"

        Mockito.`when`(gson.fromJson(api
            .doRequest(TheSportDBApi.getDetailMatch(League)),
            Response::class.java
        )).thenReturn(Response)

        matchDetail.getDetailMatch(League)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchEventList(Team)
        Mockito.verify(view).hideLoading()
    }

    @Mock
    private lateinit var view: MatchView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var api: ApiRepository
    @Mock
    private lateinit var matchDetail: MatchDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        matchDetail = MatchDetailPresenter(view, api, gson, TCProvider())
    }
}