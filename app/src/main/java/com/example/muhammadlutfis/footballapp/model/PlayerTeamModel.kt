package com.example.muhammadlutfis.footballapp.model

import com.google.gson.annotations.SerializedName

data class PlayerTeamModel (
    @SerializedName("idPlayer")
    var idPlayer: String? = null,

    @SerializedName("idTeam")
    var teamId: String? = null,

    @SerializedName("strCutout")
    var strCutout: String? = null,

    @SerializedName("strPlayer")
    var strPlayer: String? = null,

    @SerializedName("strPosition")
    var strPosition: String? = null
)