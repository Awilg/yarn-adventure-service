package com.yarn.services

import com.fasterxml.jackson.databind.SerializationFeature
import com.yarn.services.controllers.AdventureController
import com.yarn.services.controllers.ClueSolveController
import com.yarn.services.core.kodein.bindSingleton
import com.yarn.services.core.kodein.kodeinApplication
import com.yarn.services.data.AdventureInProgress
import com.yarn.services.data.AdventureRepository
import com.yarn.services.models.Adventure
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.jackson.jackson
import io.ktor.request.path
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.coroutines.launch
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.id.jackson.IdJacksonModule
import org.litote.kmongo.reactivestreams.KMongo
import org.slf4j.event.Level
import com.yarn.services.managers.ClueManager as ClueManager

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
	install(CallLogging) {
		level = Level.INFO
		filter { call -> call.request.path().startsWith("/") }
	}

	install(DefaultHeaders) {
		header("X-Engine", "Ktor") // will send this header with each response
	}

	install(Authentication) {
	}

	install(ContentNegotiation) {
		jackson {
			enable(SerializationFeature.INDENT_OUTPUT)
			registerModule(IdJacksonModule())
		}
	}

	// Connect to the DB
	// think this uses "mongo" for the docker container
	val client = KMongo.createClient("mongodb://127.0.0.1:27017").coroutine //use coroutine extension
	val database = client.getDatabase("Yarn-User")
	val adventureCollection = database.getCollection<Adventure>()
	launch {
		adventureCollection.createIndex("{\"location\" : \"2dsphere\" }" )
	}
	val adventureInProgressCollection = database.getCollection<AdventureInProgress>()


	kodeinApplication {
		bind<AdventureRepository>() with singleton { AdventureRepository(adventureCollection) }
		bindSingleton { AdventureController(it) }
		bindSingleton { ClueSolveController(it) }
		bindSingleton { ClueManager(it) }

	}

	routing {
		get("/") {
			call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
		}
	}
}
