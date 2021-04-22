package me.tatarka.inject.ponyinject

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.ponyinject.api.ApiService
import me.tatarka.inject.ponyinject.api.FakeApiService

class TestFakes(
    @get:Provides val service: ApiService = FakeApiService()
)

@Component
@ApplicationScope
abstract class TestApplicationComponent(@Component val fakes: TestFakes = TestFakes())