package com.example.muhammadlutfis.footballapp

import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.api.TheSportDBApi
import com.example.muhammadlutfis.footballapp.model.MatchModel
import com.example.muhammadlutfis.footballapp.model.MatchResponse
import com.example.muhammadlutfis.footballapp.presenter.NextMatchPresenter
import com.example.muhammadlutfis.footballapp.view.MatchView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NextMatchTest {
    @Test
    fun getNextMatchList() {
        val Team: MutableList<MatchModel> = mutableListOf()
        val Response = MatchResponse(Team)
        val League = "Spanish La Liga"

        Mockito.`when`(gson.fromJson(api
            .doRequest(TheSportDBApi.getNextMatch(League)),
            Response::class.java
        )).thenReturn(Response)

        next.getMatchList(League)

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
    private lateinit var next: NextMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        next = NextMatchPresenter(view, api, gson, TCProvider())
    }
}