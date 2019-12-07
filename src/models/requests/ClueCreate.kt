package com.yarn.services.models.requests

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.yarn.services.models.Clue
import com.yarn.services.models.LatLng


@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "type")
@JsonSubTypes(
	JsonSubTypes.Type(value = ClueTextCreate::class, name = "text"),
	JsonSubTypes.Type(value = ClueLocationCreate::class, name = "location")
)
abstract class ClueCreate(
	open val cluePrompt: String,
	open val hints: List<String>? = emptyList()
)

fun ClueCreate.toClue() : Clue {
	return Clue(
		cluePrompt = this.cluePrompt, type = "umm")
}

@JsonTypeName("text")
class ClueTextCreate(
	override val cluePrompt: String,
	override val hints: List<String>? = emptyList(),
	val answer: String
) : ClueCreate(cluePrompt = cluePrompt, hints = hints)

@JsonTypeName("location")
class ClueLocationCreate(
	override val cluePrompt: String,
	override val hints: List<String>? = emptyList(),
	val location: LatLng
) : ClueCreate(cluePrompt = cluePrompt, hints = hints)
