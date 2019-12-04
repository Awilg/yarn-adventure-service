package com.yarn.services.models.response

import com.yarn.services.models.Adventure
import com.yarn.services.models.LatLng
import java.util.*


class AdventureInfo private constructor(
	val id: String,
	val location: LatLng?,
	val name: String?,
	val creator: String?,
	val creatorId: String?,
	val createdAt: String?,
	val expiresAt: String?
) {
	override fun equals(other: Any?) = other is AdventureInfo &&
		id == other.id &&
		location == other.location &&
		name == other.name &&
		creator == other.creator &&
		creatorId == other.creatorId &&
		createdAt == other.createdAt &&
		expiresAt == other.expiresAt

	override fun hashCode() = Objects.hash(id, name, creator, creatorId, createdAt, expiresAt)

	override fun toString() = "Adventure(key=$id, location=$location, name='$name', creator='$creator'," +
		" creatorId='$creatorId', createdAt='$createdAt', expiresAt=$expiresAt)"

	class Builder {
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var id: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var location: LatLng? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var name: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var creator: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var creatorId: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var createdAt: String? = null
		@set:JvmSynthetic // Hide 'void' setter from Java.
		var expiresAt: String? = null

		fun setId(id: String?) = apply { this.id = id }
		fun setLocation(location: LatLng?) = apply { this.location = location }
		fun setName(name: String?) = apply { this.name = name }
		fun setCreator(creator: String?) = apply { this.creator = creator }
		fun setCreatorId(creatorId: String?) = apply { this.creatorId = creatorId }
		fun setCreatedAt(createdAt: String?) = apply { this.createdAt = createdAt }
		fun setExpiresAt(expiresAt: String?) = apply { this.expiresAt = expiresAt }

		fun build() = AdventureInfo(
			id = checkNotNull(this.id) { "An adventure 'id' is required" },
			location = this.location,
			name = this.name,
			creator = this.creator,
			creatorId = this.creatorId,
			createdAt = this.createdAt,
			expiresAt = this.expiresAt
		)
	}
}

@JvmSynthetic // Hide from Java callers who should use Builder.
fun AdventureInfo(initializer: AdventureInfo.Builder.() -> Unit): AdventureInfo {
	return AdventureInfo.Builder().apply(initializer).build()
}

fun Adventure.toInfo() =
	AdventureInfo {
		id = this@toInfo.id.toString()
		location = this@toInfo.location?.let { loc -> loc.getLat()?.let { LatLng(it, loc.getLng()!!) } }
		name = this@toInfo.name
		createdAt = this@toInfo.createdAt
		creatorId = this@toInfo.creatorId
		creator = this@toInfo.creator
		expiresAt = this@toInfo.expiresAt
	}


