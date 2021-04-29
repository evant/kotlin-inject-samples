package me.tatarka.inject

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope
import me.tatarka.inject.db.DbEpisodesDao
import me.tatarka.inject.db.DbSongsDao
import me.tatarka.inject.db.EpisodesDao
import me.tatarka.inject.db.SongsDao
import org.jetbrains.exposed.sql.Database

/**
 * The application-level scope. There will only be one instance of anything annotated with this.
 */
@Scope
annotation class ApplicationScope

/**
 * The main application component. There will only be a single instance of this.
 */
@Component
@ApplicationScope
abstract class ApplicationComponent(private val db: String) {
    abstract val app: app

    val database: Database
        @Provides @ApplicationScope get() {
            val cfg: HikariConfig = HikariConfig().apply {
                jdbcUrl = db
                maximumPoolSize = 6
            }
            val dataSource = HikariDataSource(cfg)
            return Database.connect(dataSource)
        }

    val DbEpisodesDao.bind: EpisodesDao
        @Provides get() = this

    val DbSongsDao.bind: SongsDao
        @Provides get() = this
}