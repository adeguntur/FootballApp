package com.example.muhammadlutfis.footballapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import com.example.muhammadlutfis.footballapp.R
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.invisible
import com.example.muhammadlutfis.footballapp.model.PlayerDetailModel
import com.example.muhammadlutfis.footballapp.presenter.PlayerDetailPresenter
import com.example.muhammadlutfis.footballapp.view.PlayerDetailView
import com.example.muhammadlutfis.footballapp.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView {

    private lateinit var progressBar: ProgressBar
    private lateinit var pemainDetailPresenter: PlayerDetailPresenter
    private lateinit var idPlayer: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        supportActionBar?.title = "Player Detail"

        progressBar = findViewById(R.id.progress_bar_player)

        val myIntent = intent

        idPlayer = myIntent.getStringExtra("id")
        val request = ApiRepository()
        val gson = Gson()

        pemainDetailPresenter = PlayerDetailPresenter(this, request, gson)
        pemainDetailPresenter.getPlayerList(idPlayer)

    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPlayerDetail(data: List<PlayerDetailModel>) {
        Picasso.get().load(data.get(0).strFanart1).into(iv_player_detail)
        txt_weight.text = data.get(0).strWeight
        txt_height.text = data.get(0).strHeight
        txt_position_player.text = data.get(0).strPosition
        txt_forward_player_detail.text = data.get(0).strDescriptionEN
    }
}