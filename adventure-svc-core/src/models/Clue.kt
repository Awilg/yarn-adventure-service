package com.yarn.services.models

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.yarn.services.models.requests.ClueLocationCreate
import com.yarn.services.models.requests.ClueTextCreate
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId


@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)
@JsonSubTypes(
		JsonSubTypes.Type(value = ClueText::class, name = "Text"),
		JsonSubTypes.Type(value = ClueLocation::class, name = "Location")
)
interface IClue {
	val id: Id<BaseClue>
	val cluePrompt: String
	val type: String
	val hints: List<String>?
}

abstract class BaseClue(
	@BsonId override val id: Id<BaseClue> = newId(),
	override val cluePrompt: String,
	override val type: String = ClueType.Text.name,
	override val hints: List<String>?
) : IClue

class ClueText(
	override val cluePrompt: String,
	override val type: String = ClueType.Text.name,
	override val hints: List<String>?,
	val answer: String
) : BaseClue(cluePrompt = cluePrompt, type = type, hints = hints)


class ClueLocation(
	override val cluePrompt: String,
	override val type: String = ClueType.Location.name,
	override val hints: List<String>?,
	val location: LatLng
) : BaseClue(cluePrompt = cluePrompt, type = type, hints = hints)


enum class ClueType {
	Location,
	Photo,
	Audio,
	Text,
	Treasure
}

enum class ClueState {
	LOCKED,
	ACTIVE,
	COMPLETED
}
