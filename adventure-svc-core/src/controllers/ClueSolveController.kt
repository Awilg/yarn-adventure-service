package com.yarn.services.controllers

import com.yarn.services.core.controllers.KodeinController
import com.yarn.services.managers.ClueManager
import com.yarn.services.core.logging.logger
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
            logger.info("Trying to solve a clue!")
            val adventureId = call.parameters["id"]
            val clueId = call.parameters["id"]
            val answer = call.receive<String>()

            adventureId?.let {
                clueId?.let {
                    call.respond(clueManager.attemptClueSolveText(adventureId, clueId, answer))
                }
            }
        }
    }
}
