package com.truongthong.map4d.model.route

data class Step(
    val distance: DistanceXX,
    val duration: DurationXX,
    val endLocation: EndLocationX,
    val htmlInstructions: String,
    val maneuver: String,
    val polyline: String,
    val startLocation: StartLocationX,
    val streetName: String,
    val travelMode: String
)