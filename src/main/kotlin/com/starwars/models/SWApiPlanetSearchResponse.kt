package com.starwars.models

data class SWApiPlanetSearchResponse(
    val count: Int,
    val results: List<Result>
) {
    data class Result(
        val climate: String,
        val created: String,
        val diameter: String,
        val edited: String,
        val films: List<String>,
        val gravity: String,
        val name: String,
        val orbital_period: String,
        val population: String,
        val residents: List<String>,
        val rotation_period: String,
        val surface_water: String,
        val terrain: String,
        val url: String
    )
}