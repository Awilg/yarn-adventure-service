package com.yarn.services.controllers

import com.yarn.services.core.controllers.KodeinController
import com.yarn.services.core.logging.logger
import com.yarn.services.data.AdventureRepository
import com.yarn.services.models.Adventure
import io.ktor.application.call
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
		get("/adventure") {
			logger.info("Calling an adventure!")

			call.respond(Adventure {
				name = "testAdventure"
				creator = "James"
			})
		}

		post("/adventure") {
			adventureDao.save(Adventure {
				name = "testAdventure"
				creator = "James"
			})
			call.respondText("Adventure Created!")
		}
	}
}
