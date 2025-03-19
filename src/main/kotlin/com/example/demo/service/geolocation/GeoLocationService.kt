package com.example.demo.service.geolocation

import com.example.demo.service.geolocation.dto.GeoAddressLocation
import com.example.demo.dto.USAddress
import com.example.demo.dto.toStreetAddress
import com.example.demo.service.geolocation.dto.GeoData
import com.example.demo.service.geolocation.dto.toLocation
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

interface GeoLocationService {
    fun geoLocationFromAddress(address: USAddress, onComplete: ((geoData:GeoData, location : GeoAddressLocation)->Unit)?): GeoAddressLocation?
}

@Service
class USCensusLocationService (val restTemplateBuilder: RestTemplateBuilder): GeoLocationService {


    override fun geoLocationFromAddress(address: USAddress,
                                        onComplete : ((geoData: GeoData, location : GeoAddressLocation) -> Unit)? ) : GeoAddressLocation? {
        val restTemplate = restTemplateBuilder

            .defaultHeader("accept", "application/geo+json")
            .build()

        val geLocationApiUrl = UriComponentsBuilder.fromUriString("https://geocoding.geo.census.gov/geocoder/locations/address")
                    .queryParam("benchmark", "Public_AR_Current")
                    .queryParam("format", "json")
                    .queryParam("street", address.toStreetAddress())
                    .queryParam("city", address.city)
                    .queryParam("state", address.state)
                    .queryParam("zip", address.zip)
            .toUriString()

        val geoData = restTemplate
            .getForObject(geLocationApiUrl, GeoData::class.java)

        if (geoData != null) {
            if (geoData.result.addressMatches.isNotEmpty()) {
                val location = GeoAddressLocation(
                    lat = geoData.result.addressMatches[0].coordinates.y,
                    lng = geoData.result.addressMatches[0].coordinates.x
                )

                if (onComplete != null) {
                    onComplete(geoData, location)
                }
                println("geoLocationFromAddress (${address.toStreetAddress()}): ${location.toLocation()}")
                return location
            }
        }
        return null
    }


}

