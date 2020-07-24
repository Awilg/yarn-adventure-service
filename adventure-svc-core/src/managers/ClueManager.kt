package com.yarn.services.managers

import com.yarn.services.data.AdventureRepository
import com.yarn.services.models.ClueText
import com.yarn.services.models.LatLng
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ClueManager(override val kodein: Kodein) : KodeinAware {
    private val adventureDao: AdventureRepository by instance<AdventureRepository>()


    fun createClue() {

    }

    fun updateClue() {

    }

    suspend fun attemptClueSolveText(adventureId: String, clueId:String, answer:String): Boolean {
        val adventure = adventureId.let { id -> adventureDao.get(id) }

        var clue = adventure?.clues?.stream()?.filter {
            clue -> clue.id.toString() == clueId
        }?.findFirst()?.get()

        if (clue != null) {
            clue = clue as ClueText
            return clue.answer == answer
        }
        return false
    }

    suspend fun attemptClueSolveImage() {

    }

    suspend fun attemptClueSolveLocation(adventureId: String, clueId:String, location:LatLng) {

    }
}