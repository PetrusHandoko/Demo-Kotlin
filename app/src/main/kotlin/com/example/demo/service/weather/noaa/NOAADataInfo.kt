package com.example.demo.service.weather.noaa


import com.google.gson.Gson
import org.jetbrains.annotations.TestOnly


data class NOAAWeatherDataInfo(
    val context: List<Any>?, // To handle mixed types (URIs and objects)
    val id: String,
    val type: String,
    val geometry: Geometry,
    val properties: Properties
)

data class Geometry(
    val type: String,
    val coordinates: List<Double>
)

data class Properties(
    val id: String,
    val type: String,
    val cwa: String,
    val forecastOffice: String,
    val gridId: String,
    val gridX: Int,
    val gridY: Int,
    val forecast: String,
    val forecastHourly: String,
    val forecastGridData: String,
    val observationStations: String,
    val relativeLocation: RelativeLocation,
    val forecastZone: String,
    val county: String,
    val fireWeatherZone: String,
    val timeZone: String,
    val radarStation: String
)

data class RelativeLocation(
    val type: String,
    val geometry: Geometry,
    val properties: RelativeLocationProperties
)

data class RelativeLocationProperties(
    val city: String,
    val state: String,
    val distance: Distance,
    val bearing: Bearing
)

data class Distance(
    val unitCode: String,
    val value: Double
)

data class Bearing(
    val unitCode: String,
    val value: Int
)

@TestOnly
fun test() {
    val json = """{
    "@context": [
        "https://geojson.org/geojson-ld/geojson-context.jsonld",
        {
            "@version": "1.1",
            "wx": "https://api.weather.gov/ontology#",
            "s": "https://schema.org/",
            "geo": "http://www.opengis.net/ont/geosparql#",
            "unit": "http://codes.wmo.int/common/unit/",
            "@vocab": "https://api.weather.gov/ontology#",
            "geometry": {
                "@id": "s:GeoCoordinates",
                "@type": "geo:wktLiteral"
            },
            "city": "s:addressLocality",
            "state": "s:addressRegion",
            "distance": {
                "@id": "s:Distance",
                "@type": "s:QuantitativeValue"
            },
            "bearing": {
                "@type": "s:QuantitativeValue"
            },
            "value": {
                "@id": "s:value"
            },
            "unitCode": {
                "@id": "s:unitCode",
                "@type": "@id"
            },
            "forecastOffice": {
                "@type": "@id"
            },
            "forecastGridData": {
                "@type": "@id"
            },
            "publicZone": {
                "@type": "@id"
            },
            "county": {
                "@type": "@id"
            }
        }
    ],
    "id": "https://api.weather.gov/points/37.2674,-122.0284",
    "type": "Feature",
    "geometry": {
        "type": "Point",
        "coordinates": [
            -122.0284,
            37.2674
        ]
    },
    "properties": {
        "@id": "https://api.weather.gov/points/37.2674,-122.0284",
        "@type": "wx:Point",
        "cwa": "MTR",
        "forecastOffice": "https://api.weather.gov/offices/MTR",
        "gridId": "MTR",
        "gridX": 94,
        "gridY": 80,
        "forecast": "https://api.weather.gov/gridpoints/MTR/94,80/forecast",
        "forecastHourly": "https://api.weather.gov/gridpoints/MTR/94,80/forecast/hourly",
        "forecastGridData": "https://api.weather.gov/gridpoints/MTR/94,80",
        "observationStations": "https://api.weather.gov/gridpoints/MTR/94,80/stations",
        "relativeLocation": {
            "type": "Feature",
            "geometry": {
                "type": "Point",
                "coordinates": [
                    -122.025413,
                    37.268493
                ]
            },
            "properties": {
                "city": "Saratoga",
                "state": "CA",
                "distance": {
                    "unitCode": "wmoUnit:m",
                    "value": 290.92368286857
                },
                "bearing": {
                    "unitCode": "wmoUnit:degree_(angle)",
                    "value": 245
                }
            }
        },
        "forecastZone": "https://api.weather.gov/zones/forecast/CAZ513",
        "county": "https://api.weather.gov/zones/county/CAC085",
        "fireWeatherZone": "https://api.weather.gov/zones/fire/CAZ513",
        "timeZone": "America/Los_Angeles",
        "radarStation": "KMUX"
    }
}"""

    //val gson = Gson()
    val weatherData: NOAAWeatherDataInfo = Gson().fromJson(json, NOAAWeatherDataInfo::class.java)

    println("ID: ${weatherData.id}")
    println("Coordinates: ${weatherData.geometry.coordinates}")
    println("City: ${weatherData.properties.relativeLocation.properties.city}")
    println("Forecast: ${weatherData.properties.forecast}")
}
