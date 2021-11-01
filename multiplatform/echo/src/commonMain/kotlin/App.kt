import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.annotations.Provides

typealias Args = Array<String>

@Component
abstract class ApplicationComponent(
    @get:Provides val args: Args,
    @Component val other: OtherComponent = OtherComponent::class.create()
) {
    abstract val app: App

    @Provides fun string(): String = "bar"
}

@Component
abstract class OtherComponent {
    abstract val stringConsumer: StringConsumer
}

@Inject
class StringConsumer(string: String)

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
    val appComponent = ApplicationComponent::class.create(args)
    appComponent.app.run()
}
