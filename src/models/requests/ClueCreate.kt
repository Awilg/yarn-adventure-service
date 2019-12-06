package com.yarn.services.models.requests

import com.yarn.services.models.Clue

open class ClueCreate(
	open val cluePrompt: String,
	open val type: String
)

fun ClueCreate.toClue() : Clue {
	return Clue(
		cluePrompt = this.cluePrompt,
		type = this.type)
}
