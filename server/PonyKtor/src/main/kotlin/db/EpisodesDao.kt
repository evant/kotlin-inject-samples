package me.tatarka.inject.db

import me.tatarka.inject.ApplicationScope
import me.tatarka.inject.annotations.Inject
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Fetches episodes from the db, uses an interface to allow a fake implementation for tests.
 */
interface EpisodesDao {
    /**
     * Returns a single episode entity by id, or null if it cannot be found.
     */
    fun byId(id: Int): EpisodeEntity?

    /**
     * Returns a single episode entity by overall episode number, or null if it cannot be found.
     */
    fun byOverall(number: Int): EpisodeEntity?

    /**
     * Returns a single episode entity by season and episode number, or null if it cannot be found.
     */
    fun bySeason(season: Int, episode: Int): EpisodeEntity?

    /**
     * Returns all episode entities within the given limit & offset.
     */
    fun all(offset: Int, limit: Int): List<EpisodeEntity>
}

/**
 * An implementation of [EpisodesDao] that actually fetches from the database using exposed.
 */
@Inject
@ApplicationScope
class DbEpisodesDao(private val db: Database) : EpisodesDao {
    override fun byId(id: Int): EpisodeEntity? {
        return by { Episodes.id eq id }
    }

    override fun byOverall(number: Int): EpisodeEntity? {
        return by { Episodes.overallEpisode eq number }
    }

    override fun bySeason(season: Int, episode: Int): EpisodeEntity? {
        return by { (Episodes.season eq season) and (Episodes.episode eq episode) }
    }

    private fun by(where: SqlExpressionBuilder.() -> Op<Boolean>): EpisodeEntity? {
        return transaction(db) {
            episodesWithImages()
                .select(where)
                .limit(1)
                .firstOrNull()
                ?.let { DbEpisodeEntity.wrapRow(it) }
        }
    }

    override fun all(offset: Int, limit: Int): List<EpisodeEntity> {
        return transaction(db) {
            episodesWithImages()
                .selectAll()
                .limit(limit, offset.toLong())
                .let { DbEpisodeEntity.wrapRows(it) }
                .toList()
        }
    }

    private fun Transaction.episodesWithImages() = Episodes.innerJoin(Images)
        .slice(Episodes.columns + Images.url)
}