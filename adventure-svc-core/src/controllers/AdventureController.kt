package com.yarn.services.controllers

import com.yarn.services.core.controllers.KodeinController
import com.yarn.services.core.logging.logger
import com.yarn.services.data.AdventureRepository
import com.yarn.services.models.requests.AdventureCreate
import com.yarn.services.models.requests.FindAdventuresByLocationRequest
import com.yarn.services.models.requests.toAdventure
import com.yarn.services.models.response.toInfo
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class AdventureController(kodein: Kodein) : KodeinController(kodein) {
	private val adventureDao: AdventureRepository by instance<AdventureRepository>()

	override fun Routing.registerRoutes() {
		get("/adventure/{id}") {
			logger.info("Calling an adventure!")
			val adventureId = call.parameters["id"]

			val adventure = adventureId?.let { id -> adventureDao.get(id) }
			adventure?.let { a -> call.respond(a.toInfo()) }
		}

		get("/adventure/featured") {
			logger.info("Fetching the featured adventure!")

			val lat = call.parameters["latitude"]
			val lon = call.parameters["longitude"]

			if (lat != null && lon != null) {
				val result = adventureDao.findByLocation(
						lat.toDouble(),
						lon.toDouble(),
						0.0,
						100000.0
				).map { adv -> adv.toInfo() }[0]
				call.respond(result)
			} else {
				//return default
				call.respond("TODO: default")
			}
		}

		post("/adventure") {
			val toCreate = call.receive<AdventureCreate>()
			val newAdventure = toCreate.toAdventure()

			adventureDao.save(newAdventure)
			call.respond(newAdventure.toInfo())
		}

		post("/adventure/findNearby") {
			val request = call.receive<FindAdventuresByLocationRequest>()

			val results = adventureDao.findByLocation(
				request.latitude,
				request.longitude,
				request.minDistance,
				request.maxDistance
			).map { adv -> adv.toInfo() }
			call.respond(results)
		}
	}
}
