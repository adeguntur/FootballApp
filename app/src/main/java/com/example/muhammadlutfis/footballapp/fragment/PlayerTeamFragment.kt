package com.example.muhammadlutfis.footballapp.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.muhammadlutfis.footballapp.R
import com.example.muhammadlutfis.footballapp.TeamPageAdapter
import com.example.muhammadlutfis.footballapp.activity.PlayerDetailActivity
import com.example.muhammadlutfis.footballapp.adapter.PlayerTeamAdapter
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.invisible
import com.example.muhammadlutfis.footballapp.model.PlayerTeamModel
import com.example.muhammadlutfis.footballapp.presenter.PlayerTeamPresenter
import com.example.muhammadlutfis.footballapp.view.PlayerTeamView
import com.example.muhammadlutfis.footballapp.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerTeamFragment : Fragment(), AnkoComponent<Context>, PlayerTeamView {

    private var playerTeams: MutableList<PlayerTeamModel> = mutableListOf()
    private lateinit var presenter: PlayerTeamPresenter
    private lateinit var adapter: PlayerTeamAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var idTeam: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = PlayerTeamAdapter(playerTeams) {
            ctx.startActivity<PlayerDetailActivity>("id" to "${it.idPlayer}")
        }
        listEvent.adapter = adapter

        val bindData = arguments
        idTeam = bindData?.getString(TeamPageAdapter.KEY_TEAM_B) ?: "idTeam"

        val request = ApiRepository()
        val gson = Gson()

        presenter = PlayerTeamPresenter(this, request, gson)
        presenter.getPlayerList(idTeam)


        swipeRefresh.onRefresh {
            presenter.getPlayerList(idTeam)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPlayerList(data: List<PlayerTeamModel>) {
        swipeRefresh.isRefreshing = false
        playerTeams.clear()
        playerTeams.addAll(data)
        adapter.notifyDataSetChanged()

    }
}