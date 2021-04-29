package me.tatarka.inject.db

import me.tatarka.inject.ApplicationScope
import me.tatarka.inject.annotations.Inject
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Fetches songs from the db, uses an interface to allow a fake implementation for tests.
 */
interface SongsDao {

    /**
     * Returns all songs for the given episode id.
     */
    fun forEpisode(id: Int): List<SongEntity>

    /**
     * Returns all songs for the episodes within the limit & offset.
     */
    fun forEpisodes(offset: Int, limit: Int): List<SongEntity>
}

/**
 * An implementation of [SongsDao] that actually fetches from the database using exposed.
 */
@Inject
@ApplicationScope
class DbSongsDao(private val db: Database) : SongsDao {
    override fun forEpisode(id: Int): List<SongEntity> {
        return transaction(db) {
            DbSongEntity.find { Songs.episodeId eq id }.toList()
        }
    }

    override fun forEpisodes(offset: Int, limit: Int): List<SongEntity> {
        return transaction(db) {
            val filteredEpisodes = Episodes
                .slice(Episodes.id)
                .selectAll()
                .limit(limit, offset.toLong())
                .alias("E")
            val episodeId = filteredEpisodes[Episodes.id]
            filteredEpisodes
                .innerJoin(Songs, onColumn = { episodeId }, otherColumn = { Songs.episodeId })
                .slice(Songs.columns)
                .selectAll()
                .let { DbSongEntity.wrapRows(it) }
                .toList()
        }
    }
}