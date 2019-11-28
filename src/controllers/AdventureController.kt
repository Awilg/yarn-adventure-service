package com.yarn.services.controllers

import com.yarn.services.core.controllers.KodeinController
import com.yarn.services.core.logging.logger
import com.yarn.services.data.Adventure
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import org.kodein.di.Kodein

class AdventureController(kodein: Kodein) : KodeinController(kodein) {

	override fun Routing.registerRoutes() {
		get("/adventure") {
			logger.info("Calling an adventure!")

			call.respond(Adventure {
				name = "testAdventure"
				creator = "James"
			})
		}

		post("/adventure") {
			val adventure = call.receive<Adventure>()

			call.respondText("Adventure Created!")
		}
	}
}
