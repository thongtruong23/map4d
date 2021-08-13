package com.truongthong.map4d.model.search

import com.truongthong.map4d.model.search.MapLocation

data class MapResponse(
    val code: String,
    var result: MutableList<MapLocation>? = null
)