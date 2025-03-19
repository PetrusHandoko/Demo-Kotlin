package com.example.demo.service.weather.noaa

import com.example.demo.service.geolocation.dto.GeoAddressLocation
import com.example.demo.dto.USAddress
import com.example.demo.dto.WeatherDataInfo
import com.example.demo.service.geolocation.GeoLocationService
import org.json.JSONObject
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service


interface ExternalWeatherServiceApi {
    fun dataFromExternalApi(address: USAddress, processData: (data:Any)->WeatherDataInfo) : WeatherDataInfo?
}


@Service
class NOAAWeatherService(val geoCodingService: GeoLocationService,
                         val restTemplateBuilder: RestTemplateBuilder
) : ExternalWeatherServiceApi {

    override fun dataFromExternalApi(address: USAddress, processData: (data: Any) -> WeatherDataInfo): WeatherDataInfo? {
        val location = geoCodingService.geoLocationFromAddress(address, null)
        val url = location?.toObservationStationURL()
        val stationId = url?.let { observationStationId(it) }

        val weatherStationURL = "https://api.weather.gov/stations/$stationId/observations/latest"
        return restTemplateBuilder.build().getForObject(weatherStationURL, String::class.java)
            ?.let { processData(it) }

    }


    private fun GeoAddressLocation.toObservationStationURL() : String{
        val pointsUrl = "https://api.weather.gov/points/${this.lat},${this.lng}"
        val restTemplate = restTemplateBuilder
            .defaultHeader("accept", "application/geo+json")
            .build()

        // We could map directly using JSON mapper
        val returnData = restTemplate.getForObject(pointsUrl, String::class.java)
        val properties = JSONObject(returnData).getJSONObject("properties")

        //observationStations
        return properties.getString("observationStations")
    }

    private fun observationStationId(observationStationsURL :String): String{

        val restTemplate = restTemplateBuilder.build()
        val observations = restTemplate.getForObject(observationStationsURL, String::class.java)
        val jsonResponse = JSONObject(observations)
        //var features = jsonResponse.getJSONArray("features");
        val stations: org.json.JSONArray = jsonResponse.getJSONArray("observationStations")
        // Picked the first station.  In general the closes one? If not do some calc on location
        val station = stations[0].toString()
        val token = station.split("/")
        println("Picked: " + token.last())
        return token.last()
    }

}