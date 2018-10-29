package com.example.muhammadlutfis.footballapp.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.muhammadlutfis.footballapp.R
import com.example.muhammadlutfis.footballapp.R.drawable.ic_add_to_favorites
import com.example.muhammadlutfis.footballapp.R.drawable.ic_added_to_favorites
import com.example.muhammadlutfis.footballapp.R.id.add_to_favorite
import com.example.muhammadlutfis.footballapp.R.menu.detail_menu
import com.example.muhammadlutfis.footballapp.TeamPageAdapter
import com.example.muhammadlutfis.footballapp.api.ApiRepository
import com.example.muhammadlutfis.footballapp.db.FavoriteTeam
import com.example.muhammadlutfis.footballapp.db.database
import com.example.muhammadlutfis.footballapp.model.TeamModel
import com.example.muhammadlutfis.footballapp.presenter.TeamDetailPresenter
import com.example.muhammadlutfis.footballapp.view.TeamDetailView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: TeamModel

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String
    private lateinit var checkNull: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        val intent = intent
        id = intent.getStringExtra("id")
        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentAdapter = TeamPageAdapter(id, supportFragmentManager, 2)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)

    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showTeamDetail(data: List<TeamModel>) {
        teams = TeamModel(data[0].teamId,
            data[0].teamName,
            data[0].teamBadge
        )

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                try {
                    if (isFavorite) removeFavorite() else addFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                } catch (e: Exception) {
                    toast("coba,lagi")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addFavorite() {
        try {
            database.use {
                insert(
                    FavoriteTeam.TBL_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to teams.teamBadge,
                    FavoriteTeam.TEAM_NAME to teams.teamId,
                    FavoriteTeam.TEAM_BADGE to teams.teamName)
            }
            Snackbar.make(findViewById(R.id.ll_team),
                "Add to Favorite",
                Snackbar.LENGTH_SHORT).show()

        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.ll_team),
                "Failed" + e.localizedMessage,
                Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun removeFavorite() {
        try {
            database.use {
                delete(FavoriteTeam.TBL_FAVORITE_TEAM, "(TEAM_ID = {id})",
                    "id" to id)
            }
            Snackbar.make(findViewById(R.id.ll_team),
                "Remove from Favorite", Snackbar.LENGTH_SHORT).show()

        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.ll_team),
                "Failed" + e.localizedMessage,
                Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                ic_add_to_favorites)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TBL_FAVORITE_TEAM)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}