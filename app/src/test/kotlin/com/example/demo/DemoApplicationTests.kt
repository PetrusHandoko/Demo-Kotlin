package com.example.demo

import com.example.demo.controller.WeatherAppController
import com.example.demo.dto.USAddress
import com.example.demo.dto.toFormattedString
import com.example.demo.service.geolocation.GeoLocationService
import com.example.demo.service.geolocation.dto.GeoData
import com.fasterxml.jackson.databind.JsonSerializer.None
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.random.Random


@SpringBootTest
class DemoApplicationTests {

	@Autowired
	lateinit var geo: GeoLocationService

	@Autowired
	lateinit var gson: Gson

	@Autowired
	lateinit var controller: WeatherAppController

	fun getRandomValue(): Int {
		return Random(8886).nextInt(150)
	}

	fun getXValue(y: Int = getRandomValue()) = if (5 < y) y else 5



	fun printType ( input : Any ) =when {
		input is String -> println("This is a string type input")
		else -> println("This is object other than String class")
	}

	fun process(x: Int?): Int? {
		println("Value: " + x.toString()) // not-null assertion operator !!
		val y =  x?.plus(10) ?: 6     // If x is not null add 10 to x
		return y
	}

	fun processNonNull(x: Int?): Int {
		println("Value: ${x}")
		if (x != null) {
			return x + 10
		}    // If x is not null add 10 to x
		return 0
	}

	fun printNonNullValue( param1: Int ): Unit{
		val y = param1 + 10
		println("Y = $y")
		// Smart cast changed param1 to non-nullable variable
		println("Y ="+processNonNull(param1))

	}





	@Test
	fun contextLoads()  {

		val y = process(20)
		val y2 = process(null)

		if (y != null) {
			printType( y )
		}

		val f : ()-> Int = {
			val yy = getRandomValue()
			yy
		}
		val x = {
			val y = getRandomValue()
			val yy = if( y>100 ) y/100 else y
			// More processing
			yy
		}()

		// some computation
		val xx = if(5 < getRandomValue()) getRandomValue() else 5


		var addr = USAddress("20289 Herriman Ave", "Saratoga", "CA", "95070")
		val addr2 = USAddress("1234 Any St", "Anytown", "NY", "12345")
		println( addr.toFormattedString() )
		val result = controller.processPostWeatherInfo(arrayOf(addr))
		println(result)


		val json  = """
		{
        "result": {
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
    }
    """

		val mapper = ObjectMapper()
		val res = mapper.readValue(json, GeoData::class.java)
		println("HEre $res")
	}


}
fun throwIt (){
	throw Exception("Error")
}
fun printCheckIntStringType ( input : Any ) = when (input) {
	is String -> println("This is a string type input")
	is Int	-> println("This is a Int type input")
	is None -> println("None type ")
	else -> println(" Type: " + input )
}

fun printCheckIntStringType2 ( input : Any ): Unit = when  {
	input is String -> println("This is a string type input")
	input is Int	-> println("This is a Int type input")
	input is Double && input < 2.0 -> println("This is a Double less than 2.0")
	else -> println(" Type: " + input.javaClass)
}

fun main (){

	printCheckIntStringType( 90 )
	printCheckIntStringType( "None" )
	printCheckIntStringType2( 1.0 )
	printCheckIntStringType2( 19.0f )
}
