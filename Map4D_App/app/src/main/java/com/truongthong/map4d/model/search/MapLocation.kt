package com.truongthong.map4d.model.search

data class MapLocation(
    val address: String,
    val id: String,
    val location: Location,
    val name: String,
    val types: MutableList<String>
)