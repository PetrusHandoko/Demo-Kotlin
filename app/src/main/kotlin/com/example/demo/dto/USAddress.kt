package com.example.demo.dto


class USAddress(val street: String, val city: String, val state: String, val zip: String)

fun USAddress.toFormattedString(): String = toStreetAddress()+", $city, $state $zip"
fun USAddress.toStreetAddress(separator: String ="+"): String = street.replace(" ", separator)
