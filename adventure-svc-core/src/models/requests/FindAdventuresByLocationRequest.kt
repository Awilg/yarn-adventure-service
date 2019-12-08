package com.yarn.services.models.requests

import com.yarn.services.models.Latitude
import com.yarn.services.models.Longitude

data class FindAdventuresByLocationRequest(
	val latitude: Latitude,
	val longitude: Longitude,
	val minDistance: Double = 0.0,
	val maxDistance: Double = Double.MAX_VALUE
	)
