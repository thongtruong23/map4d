package com.truongthong.map4d.model.route

data class Leg(
    val distance: DistanceX,
    val duration: DurationX,
    val endAddress: String,
    val endLocation: EndLocation,
    val startAddress: String,
    val startLocation: StartLocation,
    val steps: List<Step>
)