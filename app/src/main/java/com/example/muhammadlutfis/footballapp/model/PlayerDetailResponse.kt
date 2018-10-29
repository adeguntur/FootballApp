package com.example.muhammadlutfis.footballapp.model

import com.google.gson.annotations.SerializedName

data class PlayerDetailResponse (
    @field:SerializedName("players")
    val playerDetails: List<PlayerDetailModel>
)