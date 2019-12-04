package com.yarn.services.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.Instant
import java.util.Objects.hash

class Adventure private constructor(
	@BsonId val id: Id<Adventure> = newId(),
	val location: Location?,
	val name: String,
	val description: String,
	val creator: String?,
	val creatorId: String?,
	val createdAt: String = Instant.now().toEpochMilli().toString(),
	val expiresAt: String? = null
) {
	override fun equals(other: Any?) = other is Adventure &&
		id == other.id &&
		location == other.location &&
		name == other.name &&
		description == other.description &&
		creator == other.creator &&
		creatorId == other.creatorId &&
		createdAt == other.createdAt &&
		expiresAt == other.expiresAt

	override fun hashCode() = hash(id, name, description, creator, creatorId, createdAt, expiresAt)

	override fun toString(): String {
		return "Adventure(id=$id, location=$location, name='$name', description='$description', " +
			"creator=$creator, creatorId=$creatorId, createdAt='$createdAt', expiresAt=$expiresAt)"
	}

	class Builder {
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var location: Location? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var name: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var description: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var creator: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var creatorId: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var expiresAt: String? = null

		fun setLocation(location: Location?) = apply { this.location = location }
		fun setName(name: String?) = apply { this.name = name }
		fun setCreator(creator: String?) = apply { this.creator = creator }
		fun setDescription(description: String?) = apply { this.description = description }
		fun setCreatorId(creatorId: String?) = apply { this.creatorId = creatorId }
		fun setExpiresAt(expiresAt: String?) = apply { this.expiresAt = expiresAt }

		fun build() = Adventure(
			location = this.location,
			name = checkNotNull(this.name) { "'name' is required" },
			description = checkNotNull(this.description) { "'description' is required" },
			creator = this.creator,
			creatorId = this.creatorId,
			expiresAt = this.expiresAt
		)
	}
}

@JvmSynthetic // Hide from Java callers who should use Builder.
fun Adventure(initializer: Adventure.Builder.() -> Unit): Adventure {
	return Adventure.Builder().apply(initializer).build()
}

data class Location(
	val type: String = "Point",
	val coordinates: DoubleArray
) {
	fun getLat() = coordinates.getOrNull(1)
	fun getLng() = coordinates.getOrNull(0)

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Location

		if (type != other.type) return false
		if (!coordinates.contentEquals(other.coordinates)) return false

		return true
	}

	override fun hashCode() = hash(type, coordinates)
}
