package com.yarn.services

import com.fasterxml.jackson.databind.SerializationFeature
import com.yarn.services.controllers.AdventureController
import com.yarn.services.core.kodein.bindSingleton
import com.yarn.services.core.kodein.kodeinApplication
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
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
		}
	}

	kodeinApplication {
		bindSingleton { AdventureController(it) }
	}

	routing {
		get("/") {
			call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
		}

		install(StatusPages) {
			exception<AuthenticationException> {
				call.respond(HttpStatusCode.Unauthorized)
			}
			exception<AuthorizationException> {
				call.respond(HttpStatusCode.Forbidden)
			}

		}
	}
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

