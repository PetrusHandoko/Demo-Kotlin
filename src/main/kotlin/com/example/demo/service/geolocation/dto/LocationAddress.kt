package com.example.demo.service.geolocation.dto


import com.fasterxml.jackson.annotation.JsonInclude
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.TestOnly

@JsonInclude(JsonInclude.Include.NON_NULL)
@JvmRecord
data class GeoData(
    val result: Result
)

@JvmRecord
data class Result(
    val input: Input,
    val addressMatches: List<AddressMatch>
)

@JvmRecord
data class Input(
    val address: Address,
    val benchmark: Benchmark
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JvmRecord
data class Address(
    val zip: String,
    val city: String,
    val street: String,
    val state: String
)

@JvmRecord
data class Benchmark(
    val isDefault: Boolean,
    val benchmarkDescription: String,
    val id: String,
    val benchmarkName: String
)

@JvmRecord
data class AddressMatch(
    val tigerLine: TigerLine,
    val coordinates: Coordinates,
    val addressComponents: AddressComponents,
    val matchedAddress: String
)

@JvmRecord
data class TigerLine(
    val side: String,
    val tigerLineId: String
)

@JvmRecord
data class Coordinates(
    val x: Double,
    val y: Double
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JvmRecord
data class AddressComponents(
    val zip: String,
    val streetName: String,
    val preType: String,
    val city: String,
    val preDirection: String,
    val suffixDirection: String,
    val fromAddress: String,
    val state: String,
    val suffixType: String,
    val toAddress: String,
    val suffixQualifier: String,
    val preQualifier: String
)

@JvmRecord
data class LocationAddress(
    val context: List<Any>, // To handle mixed types (URIs and objects)
    val id: String,
    val type: String,
    val geometry: Geometry,
    val properties: Properties
)

@JvmRecord
data class Geometry(
    val type: String,
    val coordinates: List<Double>
)

@JvmRecord
data class Properties(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
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

@JvmRecord
data class RelativeLocation(
    val type: String,
    val geometry: Geometry,
    val properties: RelativeLocationProperties
)

@JvmRecord
data class RelativeLocationProperties(
    val city: String,
    val state: String,
    val distance: Distance,
    val bearing: Bearing
)

@JvmRecord
data class Distance(
    val unitCode: String,
    val value: Double
)

@JvmRecord
data class Bearing(
    val unitCode: String,
    val value: Int
)

@TestOnly
fun test() {
    val json = """
        {
          "result" : {
            "input" : {
              "address" : {
                "address" : "20289 Herriman Ave, Saratoga, CA 95070"
              },
              "benchmark" : {
                "isDefault" : true,
                "benchmarkDescription" : "Public Address Ranges - Current Benchmark",
                "id" : "4",
                "benchmarkName" : "Public_AR_Current"
              }
            },
            "addressMatches" : [ {
              "tigerLine" : {
                "side" : "R",
                "tigerLineId" : "614043446"
              },
              "coordinates" : {
                "x" : -122.028180237003,
                "y" : 37.267391699039
              },
              "addressComponents" : {
                "zip" : "95070",
                "streetName" : "HERRIMAN",
                "preType" : "",
                "city" : "SARATOGA",
                "preDirection" : "",
                "suffixDirection" : "",
                "fromAddress" : "20201",
                "state" : "CA",
                "suffixType" : "AVE",
                "toAddress" : "20323",
                "suffixQualifier" : "",
                "preQualifier" : ""
              },
              "matchedAddress" : "20289 HERRIMAN AVE, SARATOGA, CA, 95070"
            } ]
          }
        }
    """.trimIndent()
    val json1 = """{
        "result":{
            "input": {
                "address": {
                    "zip": "20233",
                    "city": "Washington",
                    "street": "4600 Silver Hill Rd",
                    "state": "DC"
                },
                "benchmark": {
                    "isDefault": true,
                    "benchmarkDescription": "Public Address Ranges - Current Benchmark",
                    "id": "4",
                    "benchmarkName": "Public_AR_Current"
                }
            },
            "addressMatches": [
                {
                    "tigerLine": {
                        "side": "L",
                        "tigerLineId": "76355984"
                    },
                    "coordinates": {
                        "x": -76.927487242301,
                        "y": 38.846016223866
                    },
                    "addressComponents": {
                        "zip": "20233",
                        "streetName": "SILVER HILL",
                        "preType": "",
                        "city": "WASHINGTON",
                        "preDirection": "",
                        "suffixDirection": "",
                        "fromAddress": "4600",
                        "state": "DC",
                        "suffixType": "RD",
                        "toAddress": "4700",
                        "suffixQualifier": "",
                        "preQualifier": ""
                    },
                    "matchedAddress": "4600 SILVER HILL RD, WASHINGTON, DC, 20233"
                }
            ]
        }
    }"""

    // Deserialize JSON to GeoData
    val geoData: GeoData = Gson().fromJson(json, GeoData::class.java)

    println("City: ${geoData.result.addressMatches[0].addressComponents.city}")
    println("Matched Address: ${geoData.result.addressMatches[0].matchedAddress}")
    println("Coordinates: x=${geoData.result.addressMatches[0].coordinates}")
}
