package com.yarn.services.models.requests

import com.yarn.services.models.Adventure
import com.yarn.services.models.LatLng
import com.yarn.services.models.Location

data class AdventureCreate(
	val name: String,
	val location: LatLng,
	val description: String
)

fun AdventureCreate.toAdventure(): Adventure {
	return Adventure {
		name = this@toAdventure.name
		location = Location(
			coordinates = doubleArrayOf(
				this@toAdventure.location.longitude,
				this@toAdventure.location.latitude
			)
		)
		description = this@toAdventure.description
	}
}
