package com.yarn.services.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

open class Clue(
	@BsonId val id: Id<Clue> = newId(),
	open val cluePrompt: String,
	open val type: String,
	open var state: String = ClueState.LOCKED.name,
	open val hints: List<String>? = null
)

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

class ClueText(
	override val cluePrompt: String,
	override val type: String = ClueType.Text.name,
	override val hints: List<String>?,
	val answer: String
) : Clue(cluePrompt = cluePrompt, type = type, hints = hints)


class ClueLocation(
	override val cluePrompt: String,
	override val type: String = ClueType.Location.name,
	override val hints: List<String>?,
	val location: LatLng
) : Clue(cluePrompt = cluePrompt, type = type, hints = hints)
