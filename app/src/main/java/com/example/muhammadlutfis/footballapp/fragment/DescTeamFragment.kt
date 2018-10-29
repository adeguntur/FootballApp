package com.example.muhammadlutfis.footballapp.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.muhammadlutfis.footballapp.R
import com.example.muhammadlutfis.footballapp.TeamPageAdapter.Companion.KEY_TEAM_A
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.invisible
import com.example.muhammadlutfis.footballapp.model.TeamModel
import com.example.muhammadlutfis.footballapp.presenter.TeamDetailPresenter
import com.example.muhammadlutfis.footballapp.view.TeamDetailView
import com.example.muhammadlutfis.footballapp.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DescTeamFragment : Fragment(), TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: TeamModel
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private lateinit var idTeam: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {

                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL


                            cardView{
                                layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                                    leftMargin = dip(10)
                                    rightMargin = dip(10)
                                    topMargin = dip(5)
                                    bottomMargin = dip(5)

                                }
                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)
                                    orientation = LinearLayout.VERTICAL
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    teamBadge = imageView {}.lparams(height = dip(75))

                                    teamName = textView {
                                        this.gravity = Gravity.CENTER
                                        textSize = 20f
                                        textColor = ContextCompat.getColor(context, R.color.colorAccent)
                                    }.lparams {
                                        topMargin = dip(5)
                                    }

                                    teamFormedYear = textView {
                                        this.gravity = Gravity.CENTER
                                    }

                                    teamStadium = textView {
                                        this.gravity = Gravity.CENTER
                                        textColor = ContextCompat.getColor(context, R.color.colorPrimaryText)
                                    }
                                }
                            }


                            teamDescription = textView().lparams {
                                topMargin = dip(20)
                            }
                        }
                        progressBar = progressBar {
                        }.lparams {
                            topMargin = dip(8)
                            centerHorizontally()
                        }
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bindData = arguments
        idTeam = bindData?.getString(KEY_TEAM_A) ?: "idTeam"

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)

        presenter.getTeamDetail(idTeam)

        swipeRefresh.onRefresh {
            presenter.getTeamDetail(idTeam)

        }

    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetail(data: List<TeamModel>) {
        teams = TeamModel(data[0].teamId,
            data[0].teamName,
            data[0].teamBadge)
        swipeRefresh.isRefreshing = false
        Picasso.get().load(data[0].teamBadge).into(teamBadge)
        teamName.text = data[0].teamName
        teamDescription.text = data[0].teamDescription
        teamFormedYear.text = data[0].teamFormedYear
        teamStadium.text = data[0].teamStadium

    }

}
