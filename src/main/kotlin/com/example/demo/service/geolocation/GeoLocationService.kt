package com.example.demo.service.geolocation

import com.example.demo.service.geolocation.dto.GeoAddressLocation
import com.example.demo.dto.USAddress
import com.example.demo.dto.toStreetAddress
import com.example.demo.service.geolocation.dto.GeoData
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

interface GeoLocationService {
    fun geoLocationFromAddress(address: USAddress, onComplete: ((location:GeoData)->Unit)?): GeoAddressLocation?
}

@Service
class USCensusLocationService : GeoLocationService {

    override fun geoLocationFromAddress(address: USAddress,
                                        onComplete : ((location: GeoData) -> Unit)? ) : GeoAddressLocation? {
        val geoData = WebClient.builder()
            .baseUrl("https://geocoding.geo.census.gov/geocoder/locations/address")
            .build()
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("benchmark", "Public_AR_Current")
                    .queryParam("format", "json")
                    .queryParam("street", address.toStreetAddress())
                    .queryParam("city", address.city)
                    .queryParam("state", address.state)
                    .queryParam("zip", address.zip)
                    .build()
            }
            .retrieve()
            .bodyToMono(GeoData::class.java)//GeoData::class.java) // Convert response to GeoData object
            .block() // Blocking for simplicity

        if (geoData != null) {
            if (geoData.result.addressMatches.isNotEmpty()) {
                val location = GeoAddressLocation(
                    lat = geoData.result.addressMatches[0].coordinates.y,
                    lng = geoData.result.addressMatches[0].coordinates.x
                )

                if (onComplete != null) {
                    onComplete(geoData)
                }
                return location
            }
        }
        return null
    }


}

