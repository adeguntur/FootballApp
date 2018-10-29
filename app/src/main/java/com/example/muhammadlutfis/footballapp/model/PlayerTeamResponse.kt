package com.example.muhammadlutfis.footballapp.model

import com.google.gson.annotations.SerializedName

data class PlayerTeamResponse (
    @field:SerializedName("player")
    val playerTeams: List<PlayerTeamModel>
)