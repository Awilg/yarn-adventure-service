package com.yarn.services.controllers

import com.yarn.services.core.controllers.KodeinController
import com.yarn.services.managers.ClueManager
import com.yarn.services.core.logging.logger
import com.yarn.services.models.LatLng
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ClueSolveController(kodein: Kodein) : KodeinController(kodein) {
    private val clueManager: ClueManager by instance<ClueManager>()

    override fun Routing.registerRoutes() {
        post("/adventure/{id}/solve/{clueId}/text") {
            logger.info("Trying to solve a text clue!")
            val adventureId = call.parameters["id"]
            val clueId = call.parameters["clueId"]
            val answer = call.receive<String>()

            adventureId?.let {
                clueId?.let {
                    call.respond(clueManager.attemptClueSolveText(adventureId, clueId, answer))
                }
            }
        }  

        post("/adventure/{id}/solve/{clueId}/location") {
            logger.info("Trying to solve a location clue!")
            val adventureId = call.parameters["id"]
            val clueId = call.parameters["clueId"]
            val location = call.receive<LatLng>()

            adventureId?.let {
                clueId?.let {
                    call.respond(clueManager.attemptClueSolveLocation(adventureId, clueId, location))
                }
            }
        }
    }
}
