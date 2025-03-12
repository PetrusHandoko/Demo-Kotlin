package com.example.demo.controller

import com.example.demo.dto.USAddress
import com.example.demo.dto.WeatherDataInfo
import com.example.demo.service.WeatherDataService
import org.springframework.web.bind.annotation.*

@RestController
class WeatherAppController(
    private val weatherDataService: WeatherDataService
) {
    @PostMapping("/weatherData")
    fun processPostWeatherInfo(@RequestBody address: Array<USAddress> ): List<Any> {

        return address.map {
            object {
                val zip = it.zip
                val data = weatherDataService.getWeatherDataInfo(it)
            }
        }
    }

    @GetMapping("/weatherData")
    fun getWeatherInfo(@RequestParam street:String,
                       @RequestParam city: String,
                       @RequestParam state:String,
                       @RequestParam zip: String): WeatherDataInfo {
        return weatherDataService.getWeatherDataInfo(USAddress(street=street, city = city, state = state, zip=zip))
    }

}