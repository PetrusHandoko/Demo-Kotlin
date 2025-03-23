package com.example.demo.service.geolocation.dto

data class GeoAddressLocation (val lat: Double, val lng: Double)

fun GeoAddressLocation.toLocation() = "Latitude: $lat Longitude: $lng"