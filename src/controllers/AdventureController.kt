package com.yarn.services.controllers

import com.yarn.services.core.controllers.KodeinController
import com.yarn.services.core.logging.logger
import com.yarn.services.data.AdventureRepository
import com.yarn.services.models.Adventure
import com.yarn.services.models.requests.AdventureCreate
import com.yarn.services.models.requests.toAdventure
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class AdventureController(kodein: Kodein) : KodeinController(kodein) {
	private val adventureDao : AdventureRepository by instance()

	override fun Routing.registerRoutes() {
		get("/adventure/{id}") {
			logger.info("Calling an adventure!")
			val adventureId = call.parameters["id"]

			val adventure = adventureId?.let { id -> adventureDao.get(id) }
			adventure?.let { a -> call.respond(a) }
		}

		post("/adventure") {
			val toCreate = call.receive<AdventureCreate>()
			val newAdventure = toCreate.toAdventure()
			adventureDao.save(newAdventure)
			call.respond(newAdventure)
		}
	}
}
