package com.yarn.services.models.response

import com.yarn.services.models.Clue

open class ClueInfo(
	open val cluePrompt: String,
	open val type: String
)

fun Clue.toInfo() =
	ClueInfo(cluePrompt = this.cluePrompt, type = this.type)
