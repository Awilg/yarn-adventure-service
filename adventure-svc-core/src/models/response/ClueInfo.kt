package com.yarn.services.models.response

import com.yarn.services.models.*

interface ClueInfo {
	val id: String
	val cluePrompt: String
	val type: String
	val hints: List<String>?
}

abstract class BaseClueInfo(
	override val id: String,
	override val cluePrompt: String,
	override val type: String = ClueType.Text.name,
	override val hints: List<String>?
) : ClueInfo

class ClueTextInfo(
	override val id: String,
	override val cluePrompt: String,
	override val type: String = ClueType.Text.name,
	override val hints: List<String>?
) : BaseClueInfo(id = id, cluePrompt = cluePrompt, type = type, hints = hints)

class ClueLocationInfo(
	override val id: String,
	override val cluePrompt: String,
	override val type: String = ClueType.Location.name,
	override val hints: List<String>?
) : BaseClueInfo(id = id, cluePrompt = cluePrompt, type = type, hints = hints)

fun IClue.toInfo(): ClueInfo {
	return when (this) {
		is ClueText -> {
			ClueTextInfo(id = this.id.toString(), cluePrompt = this.cluePrompt, hints = this.hints)
		}
		is ClueLocation -> {
			ClueLocationInfo(id = this.id.toString(), cluePrompt = this.cluePrompt, hints = this.hints)
		}
		else -> {
			ClueTextInfo(id = "testId", cluePrompt = this.cluePrompt, hints = this.hints)
		}
	}
}

