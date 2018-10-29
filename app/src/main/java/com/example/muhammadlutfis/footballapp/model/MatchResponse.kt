package com.example.muhammadlutfis.footballapp.model

import com.google.gson.annotations.SerializedName

data class MatchResponse (
    @field:SerializedName("events")
    val events: List<MatchModel>? = null,

    @field:SerializedName("event")
    val event: List<MatchModel>? = null,

    @field:SerializedName("teams")
    val teams: List<TeamModel>? = null
)