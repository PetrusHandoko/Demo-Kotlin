package com.example.demo.service

import com.example.demo.dto.USAddress
import com.example.demo.dto.WeatherDataInfo
import com.example.demo.service.weather.noaa.ExternalWeatherServiceApi
import org.springframework.stereotype.Service
import com.example.demo.service.weather.noaa.NOAAWeatherDataInfo
import org.json.JSONException
import org.json.JSONObject

interface WeatherDataService{
    fun getWeatherDataInfo(address: USAddress): WeatherDataInfo
}

val NO_WEATHER_DATA = WeatherDataInfo(
    Temperature = "",
    high =  "",
    low =  "",
    description =  ""
    )

@Service
class WeatherDataServiceImpl(
    val weatherForcastService: ExternalWeatherServiceApi
) : WeatherDataService{

    override fun getWeatherDataInfo(address: USAddress): WeatherDataInfo {
        return weatherForcastService.dataFromExternalApi(address) { data ->
            when (data) {
                is NOAAWeatherDataInfo -> data.toWeatherDataInfo()
                is String -> data.toWeatherDataInfo()
                else -> NO_WEATHER_DATA
            }
        } ?: NO_WEATHER_DATA

    }

}

// Mapper
fun NOAAWeatherDataInfo.toWeatherDataInfo() : WeatherDataInfo{
    return WeatherDataInfo(
        Temperature = this.properties.forecast,
        high =  "",
        low =  "",
        description =  ""
    )
}

fun String.toWeatherDataInfo(): WeatherDataInfo {
    try {
        val jsonResponse = JSONObject(this)
        val properties: JSONObject = jsonResponse.getJSONObject("properties")
        var temperature: JSONObject = properties.getJSONObject("temperature")
        val currentTemp = if (temperature.getString("value") == "null") -99.0 else temperature.getDouble("value")

        temperature = properties.getJSONObject("minTemperatureLast24Hours")
        val minTemperature = if (temperature.getString("value") == "null") -99.0 else temperature.getDouble("value")

        temperature = properties.getJSONObject("maxTemperatureLast24Hours")
        val maxTemperature = if (temperature.getString("value") == "null") -99.0 else temperature.getDouble("value")
        val description: String = properties.getString("textDescription")
        return WeatherDataInfo(
            currentTemp.toStringTemperature(),
            minTemperature.toStringTemperature(),
            maxTemperature.toStringTemperature(), description
        )
    } catch (e: JSONException) {
        throw RuntimeException(e)
    }
}

fun Double.toStringTemperature(isCelsius: Boolean = true): String {
    if (this == -99.0) return "N/A"
    val fahrenheit = if (isCelsius) (this * 9.0 / 5.0) + 32 else this
    return String.format("%.1f", fahrenheit)
}
