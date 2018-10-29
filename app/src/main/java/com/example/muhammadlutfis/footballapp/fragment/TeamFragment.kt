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
import android.widget.*
import com.example.muhammadlutfis.footballapp.R
import com.example.muhammadlutfis.footballapp.R.array.league_list
import com.example.muhammadlutfis.footballapp.R.color.*
import com.example.muhammadlutfis.footballapp.R.string.*
import com.example.muhammadlutfis.footballapp.R.id.*
import com.example.muhammadlutfis.footballapp.activity.TeamDetailActivity
import com.example.muhammadlutfis.footballapp.adapter.TeamAdapter
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.invisible
import com.example.muhammadlutfis.footballapp.model.TeamModel
import com.example.muhammadlutfis.footballapp.presenter.TeamPresenter
import com.example.muhammadlutfis.footballapp.view.TeamView
import com.example.muhammadlutfis.footballapp.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamFragment : Fragment(), AnkoComponent<Context>, TeamView {

    private var teams: MutableList<TeamModel> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var spinner: Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String

    private lateinit var fieldSearch: EditText
    private lateinit var searchButton: ImageButton

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val spinnerItems = resources.getStringArray(league_list)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = TeamAdapter(teams) {
            ctx.startActivity<TeamDetailActivity>("id" to "${it.teamId}")
        }
        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName, getString(R.string.no_parameter))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName, getString(R.string.no_parameter))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            appBarLayout {
                lparams(matchParent, wrapContent)
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)
                bottomPadding = dip(8)

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL

                    fieldSearch = editText {
                        singleLine = true
                        id = edt_search
                        hint = "Find Team"
                        setTextColor(getResources().getColor(white))
                        setHintTextColor(getResources().getColor(white))
                    }.lparams(width = dip(0), height = wrapContent, weight = 5f)

                    searchButton = imageButton {
                        imageResource = com.example.muhammadlutfis.footballapp.R.drawable.ic_search_black
                        backgroundColor = 80000000
                        onClick {
                            presenter.getTeamList(getString(no_parameter), fieldSearch.textValue())
                        }
                    }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                }
            }
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.HORIZONTAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)
                bottomPadding = dip(8)
                spinner = spinner()
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    colorAccent,
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

    override fun showTeamList(data: List<TeamModel>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    fun EditText.textValue() = text.toString()

}