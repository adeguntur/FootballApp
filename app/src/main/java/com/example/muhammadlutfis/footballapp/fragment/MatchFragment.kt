package com.example.muhammadlutfis.footballapp.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.R.layout.support_simple_spinner_dropdown_item
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
import com.example.muhammadlutfis.footballapp.R.array.match_list
import com.example.muhammadlutfis.footballapp.R.color.*
import com.example.muhammadlutfis.footballapp.R.string.*
import com.example.muhammadlutfis.footballapp.R.id.*
import com.example.muhammadlutfis.footballapp.activity.MatchDetailActivity
import com.example.muhammadlutfis.footballapp.adapter.MatchAdapter
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.invisible
import com.example.muhammadlutfis.footballapp.key.KeyID
import com.example.muhammadlutfis.footballapp.model.MatchModel
import com.example.muhammadlutfis.footballapp.presenter.NextMatchPresenter
import com.example.muhammadlutfis.footballapp.presenter.PastMatchPresenter
import com.example.muhammadlutfis.footballapp.view.MatchView
import com.example.muhammadlutfis.footballapp.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.*

class MatchFragment : Fragment(), AnkoComponent<Context>, MatchView {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var fieldSearch: EditText
    private lateinit var searchButton: ImageButton

    private lateinit var spinner: Spinner
    private lateinit var spinner2: Spinner

    private var matchEvent: MutableList<MatchModel> = mutableListOf()
    private lateinit var pastMatchPresenter: PastMatchPresenter
    private lateinit var nextMatchPresenter: NextMatchPresenter
    private lateinit var adapter: MatchAdapter

    private lateinit var leagueName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fieldSearch = view!!.findViewById(edt_search)

        adapter = MatchAdapter(matchEvent) {
            startActivity<MatchDetailActivity>(
                KeyID.HOME_ID_KEY to it.idHomeTeam,
                KeyID.AWAY_ID_KEY to it.idAwayTeam,
                KeyID.EVENT_ID_KEY to it.idEvent)
        }
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        pastMatchPresenter = PastMatchPresenter(this, request, gson)
        nextMatchPresenter = NextMatchPresenter(this, request, gson)

        val spinnerItems = resources.getStringArray(match_list)
        val spinnerAdapter = ArrayAdapter(ctx, support_simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        val spinnerItemsLeague = resources.getStringArray(league_list)
        val spinnerAdapterLeague = ArrayAdapter(ctx, support_simple_spinner_dropdown_item, spinnerItemsLeague)
        spinner2.adapter = spinnerAdapterLeague

        pastMatchPresenter.getMatchList(getString(id_english_premier_league),
            getString(R.string.no_parameter))

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (spinner.selectedItem == getString(past_match) &&
                    spinner2.selectedItem == getString(spanish_la_liga)) {

                    pastMatchPresenter.getMatchList(getString(id_spanish_la_liga),
                        getString(R.string.no_parameter))

                    swipeRefresh.onRefresh {
                        pastMatchPresenter.getMatchList(getString(id_spanish_la_liga),
                            getString(R.string.no_parameter))
                    }

                }
                if (spinner.selectedItem == getString(past_match) &&
                    spinner2.selectedItem == getString(english_premier_league)) {

                    pastMatchPresenter.getMatchList(getString(id_english_premier_league),
                        getString(R.string.no_parameter))

                    swipeRefresh.onRefresh {
                        pastMatchPresenter.getMatchList(getString(id_english_premier_league),
                            getString(R.string.no_parameter))
                    }

                }

                if (spinner.selectedItem == getString(past_match) &&
                    spinner2.selectedItem == getString(english_league_championship)) {

                    pastMatchPresenter.getMatchList(getString(id_english_league_championship),
                        getString(R.string.no_parameter))

                    swipeRefresh.onRefresh {
                        pastMatchPresenter.getMatchList(getString(id_english_league_championship),
                            getString(R.string.no_parameter))
                    }
                }
                if (spinner.selectedItem == getString(past_match) &&
                    spinner2.selectedItem == getString(german_bundes_liga)) {

                    pastMatchPresenter.getMatchList(getString(id_german_bundes_liga),
                        getString(R.string.no_parameter))

                    swipeRefresh.onRefresh {
                        pastMatchPresenter.getMatchList(getString(id_german_bundes_liga),
                            getString(R.string.no_parameter))
                    }

                }
                if (spinner.selectedItem == getString(past_match) &&
                    spinner2.selectedItem == getString(italian_serie_A)) {

                    pastMatchPresenter.getMatchList(getString(id_italian_serie_A),
                        getString(R.string.no_parameter))

                    swipeRefresh.onRefresh {
                        pastMatchPresenter.getMatchList(getString(id_italian_serie_A),
                            getString(R.string.no_parameter))
                    }

                }
                if (spinner.selectedItem == getString(past_match) &&
                    spinner2.selectedItem == getString(french_ligue_1)) {

                    pastMatchPresenter.getMatchList(getString(id_french_ligue_1),
                        getString(R.string.no_parameter))

                    swipeRefresh.onRefresh {
                        pastMatchPresenter.getMatchList(getString(id_french_ligue_1),
                            getString(R.string.no_parameter))
                    }

                }
                if (spinner.selectedItem == getString(next_match) &&
                    spinner2.selectedItem == getString(spanish_la_liga)) {

                    nextMatchPresenter.getMatchList(getString(id_spanish_la_liga))

                    swipeRefresh.onRefresh {
                        nextMatchPresenter.getMatchList(getString(id_spanish_la_liga))
                    }

                }
                if (spinner.selectedItem == getString(next_match) &&
                    spinner2.selectedItem == getString(english_premier_league)) {

                    nextMatchPresenter.getMatchList(getString(id_english_premier_league))

                    swipeRefresh.onRefresh {
                        nextMatchPresenter.getMatchList(getString(id_english_premier_league))
                    }

                }
                if (spinner.selectedItem == getString(next_match) &&
                    spinner2.selectedItem == getString(english_league_championship)) {

                    nextMatchPresenter.getMatchList(getString(id_english_league_championship))

                    swipeRefresh.onRefresh {
                        nextMatchPresenter.getMatchList(getString(id_english_league_championship))
                    }

                }
                if (spinner.selectedItem == getString(next_match) &&
                    spinner2.selectedItem == getString(german_bundes_liga)) {

                    nextMatchPresenter.getMatchList(getString(id_german_bundes_liga))

                    swipeRefresh.onRefresh {
                        nextMatchPresenter.getMatchList(getString(id_german_bundes_liga))
                    }

                }
                if (spinner.selectedItem == getString(next_match) &&
                    spinner2.selectedItem == getString(italian_serie_A)) {

                    nextMatchPresenter.getMatchList(getString(id_italian_serie_A))

                    swipeRefresh.onRefresh {
                        nextMatchPresenter.getMatchList(getString(id_italian_serie_A))
                    }

                }
                if (spinner.selectedItem == getString(next_match) &&
                    spinner2.selectedItem == getString(french_ligue_1)) {

                    nextMatchPresenter.getMatchList(getString(id_french_ligue_1))
                }

                swipeRefresh.onRefresh {
                    nextMatchPresenter.getMatchList(getString(id_french_ligue_1))
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinner.onItemSelectedListener = spinner2.onItemSelectedListener
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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
                        hint = "Find Match"
                        setTextColor(getResources().getColor(R.color.white))
                        setHintTextColor(getResources().getColor(R.color.white))
                    }.lparams(width = dip(0), height = wrapContent, weight = 5f)

                    searchButton = imageButton {
                        imageResource = R.drawable.ic_search_black
                        backgroundColor = 80000000
                        onClick {
                            pastMatchPresenter.getMatchList(getString(R.string.no_parameter),
                                fieldSearch.textValue())
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
                spinner = spinner {}
                    .lparams(width = dip(0), height = wrapContent, weight = 1f)

                spinner2 = spinner {}
                    .lparams(width = dip(0), height = wrapContent, weight = 1f)
            }

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        id = R.id.list_team
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerInParent()
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

    override fun showMatchEventList(data: List<MatchModel>?) {
        swipeRefresh.isRefreshing = false
        matchEvent.clear()
        data?.let {
            matchEvent.addAll(data)
            adapter.notifyDataSetChanged()
        } ?: toast(getString(R.string.no_data))
    }

    fun EditText.textValue() = text.toString()
}