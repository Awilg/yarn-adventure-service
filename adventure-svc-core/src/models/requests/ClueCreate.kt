package com.yarn.services.models.requests

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.yarn.services.models.*

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "type"
)
@JsonSubTypes(
	JsonSubTypes.Type(value = ClueTextCreate::class, name = "Text"),
	JsonSubTypes.Type(value = ClueLocationCreate::class, name = "Location")
)
interface ClueCreate {
	val cluePrompt: String
	val hints: List<String>?
}

fun ClueCreate.toClue(): IClue {
	when (this) {
		is ClueTextCreate -> {
			return ClueText(
				cluePrompt = this.cluePrompt,
				type = ClueType.Text.name,
				hints = this.hints,
				answer = this.answer
			)
		}
		is ClueLocationCreate -> {
			return ClueLocation(
				cluePrompt = this.cluePrompt,
				type = ClueType.Location.name,
				hints = this.hints,
				location = this.location
			)
		}
		else -> {
			return ClueText(
				cluePrompt = this.cluePrompt,
				type = ClueType.Text.name,
				hints = this.hints,
				answer = "ELSE"
			)
		}
	}
}

@JsonTypeName("Text")
class ClueTextCreate(
	override val cluePrompt: String,
	override val hints: List<String>? = emptyList(),
	val answer: String
) : ClueCreate

@JsonTypeName("Location")
class ClueLocationCreate(
	override val cluePrompt: String,
	override val hints: List<String>? = emptyList(),
	val location: LatLng
) : ClueCreate
