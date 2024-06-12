import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.annotations.KmpComponentCreate
import me.tatarka.inject.annotations.Provides

typealias Args = Array<String>

@Component
abstract class ApplicationComponent(@get:Provides val args: Args) {
    abstract val app: App
}

@KmpComponentCreate
expect fun createApplicationComponent(args: Args): ApplicationComponent

@Inject
class ArgProcessor(private val args: Args) {
    fun process(): String = args.joinToString(" ")
}

@Inject
class App(private val argProcessor: ArgProcessor) {
    fun run() {
        println(argProcessor.process())
    }
}

fun main(args: Array<String>) {
    val appComponent = createApplicationComponent(args)
    appComponent.app.run()
}