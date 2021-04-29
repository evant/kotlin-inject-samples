import db.FakeEpisodesDao
import db.FakeSongsDao
import me.tatarka.inject.ApplicationScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.db.EpisodesDao
import me.tatarka.inject.db.SongsDao

class TestFakes(
    @get:Provides val episodesDao: EpisodesDao = FakeEpisodesDao(),
    @get:Provides val songsDao: SongsDao = FakeSongsDao(),
)

@Component
@ApplicationScope
abstract class TestApplicationComponent(@Component val fakes: TestFakes = TestFakes())