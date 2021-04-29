/**
 * Tables and entities to access the db using exposed.
 */
package me.tatarka.inject.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.date
import java.time.LocalDate

/**
 * The episodes table.
 */
object Episodes : IntIdTable("episodes", "EpisodeId") {
    val name = text("Name")
    val imageId = reference("ImageID", Images)
    val season = integer("Season").nullable()
    val episode = integer("Episode").nullable()
    val overallEpisode = integer("OverallEpisode").nullable()
    val airdate = date("Airdate")
    val writtenBy = text("WrittenBy").nullable()
    val storyboard = text("Storyboard").nullable()
}

/**
 * The images table.
 */
object Images : IntIdTable("images", "ImageId") {
    val url = text("Url")
}

/**
 * The songs table.
 */
object Songs : IntIdTable("songs", "SongId") {
    val name = text("Name")
    val episodeId = reference("EpisodeId", Episodes)
}

/**
 * Represents a single row in the episodes table.
 */
class DbEpisodeEntity(id: EntityID<Int>) : IntEntity(id), EpisodeEntity {
    companion object : IntEntityClass<DbEpisodeEntity>(Episodes)

    override val episodeId: Int get() = id.value
    override val name: String by Episodes.name
    override val imageUrl: String by Images.url
    override val season: Int? by Episodes.season
    override val episode: Int? by Episodes.episode
    override val overallEpisode: Int? by Episodes.overallEpisode
    override val airdate: LocalDate by Episodes.airdate
    override val writtenBy: String? by Episodes.writtenBy
    override val storyboard: String? by Episodes.storyboard
}

/**
 * Represents a single row in the songs table.
 */
class DbSongEntity(id: EntityID<Int>) : IntEntity(id), SongEntity {
    companion object : IntEntityClass<DbSongEntity>(Songs)

    override val episodeId: Int
        get() = Songs.episodeId.lookup().value

    override val songId: Int
        get() = id.value

    override val name: String by Songs.name
}

/**
 * An episode entity, this uses an interface to allow a fake implementation for tests.
 */
interface EpisodeEntity {
    val episodeId: Int
    val name: String
    val imageUrl: String
    val season: Int?
    val episode: Int?
    val overallEpisode: Int?
    val airdate: LocalDate
    val writtenBy: String?
    val storyboard: String?
}

/**
 * A song entity, this uses an interface to allow a fake implementation for tests.
 */
interface SongEntity {
    val episodeId: Int
    val songId: Int
    val name: String
}