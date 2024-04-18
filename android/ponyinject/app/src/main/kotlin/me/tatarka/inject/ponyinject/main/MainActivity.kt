package me.tatarka.inject.ponyinject.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.ponyinject.ApplicationComponent
import me.tatarka.inject.ponyinject.R

/**
 * The app's main entry-point. We want to make this as thin as possible to allow easily changing
 * the component when under test. Any top-level logic should be in [MainFragment] instead.
 *
 * @see me.tatarka.inject.ponyinject.EndToEndTest
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val component = MainActivityComponent::class.create(ApplicationComponent.getInstance(this))
        supportFragmentManager.fragmentFactory = component.fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}

@Component
abstract class MainActivityComponent(@Component val parent: ApplicationComponent) {
    abstract val fragmentFactory: InjectFragmentFactory
}
