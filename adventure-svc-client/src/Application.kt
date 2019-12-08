package com.yarn.adventure

import com.yarn.services.models.response.AdventureInfo
import io.ktor.application.Application
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.BrowserUserAgent
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.auth.providers.basic
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
	val client = HttpClient(Apache) {
		install(Auth) {
			this.basic {
				username = "test"
				password = "pass"
			}
		}
		install(JsonFeature) {
			serializer = GsonSerializer()
		}
		install(Logging) {
			level = LogLevel.HEADERS
		}
		BrowserUserAgent() // install default browser-like user-agent
		// install(UserAgent) { agent = "some user agent" }
	}
	runBlocking {
		val message = client.get<AdventureInfo> {
			url("http://127.0.0.1:8080/adventure/5dec09bb38f8ce535826e646")
			contentType(ContentType.Application.Json)
		}
	}

}

data class JsonSampleClass(val hello: String)

