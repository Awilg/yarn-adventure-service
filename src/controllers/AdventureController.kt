package com.yarn.services.controllers

import com.yarn.services.core.controllers.KodeinController
import com.yarn.services.core.logging.logger
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.kodein.di.Kodein

class AdventureController(kodein: Kodein) : KodeinController(kodein) {

	override fun Routing.registerRoutes() {
		get("/adventure") {
			logger.info("Calling an adventure!")
			call.respondText("An Adventure!")
		}	}
}
