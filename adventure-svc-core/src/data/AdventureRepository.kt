package com.yarn.services.data

import com.yarn.services.core.data.BaseDaoAsync
import com.yarn.services.models.Adventure
import org.litote.kmongo.MongoOperator
import org.litote.kmongo.coroutine.CoroutineCollection

class AdventureRepository(private val collection: CoroutineCollection<Adventure>) : BaseDaoAsync<Adventure>(collection) {

	//47.6062° N, 122.3321° W
	suspend fun findByLocation(latitude : Double, longitude: Double, minDistance : Double, maxDistance : Double): List<Adventure> {

		val results = collection.find(
			"{ " +
				"location: {" +
					"${MongoOperator.nearSphere}:{" +
						"${MongoOperator.geometry}:{" +
							"type: \"Point\"," +
							"coordinates: [${longitude}, ${latitude}], }," +
						"\$maxDistance: $maxDistance," +
						"\$minDistance: $minDistance," +
					"}," +
				"}," +
				"}"
		)

		return results.toList()
	}
}
