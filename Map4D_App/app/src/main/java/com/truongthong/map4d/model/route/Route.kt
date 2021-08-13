package com.truongthong.map4d.model.route

data class Route(
    val distance: Distance,
    val duration: Duration,
    val legs: List<Leg>,
    val overviewPolyline: String,
    val snappedWaypoints: List<SnappedWaypoint>,
    val summary: String
)