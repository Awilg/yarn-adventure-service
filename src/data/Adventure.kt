package com.yarn.services.data

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.Instant
import java.util.Objects.hash

class Adventure private constructor(
	@BsonId val key : Id<Adventure> = newId(),
	val location: Location?,
	val name: String,
	val creator: String?,
	val creatorId: String?,
	val createdAt: String = Instant.now().toEpochMilli().toString(),
	val expiresAt: String? = null
	) {
	override fun equals(other: Any?) = other is Adventure &&
		key == other.key &&
		location == other.location &&
		name == other.name &&
		creator == other.creator &&
		creatorId == other.creatorId &&
		createdAt == other.createdAt &&
		expiresAt == other.expiresAt

	override fun hashCode() = hash(key, name, creator, creatorId, createdAt, expiresAt)

	override fun toString() = "Adventure(key=$key, location=$location, name='$name', creator='$creator'," +
		" creatorId='$creatorId', createdAt='$createdAt', expiresAt=$expiresAt)"

	class Builder {
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var location: Location? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var name: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var creator: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var creatorId: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var expiresAt: String? = null

		fun setLocation(location: Location?) = apply { this.location = location }
		fun setName(name: String?) = apply { this.name = name }
		fun setCreator(creator: String?) = apply { this.creator = creator }
		fun setCreatorId(creatorId: String?) = apply { this.creatorId = creatorId }
		fun setExpiresAt(expiresAt: String?) = apply { this.expiresAt = expiresAt }

		fun build() = Adventure(
			location = this.location,
			name = checkNotNull(this.name) { "'name' is required" },
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

class Location private constructor(
	val type : String,
	val coordinates : FloatArray
)
